package ecse321.fall2014.group3.bomberman.nterface;

import java.awt.Font;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector4f;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

import org.spout.renderer.api.Camera;
import org.spout.renderer.api.GLImplementation;
import org.spout.renderer.api.Material;
import org.spout.renderer.api.Pipeline;
import org.spout.renderer.api.Pipeline.PipelineBuilder;
import org.spout.renderer.api.data.ShaderSource;
import org.spout.renderer.api.data.Uniform.IntUniform;
import org.spout.renderer.api.data.Uniform.Vector2Uniform;
import org.spout.renderer.api.gl.Context;
import org.spout.renderer.api.gl.Context.Capability;
import org.spout.renderer.api.gl.Program;
import org.spout.renderer.api.gl.Shader;
import org.spout.renderer.api.gl.Texture;
import org.spout.renderer.api.gl.Texture.FilterMode;
import org.spout.renderer.api.gl.Texture.Format;
import org.spout.renderer.api.gl.VertexArray;
import org.spout.renderer.api.model.Model;
import org.spout.renderer.api.model.StringModel;
import org.spout.renderer.api.model.StringModel.AntiAliasing;
import org.spout.renderer.api.util.CausticUtil;
import org.spout.renderer.api.util.MeshGenerator;
import org.spout.renderer.api.util.Rectangle;
import org.spout.renderer.lwjgl.LWJGLUtil;

/**
 * The main class for the interface (aka the view).
 *
 * @author Aleksi Sapon
 */
public class Interface extends TickingElement {
    private static final int SPRITE_SIZE = 64;
    public static final int VIEW_WIDTH_TILE = 15, VIEW_HEIGHT_TILE = 13;
    private static final int TILE_PIXEL_SIZE = SPRITE_SIZE;
    private static final int WIDTH = VIEW_WIDTH_TILE * TILE_PIXEL_SIZE, HEIGHT = VIEW_HEIGHT_TILE * TILE_PIXEL_SIZE;
    private static final float ASPECT_RATIO = VIEW_WIDTH_TILE / (float) VIEW_HEIGHT_TILE;
    private static final int SCROLL_TILE_THRESHOLD = 7;
    private static final String GLYPHS;
    private final Game game;
    private final Camera orthographicCamera;
    private final Context context = GLImplementation.get(LWJGLUtil.GL20_IMPL);
    private Program spriteProgram;
    private Program fontProgram;
    private VertexArray spriteVertexArray;
    private final java.util.Map<String, Model> spriteBaseModels = new HashMap<>();
    private final java.util.Map<String, StringModel> textBaseModels = new HashMap<>();
    private final Pipeline pipeline;
    private long mapVersion = 0;
    private final java.util.Map<Vector2i, Model> tileModels = new HashMap<>();
    private final java.util.Map<Entity, Model> entityModels = new HashMap<>();

    static {
        final String alphabetic = "abcdefghijklmnopqrstuvwxyz";
        final String numeric = "0123456789";
        final String special = " ,.;:/\\|!@#$%?&*()-_=+\"'";
        GLYPHS = alphabetic + alphabetic.toUpperCase() + numeric + special;
    }

    public Interface(Game game) {
        super("Interface", 60);
        this.game = game;

        orthographicCamera = Camera.createOrthographic(VIEW_WIDTH_TILE, 0, VIEW_HEIGHT_TILE, 0, 0, 10);
        pipeline = new PipelineBuilder()
                .clearBuffer()
                .useCamera(orthographicCamera)
                .renderModels(tileModels.values())
                .renderModels(entityModels.values())
                .updateDisplay()
                .build();
    }

