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
package ecse321.fall2014.group3.bomberman.physics.entity.mob;

import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombPass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.Detonator;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlamePass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlameUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.SpeedUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.WallPass;

/**
 * The Class Player.
 */
public class Player extends Mob {
    private static final Vector2f SIZE = Vector2f.ONE;
    private static final CollisionBox COLLISION_BOX = new CollisionBox(SIZE);
    private static final SpriteInfo DOWN_SPRITE = new SpriteInfo("Sprite", 0, Vector2f.ONE);
    private static final SpriteInfo UP_SPRITE = new SpriteInfo("Sprite", 1, Vector2f.ONE);
    private static final SpriteInfo LEFT_SPRITE = new SpriteInfo("Sprite", 2, Vector2f.ONE);
    private static final SpriteInfo RIGHT_SPRITE = new SpriteInfo("Sprite", 3, Vector2f.ONE);
    // This value is used to change the amount of travel the player needs to do in a direction
    // before he actually rotates to that direction. Higher value means more travel
    private static final float DIRECTION_CHANGE_THRESHOLD = 2 / 3f;
    // The base speed of the player, which is also the bonus for each speed upgrade
    private static final float BASE_SPEED = 2;
    // A dampened velocity that never quite gets to the maximum speed of the player (asymptotic)
    private volatile Vector2f directionVelocity = Vector2f.ZERO;
    private final Map<Class<? extends PowerUP>, Integer> powerUPs = new ConcurrentHashMap<>();

    /**
     * Instantiates a new player.
     *
     * @param position the position
     */
    public Player(Vector2f position) {
        super(position, COLLISION_BOX);
    }


    @Override
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        // Only update the direction if we've been moving in that direction for a little while
        // This is a special case for player to improve rotation animations when going around corners
        directionVelocity = this.directionVelocity.add(velocity).div(2);
        final float speed = getSpeed();
        if (directionVelocity.lengthSquared() / (speed * speed) >= DIRECTION_CHANGE_THRESHOLD) {
            direction = Direction.fromUnit(directionVelocity);
        }
    }

    /**
     * Gets the speed.
     *
     * @return the speed
     */
    public float getSpeed() {
        return (1 + getPowerUPLevel(SpeedUpgrade.class)) * BASE_SPEED;
    }

    /**
     * Gets the blast radius.
     *
     * @return the blast radius
     */
    public int getBlastRadius() {
        return 1 + getPowerUPLevel(FlameUpgrade.class);
    }

    /**
     * Gets the bomb placement count.
     *
     * @return the bomb placement count
     */
    public int getBombPlacementCount() {
        return 1 + getPowerUPLevel(BombUpgrade.class);
    }

    /**
     * Gets the powerUPs.
     *
     * @return the powerUPs
     */
    public Map<Class<? extends PowerUP>, Integer> getPowerUPs() {
        return powerUPs;
    }

    /**
     * Checks for power up.
     *
     * @param powerUP the powerUP
     * @return true, if successful
     */
    public boolean hasPowerUP(Class<? extends PowerUP> powerUP) {
        return powerUPs.containsKey(powerUP);
    }

    /**
     * Gets the power up level.
     *
     * @param powerUP the powerUP
     * @return the powerUP level
     */
    public int getPowerUPLevel(Class<? extends PowerUP> powerUP) {
        final Integer level = powerUPs.get(powerUP);
        if (level == null) {
            return 0;
        }
        return level;
    }

    /**
     * Adds the power up.
     *
     * @param powerUP the powerUP
     */
    public void addPowerUP(PowerUP powerUP) {
        Integer level = powerUPs.get(powerUP.getClass());
        if (level == null || !powerUP.canBeUpgraded()) {
            level = 1;
        } else {
            level++;
        }
        powerUPs.put(powerUP.getClass(), level);
    }

    /**
     * Clear powerUPs
     */
    public void clearPowerUPs() {
        powerUPs.clear();
    }

    /**
     * On death.
     */
    public void onDeath() {
        powerUPs.remove(Detonator.class);
        powerUPs.remove(BombPass.class);
        powerUPs.remove(WallPass.class);
        powerUPs.remove(FlamePass.class);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        switch (direction) {
            case UP:
                return UP_SPRITE;
            case DOWN:
                return DOWN_SPRITE;
            case LEFT:
                return LEFT_SPRITE;
            case RIGHT:
                return RIGHT_SPRITE;
            default:
                return DOWN_SPRITE;
        }
    }

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
