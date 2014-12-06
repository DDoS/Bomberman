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
package ecse321.fall2014.group3.bomberman.physics.entity;

import java.util.HashSet;

import java.util.Set;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.nterface.Textured;
import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

/**
 * The Class Entity.
 */
public abstract class Entity extends Collidable implements Textured {

    /** The velocity. */
    protected volatile Vector2f velocity = Vector2f.ZERO;

    /** The direction. */
    protected volatile Direction direction = Direction.RIGHT;

    /**
     * Instantiates a new entity.
     *
     * @param position the position
     * @param collisionBox the collision box
     */
    protected Entity(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }

    /**
     * Sets the velocity.
     *
     * @param velocity the new velocity
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        // Only update the direction if we're actually moving, else preserve the current direction
        if (velocity.lengthSquared() >= GenericMath.FLT_EPSILON * GenericMath.FLT_EPSILON) {
            direction = Direction.fromUnit(velocity);
        }
    }

    /**
     * Gets the velocity.
     *
     * @return the velocity
     */
    public Vector2f getVelocity() {
        return velocity;
    }

    /**
     * Gets the direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Gets the entity collision list.
     *
     * @return the entity collision list
     */
    public Set<Entity> getEntityCollisionList() {
        final Set<Entity> entities = new HashSet<>();
        for (Collidable collidable : getCollisionList()) {
            if (collidable instanceof Entity) {
                entities.add((Entity) collidable);
            }
        }
        return entities;
    }

    /**
     * Gets the tile collision list.
     *
     * @return the tile collision list
     */
    public Set<Tile> getTileCollisionList() {
        final Set<Tile> tiles = new HashSet<>();
        for (Collidable collidable : getCollisionList()) {
            if (collidable instanceof Tile) {
                tiles.add((Tile) collidable);
            }
        }
        return tiles;
    }
}
