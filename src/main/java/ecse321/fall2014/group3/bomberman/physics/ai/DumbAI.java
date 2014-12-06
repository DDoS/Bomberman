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
package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Fire;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Breakable;

/**
 * Represents the Dumb AI.  This Ai moves in one direction until it hits a bomb or a wall and then turns around.
 * The AI sets an initial non-zero velocity to the Enemy. If the enemy has the wall pass ability, check that the next space
 * is Air, Fire, or a Breakable wall. If the enemy does not have the wall pass ability, check that the next space is Air or Fire.
 * If the next space is empty, set the new position to the velocity * poll time. If the next space is a wall, set the Enemy velocity
 * to -velocity and set the new position to -velocity * poll time.
 */
public class DumbAI extends AI {
    @Override
    public Vector2f nextPosition(Enemy target, long dt, Map map, Player player) {
        float timeSec = dt / TO_SECS;
        Vector2f enemyPos = target.getPosition();
        Vector2f eVelocity = target.getVelocity();
        float eSpeed = target.getSpeed();
        
        //Set initial velocity of the enemy based on the first direction with a free space
        if (eVelocity.length() == 0) {
            if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
                target.setVelocity(new Vector2f(eSpeed, 0f));
                return enemyPos.add(1f * timeSec, 0f);
            } else if (map.isTile(enemyPos.add(-1f * timeSec, 0f), Air.class)) {
                target.setVelocity(new Vector2f(-eSpeed, 0f));
                return enemyPos.sub(eSpeed * timeSec, 0f);
            } else if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
                target.setVelocity(new Vector2f(0f, eSpeed));
                return enemyPos.add(0f, eSpeed * timeSec);
            } else {
                target.setVelocity(new Vector2f(0f, -eSpeed));
                return enemyPos.sub(0f, eSpeed * timeSec);
            }
        } else if (!target.isWallPass()) {
            //If the enemy does not have the wall pass ability
            //It can only go through tiles that are Air or Fire
            //Reverse direction if a non-passable tile is encountered
            float xs = eVelocity.getX();
            float ys = eVelocity.getY();

            if (xs == 0f) {
                if (ys < 0) {
                    if (map.isTile(enemyPos.add(0f, timeSec * ys), Air.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else if (map.isTile(enemyPos.add(0f, timeSec * ys), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, eSpeed));
                        return enemyPos.add(0f, eSpeed * timeSec);
                    }
                } else {
                    if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else if (map.isTile(enemyPos.add(0f, 1f), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, -eSpeed));
                        return enemyPos.add(0f, -eSpeed * timeSec);
                    }
                }
            } else {
                if (xs < 0) {
                    if (map.isTile(enemyPos.add(xs * timeSec, 0f), Air.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else if (map.isTile(enemyPos.add(xs * timeSec, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(eSpeed, 0f));
                        return enemyPos.add(eSpeed * timeSec, 0f);
                    }
                } else {
                    if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else if (map.isTile(enemyPos.add(1f, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(-eSpeed, 0f));
                        return enemyPos.add(-eSpeed * timeSec, 0f);
                    }
                }
            }        
        } else {
            //If the Enemy does have the wall pass ability
            //It can go through tiles that are Air, Fire, Breakable
            //Reverse direction when an unpassable tile is met
            float xs = eVelocity.getX();
            float ys = eVelocity.getY();
            
            //If the horizontal component of the velocity is 0
            //Check the position above if the vertical component is greater than 0
            //Check the position below if the vertical component is less than 0
            //If the position is empty, move into it.  Otherwise reverse direction
            if (xs == 0f) {
                if (ys < 0) {
                    if (map.isTile(enemyPos.add(0f, timeSec * ys), Air.class) ||
                        map.isTile(enemyPos.add(0f, timeSec * ys), Breakable.class) ||
                        map.isTile(enemyPos.add(0f, timeSec * ys), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, eSpeed));
                        return enemyPos.add(0f, eSpeed * timeSec);
                    }
                } else {
                    if (map.isTile(enemyPos.add(0f, 1f), Air.class) ||
                        map.isTile(enemyPos.add(0f, 1f), Breakable.class) ||
                        map.isTile(enemyPos.add(0f, 1f), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, -eSpeed));
                        return enemyPos.add(0f, -eSpeed * timeSec);
                    }
                }
            } else {
            //If the vertical component of the velocity is 0
            //Check the position above if the horizontal component is greater than 0
            //Check the position below if the horizontal component is less than 0
            //If the position is empty, move into it.  Otherwise reverse direction
                if (xs < 0) {
                    if (map.isTile(enemyPos.add(xs * timeSec, 0f), Air.class) ||
                        map.isTile(enemyPos.add(xs * timeSec, 0f), Breakable.class) ||
                        map.isTile(enemyPos.add(xs * timeSec, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(eSpeed, 0f));
                        return enemyPos.add(eSpeed * timeSec, 0f);
                    }
                } else {
                    if (map.isTile(enemyPos.add(1f, 0f), Air.class) ||
                        map.isTile(enemyPos.add(1f, 0f), Breakable.class) ||
                        map.isTile(enemyPos.add(1f, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(-eSpeed, 0f));
                        return enemyPos.add(-eSpeed * timeSec, 0f);
                    }
                }
            }
        }
    }
}
