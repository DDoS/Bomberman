package ecse321.fall2014.group3.bomberman.nterface;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;

import ecse321.fall2014.group3.bomberman.Game;
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
    private final Game game;
    private final Camera orthoCamera;
    private final Context context = GLImplementation.get(getBestImpl());
    private Model spriteModel;
    private Pipeline pipeline;
    private final Set<Model> renderedModels = new HashSet<>();

    public Interface(Game game) {
        super("Interface", 60);
        this.game = game;

        final float aspectRatio = WIDTH / (float) HEIGHT;
        orthoCamera = Camera.createOrthographic(aspectRatio, 0, 1, 0, 0, 10);
    }

    @Override
    public void onStart() {
        context.setWindowTitle("Bomberman");
        context.setWindowSize(WIDTH, 480);
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
        spriteSheetTexture.setFilters(FilterMode.NEAREST, FilterMode.NEAREST);
        spriteSheetTexture.setFormat(Format.RGB);

        final Rectangle size = new Rectangle();
        final ByteBuffer imageData = CausticUtil.getImageData(getClass().getResourceAsStream("/textures/sprite_sheet.png"), Format.RGB, size);
        spriteSheetTexture.setImageData(imageData, size.getWidth(), size.getHeight());

        final Material spriteMaterial = new Material(spriteProgram);
        spriteMaterial.addTexture(0, spriteSheetTexture);

        final VertexArray spriteVertexArray = context.newVertexArray();
        spriteVertexArray.create();
        spriteVertexArray.setData(MeshGenerator.generatePlane(Vector2f.ONE));

        spriteModel = new Model(spriteVertexArray, spriteMaterial);

        pipeline = new PipelineBuilder().clearBuffer().useCamera(orthoCamera).renderModels(renderedModels).updateDisplay().build();

        // TEST CODE

        Model testModel1 = spriteModel.getInstance();
        testModel1.setScale(new Vector3f(0.5f, 0.5f, 1));
        testModel1.setPosition(new Vector3f(0.5f, 0.5f, -1));

        Model testModel2 = spriteModel.getInstance();
        testModel2.setScale(new Vector3f(0.3f, 0.3f, 1));
        testModel2.setPosition(new Vector3f(0.7f, 0.7f, -2));

        renderedModels.add(testModel1);
        renderedModels.add(testModel2);
    }

    @Override
    public void onTick(long l) {
        if (context.isWindowCloseRequested()) {
            game.close();
        }
        pipeline.run(context);
    }

    @Override
    public void onStop() {
        context.destroy();
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
            throw new IllegalStateException("Error when initializing temporary OpenGL context", ex);
            // maybe use caustic-software in this case
        }
    }
}
