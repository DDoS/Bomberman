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

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * The Class KeyboardState.
 */
public class KeyboardState {
    private final AtomicLongArray pressTimes = new AtomicLongArray(Key.getCount());
    private final AtomicIntegerArray pressCounts = new AtomicIntegerArray(Key.getCount());

    /**
     * Increment press time.
     *
     * @param key the key
     * @param dt the time
     */
    void incrementPressTime(Key key, long dt) {
        pressTimes.getAndAdd(key.ordinal(), dt);
    }

    /**
     * Increment press count.
     *
     * @param key the key
     * @param count the count
     */
    void incrementPressCount(Key key, int count) {
        pressCounts.getAndAdd(key.ordinal(), count);
    }

    /**
     * Gets the press time.
     *
     * @param key the key
     * @return the press time
     */
    public long getAndClearPressTime(Key key) {
        return pressTimes.getAndSet(key.ordinal(), 0);
    }

    /**
     * Gets the press count.
     *
     * @param key the key
     * @return the press count
     */
    public int getAndClearPressCount(Key key) {
        return pressCounts.getAndSet(key.ordinal(), 0);
    }
}
