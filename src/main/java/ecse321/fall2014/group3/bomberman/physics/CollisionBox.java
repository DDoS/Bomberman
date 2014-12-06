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
package ecse321.fall2014.group3.bomberman.physics;

import com.flowpowered.math.vector.Vector2f;

/**
 * An axis-aligned bounding box for collisions, used by {@link ecse321.fall2014.group3.bomberman.physics.SweepAndPruneAlgorithm}. Has a size but no position.
 */
public class CollisionBox {
    private final Vector2f size;
    private final float area;
    private final Vector2f halfSize;

    /**
     * The size of the box, in width (x) and height (y).
     *
     * @param size The size of the box
     */
    public CollisionBox(Vector2f size) {
        if (size.getX() <= 0 || size.getY() <= 0) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        this.size = size;
        this.area = size.getX() * size.getY();
        halfSize = size.div(2);
    }

    /**
     * Returns the size of the box.
     *
     * @return The box size
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Returns the surface area of the box.
     *
     * @return The area
     */
    public float getArea() {
        return area;
    }

    /**
     * Computes the max point (largest coordinates) from a position using the size.
     *
     * @param position The position to compute from
     * @return The max point
     */
    Vector2f getMaxPoint(Vector2f position) {
        return position.add(halfSize);
    }

    /**
     * Computes the min point (smallest coordinates) from a position using the size.
     *
     * @param position The position to compute from
     * @return The min point
     */
    Vector2f getMinPoint(Vector2f position) {
        return position.sub(halfSize);
    }
}
