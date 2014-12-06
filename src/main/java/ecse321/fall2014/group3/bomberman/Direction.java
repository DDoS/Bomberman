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
package ecse321.fall2014.group3.bomberman;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Complexf;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.input.Key;

/**
 * The Enum Direction.
 */
public enum Direction {

    /** The right Direction. */
    RIGHT(0, Key.RIGHT),

    /** The up Direction. */
    UP(90, Key.UP),

    /** The left Direction. */
    LEFT(180, Key.LEFT),

    /** The down direction. */
    DOWN(270, Key.DOWN);

    private final float angleDeg;
    private final Vector2f unit;
    private final Complexf rotation;
    private final Vector2f perpendicularUnit;
    private final Key key;

    private Direction(float angleDeg, Key key) {
        this.angleDeg = angleDeg;
        rotation = Complexf.fromAngleDeg(angleDeg);
        unit = rotation.getDirection();
        perpendicularUnit = new Vector2f(unit.getY(), unit.getX()).abs();
        this.key = key;
    }

    /**
     * Gets the angle of the direction.
     *
     * @return the angle
     */
    public float getAngleDeg() {
        return angleDeg;
    }

    /**
     * Gets the rotation of the direction.
     *
     * @return the rotation
     */
    public Complexf getRotation() {
        return rotation;
    }

    /**
     * Gets the unit vector of the direction.
     *
     * @return the unit vector
     */
    public Vector2f getUnit() {
        return unit;
    }

    /**
     * Gets the perpendicular unit vector of the direction.
     *
     * @return the perpendicular unit vector
     */
    public Vector2f getPerpendicularUnit() {
        return perpendicularUnit;
    }

    /**
     * Gets the key pressed.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * From unit.
     *
     * @param unit the unit direction.
     * @return the direction
     */
    public static Direction fromUnit(Vector2f unit) {
        unit = unit.normalize();
        float cosAngle = (float) Math.toDegrees(TrigMath.acos(unit.getX()));
        if (unit.getY() >= 0) {
            return fromAngleDeg(cosAngle);
        } else {
            return fromAngleDeg(360 - cosAngle);
        }
    }

    /**
     * From rotation.
     *
     * @param rotation the rotation
     * @return the direction
     */
    public static Direction fromRotation(Complexf rotation) {
        return fromAngleDeg(rotation.getAngleDeg());
    }

    /**
     * From angle
     *
     * @param angle the angle
     * @return the direction
     */
    public static Direction fromAngleDeg(float angle) {
        return values()[Math.round((angle % 360 + 360) % 360 / 90) % 4];
    }
}