    @Override
    public void onStart() {
        context.setWindowTitle("Bomberman");
        context.setWindowSize(WIDTH, HEIGHT);
        context.create();
        context.setClearColor(CausticUtil.GREEN);
        context.enableCapability(Capability.CULL_FACE);
        context.enableCapability(Capability.DEPTH_TEST);

        final Shader spriteVertShader = context.newShader();
        spriteVertShader.create();
        spriteVertShader.setSource(new ShaderSource(getClass().getResourceAsStream("/shaders/sprite.vert")));
        spriteVertShader.compile();
        final Shader spriteFragShader = context.newShader();
        spriteFragShader.create();
        spriteFragShader.setSource(new ShaderSource(getClass().getResourceAsStream("/shaders/sprite.frag")));
        spriteFragShader.compile();

        spriteProgram = context.newProgram();
        spriteProgram.create();
        spriteProgram.attachShader(spriteVertShader);
        spriteProgram.attachShader(spriteFragShader);
        spriteProgram.link();

        spriteVertexArray = context.newVertexArray();
        spriteVertexArray.create();
        spriteVertexArray.setData(MeshGenerator.generatePlane(Vector2f.ONE));

        final Shader fontVertShader = context.newShader();
        fontVertShader.create();
        fontVertShader.setSource(new ShaderSource(getClass().getResourceAsStream("/shaders/glsl120/font.vert")));
        fontVertShader.compile();
        final Shader fontFragShader = context.newShader();
        fontFragShader.create();
        fontFragShader.setSource(new ShaderSource(getClass().getResourceAsStream("/shaders/glsl120/font.frag")));
        fontFragShader.compile();

        fontProgram = context.newProgram();
        fontProgram.create();
        fontProgram.attachShader(fontVertShader);
        fontProgram.attachShader(fontFragShader);
        fontProgram.link();
    }

    @Override
    public void onTick(long dt) {
        final Map map = game.getWorld().getMap();
        final long newVersion = map.getVersion();

        if (mapVersion < newVersion) {
            tileModels.clear();
            for (int y = 0; y < Map.HEIGHT; y++) {
                for (int x = 0; x < Map.WIDTH; x++) {
                    final Vector2i position = new Vector2i(x, y);
                    final Tile tile = map.getTile(position);
                    if (tile instanceof Air) {
                        continue;
                    }
                    final Model model = newSpriteModel(tile.getSpriteInfo());
                    model.setPosition(tile.getPosition().toVector3(-1));
                    model.setScale(tile.getModelSize().toVector3(1));
                    tileModels.put(position, model);
                }
            }
            mapVersion = newVersion;
        } else {
            for (int y = 0; y < Map.HEIGHT; y++) {
                for (int x = 0; x < Map.WIDTH; x++) {
                    final Vector2i position = new Vector2i(x, y);
                    final Tile tile = map.getTile(position);
                    if (tile instanceof Air) {
                        continue;
                    }
                    final Model model = tileModels.get(position);
                    if (model != null) {
                        model.setScale(tile.getModelSize().toVector3(1));
                    }
                }
            }
        }

        final Set<Entity> entities = game.getPhysics().getEntities();
        for (Iterator<Entity> iterator = entityModels.keySet().iterator(); iterator.hasNext(); ) {
            if (!entities.contains(iterator.next())) {
                iterator.remove();
            }
        }
        for (Entity entity : entities) {
            Model model = entityModels.get(entity);
            Model newModel;
            if (entity instanceof SpriteTextured) {
                newModel = updateSpriteEntity((SpriteTextured) entity, model);
                newModel.setScale(entity.getModelSize().toVector3(1));
            } else if (entity instanceof TextTextured) {
                newModel = updateTextEntity((TextTextured) entity, (StringModel) model);
                newModel.setScale(entity.getModelSize().mul(1, ASPECT_RATIO).toVector3(1));
            } else {
                throw new IllegalStateException("Unknown entity draw type: neither sprite or text \"" + entity + "\"");
            }
            if (model == null) {
                entityModels.put(entity, newModel);
                model = newModel;
            }
            model.setPosition(entity.getPosition().toVector3(0));
        }

        final Vector3f viewPosition;
        if (game.getWorld().getLevel().isMenu()) {
            viewPosition = Vector3f.ZERO;
        } else {
            final Vector2f playerPosition = game.getPhysics().getPlayer().getPosition();
            if (playerPosition.getFloorX() < SCROLL_TILE_THRESHOLD) {
                viewPosition = Vector3f.ZERO;
            } else if (playerPosition.getFloorX() >= Map.WIDTH - SCROLL_TILE_THRESHOLD - 1) {
                viewPosition = new Vector3f(Map.WIDTH - VIEW_WIDTH_TILE, 0, 0);
            } else {
                viewPosition = new Vector3f(playerPosition.getX() - SCROLL_TILE_THRESHOLD, 0, 0);
            }
        }
        orthographicCamera.setPosition(viewPosition.sub(0.5f, 0.5f, 0));

        pipeline.run(context);
    }

