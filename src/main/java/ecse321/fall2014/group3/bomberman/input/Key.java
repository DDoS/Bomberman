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

import org.lwjgl.input.Keyboard;

/**
 * The Enum Key.
 */
public enum Key {

    /** The up key. */
    UP(Keyboard.KEY_UP),

    /** The down key. */
    DOWN(Keyboard.KEY_DOWN),

    /** The left key. */
    LEFT(Keyboard.KEY_LEFT),

    /** The right key. */
    RIGHT(Keyboard.KEY_RIGHT),

    /** The place bomb key. */
    PLACE(Keyboard.KEY_A),

    /** The exit. */
    EXIT(Keyboard.KEY_ESCAPE),

    /** The detonate. */
    DETONATE(Keyboard.KEY_B);

    /** The Constant COUNT. */
    private static final int COUNT = values().length;
    private final int keyCode;

    private Key(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Gets the key code.
     *
     * @return the key code
     */
    int getKeyCode() {
        return keyCode;
    }

    /**
     * Checks if is key is down.
     *
     * @return true, if key is down
     */
    boolean isDown() {
        return Keyboard.isKeyDown(keyCode);
    }

    /**
     * Gets the key count.
     *
     * @return the key count
     */
    public static int getCount() {
        return COUNT;
    }
}
