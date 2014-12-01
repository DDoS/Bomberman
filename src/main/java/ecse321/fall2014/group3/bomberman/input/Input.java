/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.input;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/**
 * The Class Input.
 */
public class Input extends TickingElement {
    private final Game game;
    private boolean keyboardCreated = false;
    private final Map<Long, KeyboardState> keyboardStates = new ConcurrentHashMap<>();
    private final long[] dtPressTimes = new long[Key.getCount()];
    private final int[] dtPressCounts = new int[Key.getCount()];
    private final boolean[] pressStates = new boolean[Key.getCount()];

    /**
     * Instantiates a new input.
     *
     * @param game the game
     */
    public Input(Game game) {
        super("input", 60);
        this.game = game;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.ticking.TickingElement#onStart()
     */
    @Override
    public void onStart() {
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.ticking.TickingElement#onTick(long)
     */
    @Override
    public void onTick(long dt) {
        if (Display.isCreated() && Display.isCloseRequested()) {
            game.close();
        }

        createInputIfNecessary();

        if (keyboardCreated) {
            // Poll the latest keyboard state
            Keyboard.poll();
            // Generate keyboard info for the tick for each key
            for (Key key : Key.values()) {
                final int ordinal = key.ordinal();
                if (key.isDown()) {
                    dtPressTimes[ordinal] = dt;
                    // look for press state rising edge
                    if (!pressStates[ordinal]) {
                        // increment press count on rising edge
                        dtPressCounts[ordinal] = 1;
                    } else {
                        // no change in key press
                        dtPressCounts[ordinal] = 0;
                    }
                    pressStates[ordinal] = true;
                } else {
                    dtPressTimes[ordinal] = 0;
                    dtPressCounts[ordinal] = 0;
                    pressStates[ordinal] = false;
                }
            }
            // update the keyboard state objects
            for (KeyboardState state : keyboardStates.values()) {
                for (Key key : Key.values()) {
                    final int ordinal = key.ordinal();
                    state.incrementPressTime(key, dtPressTimes[ordinal]);
                    state.incrementPressCount(key, dtPressCounts[ordinal]);
                }
            }
        }
    }

    private void createInputIfNecessary() {
        if (!keyboardCreated) {
            if (Display.isCreated()) {
                if (!Keyboard.isCreated()) {
                    try {
                        Keyboard.create();
                        keyboardCreated = true;
                    } catch (LWJGLException ex) {
                        throw new RuntimeException("Could not create keyboard", ex);
                    }
                } else {
                    keyboardCreated = true;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.ticking.TickingElement#onStop()
     */
    @Override
    public void onStop() {
        game.close();
        if (Keyboard.isCreated()) {
            Keyboard.destroy();
        }
        keyboardCreated = false;
    }

    /**
     * Gets the keyboard state.
     *
     * @return the keyboard state
     */
    public KeyboardState getKeyboardState() {
        // One keyboard state per thread.
        final long callerID = Thread.currentThread().getId();
        KeyboardState state = keyboardStates.get(callerID);
        if (state == null) {
            state = new KeyboardState();
            keyboardStates.put(callerID, state);
        }
        return state;
    }
}