    private Model updateSpriteEntity(SpriteTextured entity, Model model) {
        final SpriteInfo spriteInfo = entity.getSpriteInfo();
        if (model == null) {
            model = newSpriteModel(spriteInfo);
        } else {
            model.getUniforms().<IntUniform>get("spriteNumber").set(spriteInfo.getSpriteNumber());
            model.getUniforms().<Vector2Uniform>get("spriteSize").set(spriteInfo.getSpriteSize());
        }
        return model;
    }

    private Model newSpriteModel(SpriteInfo spriteInfo) {
        // Try and load the sprite base model from the sheet name
        Model model = loadSpriteBaseModel(spriteInfo.getSheetName());
        if (model == null) {
            throw new IllegalStateException("Missing sprite sheet: " + spriteInfo.getSheetName());
        }
        // Get a copy of the base model (referred to as an "instance")
        model = model.getInstance();
        model.getUniforms().add(new IntUniform("spriteNumber", spriteInfo.getSpriteNumber()));
        model.getUniforms().add(new Vector2Uniform("spriteSize", spriteInfo.getSpriteSize()));
        return model;
    }

    private Model loadSpriteBaseModel(String sheetName) {
        // Check if it's already loaded
        Model model = spriteBaseModels.get(sheetName);
        if (model != null) {
            return model;
        }

        // Else create a new one and cache it
        final InputStream spriteInput = getClass().getResourceAsStream("/sprites/" + sheetName + ".png");
        if (spriteInput == null) {
            return null;
        }
        final Rectangle size = new Rectangle();
        final ByteBuffer imageData = CausticUtil.getImageData(spriteInput, Format.RGBA, size);

        final Texture spriteSheetTexture = context.newTexture();
        spriteSheetTexture.create();
        spriteSheetTexture.setFilters(FilterMode.LINEAR_MIPMAP_LINEAR, FilterMode.LINEAR);
        spriteSheetTexture.setFormat(Format.RGBA);
        spriteSheetTexture.setImageData(imageData, size.getWidth(), size.getHeight());

        final Material spriteMaterial = new Material(spriteProgram);
        spriteMaterial.addTexture(0, spriteSheetTexture);
        spriteMaterial.getUniforms().add(new IntUniform("spritesPerLine", size.getWidth() / SPRITE_SIZE));

        model = new Model(spriteVertexArray, spriteMaterial);
        spriteBaseModels.put(sheetName, model);

        return model;
    }

    private StringModel updateTextEntity(TextTextured entity, StringModel model) {
        if (model == null) {
            model = newTextModel(entity.getFontInfo());
        }
        model.setString(formatColoredText(entity.getText(), entity.getTextColor()));
        return model;
    }

    private String formatColoredText(String text, Vector4f color) {
        // Scale to [0, 255]
        color = color.mul(255);
        // Append color code to start of string for decode by string renderer
        return "#" + Integer.toHexString((color.getFloorW() & 255) << 24 | (color.getFloorX() & 255) << 16 | (color.getFloorY() & 255) << 8 | color.getFloorZ() & 255) + text;
    }

    private StringModel newTextModel(FontInfo fontInfo) {
        // Load the text base model from the font type
        final StringModel model = loadTextBaseModel(fontInfo.getTypeString());
        // Return a copy of the base model (referred to as an "instance")
        return model.getInstance();
    }

    private StringModel loadTextBaseModel(String fontType) {
        // Check if it's already loaded
        StringModel model = textBaseModels.get(fontType);
        if (model != null) {
            return model;
        }

        // Else create a new one and cache it
        final Font font = Font.decode(fontType);
        model = new StringModel(context, fontProgram, GLYPHS, font, AntiAliasing.OFF, TILE_PIXEL_SIZE);
        textBaseModels.put(fontType, model);

        return model;
    }

    @Override
    public void onStop() {
        spriteProgram.destroy();
        spriteProgram = null;
        spriteVertexArray.destroy();
        spriteVertexArray = null;
        fontProgram.destroy();
        fontProgram = null;
        context.destroy();
        spriteBaseModels.clear();
        textBaseModels.clear();
        mapVersion = 0;
        tileModels.clear();
        entityModels.clear();
    }
}
