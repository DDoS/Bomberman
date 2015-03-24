/*
 * This file is part of Bomberman, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Group 3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

import com.flowpowered.caustic.api.Camera;
import com.flowpowered.caustic.api.GLImplementation;
import com.flowpowered.caustic.api.Material;
import com.flowpowered.caustic.api.Pipeline;
import com.flowpowered.caustic.api.Pipeline.PipelineBuilder;
import com.flowpowered.caustic.api.data.ShaderSource;
import com.flowpowered.caustic.api.data.Uniform.IntUniform;
import com.flowpowered.caustic.api.data.Uniform.Vector2Uniform;
import com.flowpowered.caustic.api.gl.Context;
import com.flowpowered.caustic.api.gl.Context.Capability;
import com.flowpowered.caustic.api.gl.Program;
import com.flowpowered.caustic.api.gl.Shader;
import com.flowpowered.caustic.api.gl.Texture;
import com.flowpowered.caustic.api.gl.Texture.FilterMode;
import com.flowpowered.caustic.api.gl.Texture.Format;
import com.flowpowered.caustic.api.gl.VertexArray;
import com.flowpowered.caustic.api.model.Model;
import com.flowpowered.caustic.api.model.StringModel;
import com.flowpowered.caustic.api.model.StringModel.AntiAliasing;
import com.flowpowered.caustic.api.util.CausticUtil;
import com.flowpowered.caustic.api.util.MeshGenerator;
import com.flowpowered.caustic.api.util.Rectangle;
import com.flowpowered.caustic.lwjgl.LWJGLUtil;

/**
 * The main class for the interface (aka the view). Runs on its own thread and pulls all the information from {@link ecse321.fall2014.group3.bomberman.world.World} and {@link
 * ecse321.fall2014.group3.bomberman.physics.Physics} to perform the rendering. More precisely, this class can render tiles that are instances of {@link
 * ecse321.fall2014.group3.bomberman.nterface.SpriteTextured} and entities that are either {@link ecse321.fall2014.group3.bomberman.nterface.SpriteTextured} or {@link
 * ecse321.fall2014.group3.bomberman.nterface.TextTextured}. Runs at a target refresh rate of 60 FPS. Requires OpenGL 2 or newer.
 */
public class Interface extends TickingElement {
    private static final int SPRITE_SIZE = 64;
    /**
     * The view width and height in tiles. This is what's visible at any given time on the screen.
     */
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

    /**
     * Constructs a new Interface from the game to render.
     *
     * @param game The game
     */
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

    // Updates the model to match the sprite entity
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

    // Generates a new model for the sprite, updating it
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

    // Loads, if needed, a new model to use as a base for other models that use the same sprite sheet.
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

    // Updates the model to match the text entity
    private StringModel updateTextEntity(TextTextured entity, StringModel model) {
        if (model == null) {
            model = newTextModel(entity.getFontInfo());
        }
        model.setString(formatColoredText(entity.getText(), entity.getTextColor()));
        return model;
    }

    // Adds the encoded color to the beginning of the text for use by the text renderer
    private String formatColoredText(String text, Vector4f color) {
        // Scale to [0, 255]
        color = color.mul(255);
        // Append color code to start of string for decode by string renderer
        return "#" + Integer.toHexString((color.getFloorW() & 255) << 24 | (color.getFloorX() & 255) << 16 | (color.getFloorY() & 255) << 8 | color.getFloorZ() & 255) + text;
    }

    // Generates a new model for text, updating it
    private StringModel newTextModel(FontInfo fontInfo) {
        // Load the text base model from the font type
        final StringModel model = loadTextBaseModel(fontInfo.getTypeString());
        // Return a copy of the base model (referred to as an "instance")
        return model.getInstance();
    }

    // Loads, if needed, a new model to use as a base for other models that use the same font
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
