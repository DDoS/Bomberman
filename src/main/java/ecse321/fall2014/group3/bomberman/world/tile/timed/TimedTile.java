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
/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

/**
 * The Class TimedTile.
 */
public abstract class TimedTile extends CollidableTile {
    private final long lifeTime, placeTime;

    /**
     * Instantiates a new timed tile.
     *
     * @param position the position
     * @param size the size
     * @param lifeTime the life time
     */
    protected TimedTile(Vector2f position, float size, long lifeTime) {
        super(position, size);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
    }

    /**
     * Instantiates a new timed tile.
     *
     * @param position the position
     * @param lifeTime the life time
     */
    protected TimedTile(Vector2f position, long lifeTime) {
        super(position);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
    }

    /**
     * Gets the remaining time.
     *
     * @return the remaining time
     */
    public long getRemainingTime() {
        return Math.max(lifeTime - (System.currentTimeMillis() - placeTime), 0);
    }

    /**
     * Checks to see if tile is expired.
     *
     * @return true, if successful
     */
    public boolean hasExpired() {
        return getRemainingTime() <= 0;
    }
}
