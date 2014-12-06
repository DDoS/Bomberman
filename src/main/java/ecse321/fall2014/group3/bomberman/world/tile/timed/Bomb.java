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

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class Bomb tile.
 */
public class Bomb extends TimedTile {
    private static final SpriteInfo BOMB_SPRITE = new SpriteInfo("Sprite", 68, Vector2f.ONE);
    private static final long LIFE_TIME = 3000;
    private static final float ANIMATION_GROWTH_PERCENT = 0.25f;

    /**
     * Instantiates a new bomb tile.
     *
     * @param position the position
     */
    public Bomb(Vector2f position) {
        super(position, LIFE_TIME);
    }

    @Override
    public Vector2f getModelSize() {
        final float timePercent = 1 - getRemainingTime() / (float) LIFE_TIME;
        return super.getModelSize().mul(timePercent * ANIMATION_GROWTH_PERCENT + (1 - ANIMATION_GROWTH_PERCENT));
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMB_SPRITE;
    }
}
