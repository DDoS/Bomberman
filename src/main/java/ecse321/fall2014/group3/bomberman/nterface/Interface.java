package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;

import org.spout.renderer.api.GLImplementation;
import org.spout.renderer.api.gl.Context;
import org.spout.renderer.lwjgl.LWJGLUtil;

/**
 * The main class for the interface (aka the view).
 *
 * @author Aleksi
 */
public class Interface extends TickingElement {
    private final Game game;
    private final Context context = GLImplementation.get(getBestImpl());

    public Interface(Game game) {
        super("Interface", 60);
        this.game = game;
    }

    @Override
    public void onStart() {
        context.setWindowTitle("Bomberman");
        context.setWindowSize(640, 480);
        context.create();
    }

    @Override
    public void onTick(long l) {
        if (context.isWindowCloseRequested()) {
            game.close();
        }
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
