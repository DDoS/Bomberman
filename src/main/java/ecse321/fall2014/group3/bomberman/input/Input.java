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

    @Override
    public void onStart() {
    }

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