package ecse321.fall2014.group3.bomberman.nterface;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

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
import org.spout.renderer.api.util.CausticUtil;
import org.spout.renderer.api.util.MeshGenerator;
import org.spout.renderer.api.util.Rectangle;
import org.spout.renderer.lwjgl.LWJGLUtil;

/**
 * The main class for the interface (aka the view).
 *
 * @author Aleksi
 */
public class Interface extends TickingElement {
    private static final int WIDTH = 640, HEIGHT = 480;
    private static final float ASPECT_RATIO = WIDTH / (float) HEIGHT;
    private static final float VIEW_WIDTH = 10 * ASPECT_RATIO, VIEW_HEIGHT = 10;
    private static final int SPRITE_SIZE = 64;
    private final Game game;
    private final Camera orthoCamera;
    private final Context context = GLImplementation.get(getBestImpl());
    private Model spriteModel;
    private Pipeline pipeline;
    private long mapVersion = 0;
    private final Set<Model> tileModels = new HashSet<>();
    private final java.util.Map<Entity, Model> entityModels = new HashMap<>();

    public Interface(Game game) {
        super("Interface", 60);
        this.game = game;

        orthoCamera = Camera.createOrthographic(VIEW_WIDTH, 0, VIEW_HEIGHT, 0, 0, 10);
        pipeline = new PipelineBuilder().clearBuffer().useCamera(orthoCamera).renderModels(tileModels).renderModels(entityModels.values()).updateDisplay().build();
    }

    @Override
    public void onStart() {
        context.setWindowTitle("Bomberman");
        context.setWindowSize(WIDTH, HEIGHT);
        context.create();
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

        final Program spriteProgram = context.newProgram();
        spriteProgram.create();
        spriteProgram.attachShader(spriteVertShader);
        spriteProgram.attachShader(spriteFragShader);
        spriteProgram.link();

        final Texture spriteSheetTexture = context.newTexture();
        spriteSheetTexture.create();
        spriteSheetTexture.setFilters(FilterMode.LINEAR, FilterMode.LINEAR);
        spriteSheetTexture.setFormat(Format.RGB);

        final Rectangle size = new Rectangle();
        final ByteBuffer imageData = CausticUtil.getImageData(getClass().getResourceAsStream("/textures/sprite_sheet.png"), Format.RGB, size);
        spriteSheetTexture.setImageData(imageData, size.getWidth(), size.getHeight());

        final Material spriteMaterial = new Material(spriteProgram);
        spriteMaterial.addTexture(0, spriteSheetTexture);
        spriteMaterial.getUniforms().add(new IntUniform("spritesPerLine", size.getWidth() / SPRITE_SIZE));

        final VertexArray spriteVertexArray = context.newVertexArray();
        spriteVertexArray.create();
        spriteVertexArray.setData(MeshGenerator.generatePlane(Vector2f.ONE));

        spriteModel = new Model(spriteVertexArray, spriteMaterial);
    }

    @Override
    public void onTick(long dt) {
        final Map map = game.getWorld().getMap();
        final long newVersion = map.getVersion();

        if (mapVersion < newVersion) {
            tileModels.clear();
            for (int y = 0; y < Map.HEIGHT; y++) {
                for (int x = 0; x < Map.WIDTH; x++) {
                    final Tile tile = map.getTile(x, y);
                    final Model model = spriteModel.getInstance();
                    model.setPosition(tile.getPosition().toVector3(-1));
                    model.setScale(tile.getModelSize().toVector3(1));
                    model.getUniforms().add(new IntUniform("spriteNumber", tile.getSpriteInfo().getSpriteNumber()));
                    model.getUniforms().add(new Vector2Uniform("spriteSize", tile.getSpriteInfo().getSpriteSize()));
                    tileModels.add(model);
                }
            }
            mapVersion = newVersion;
        }

        final Set<Entity> entities = game.getPhysics().getEntities();
        for (Iterator<Entity> iterator = entityModels.keySet().iterator(); iterator.hasNext(); ) {
            if (!entities.contains(iterator.next())) {
                iterator.remove();
            }
        }
        for (Entity entity : entities) {
            Model model = entityModels.get(entity);
            if (model == null) {
                model = spriteModel.getInstance();
                model.setScale(entity.getModelSize().toVector3(1));
                model.getUniforms().add(new IntUniform("spriteNumber", 0));
                model.getUniforms().add(new Vector2Uniform("spriteSize", Vector2f.ZERO));
                entityModels.put(entity, model);
            }
            model.setPosition(entity.getPosition().toVector3(0));
            model.setRotation(entity.getDirection().getRotation().toQuaternion());
            model.getUniforms().<IntUniform>get("spriteNumber").set(entity.getSpriteInfo().getSpriteNumber());
            model.getUniforms().<Vector2Uniform>get("spriteSize").set(entity.getSpriteInfo().getSpriteSize());
        }

        orthoCamera.setPosition(game.getPhysics().getPlayer().getPosition().sub(VIEW_WIDTH / 2, VIEW_HEIGHT / 2).toVector3());

        pipeline.run(context);
    }

    @Override
    public void onStop() {
        context.destroy();
        mapVersion = 0;
        tileModels.clear();
    }

    private static GLImplementation getBestImpl() {
        try {
            // Create a temporary OpenGL context using an empty pixel buffer
            Pbuffer tempContext = new Pbuffer(0, 0, new PixelFormat(), null);
            tempContext.makeCurrent();
            // Get the capabilities for the context
            ContextCapabilities capabilities = GLContext.getCapabilities();
            tempContext.destroy();
            // Return the proper context implementation for the highest supported version
            if (capabilities.OpenGL21) {
                return LWJGLUtil.GL20_IMPL;
            } else if (capabilities.OpenGL20) {
                return LWJGLUtil.GL20_IMPL;
            } else {
                throw new IllegalStateException("This hardware doesn't support OpenGL2.0+");
                // maybe use caustic-software in this case
            }
        } catch (LWJGLException ex) {
            // try GL2.0 if we can't verify the supported version
            return LWJGLUtil.GL20_IMPL;
            // maybe use caustic-software in this case
        }
    }
}
