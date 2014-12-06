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
package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;


/**
 * The Class Enemy.
 */
public abstract class Enemy extends Mob {
    private static final Vector2f SIZE = Vector2f.ONE;
    private static final CollisionBox COLLISION_BOX = new CollisionBox(SIZE.mul(0.8f));
    
    /** The Constant ENEMY_BASE_SPEED. */
    public static final float ENEMY_BASE_SPEED = 2;

    /**
     * Instantiates a new enemy.
     *
     * @param position the position
     */
    public Enemy(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    /**
     * Gets the AI.
     *
     * @return the AI
     */
    public abstract AI getAI();

    /**
     * Gets the of the enemy.
     *
     * @return the speed
     */
    public abstract float getSpeed();

    /**
     * Checks if enemy can wall pass.
     *
     * @return true, if is wall pass
     */
    public abstract boolean isWallPass();

    /**
     * Gets the score of the enemy.
     *
     * @return the score
     */
    public abstract int getScore();

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
