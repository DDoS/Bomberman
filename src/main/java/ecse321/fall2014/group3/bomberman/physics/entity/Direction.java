package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.imaginary.Complexf;
import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public enum Direction {
    RIGHT(Vector2f.UNIT_X, Complexf.fromAngleDeg(0)),
    UP(Vector2f.UNIT_Y, Complexf.fromAngleDeg(90)),
    LEFT(Vector2f.UNIT_X.negate(), Complexf.fromAngleDeg(180)),
    DOWN(Vector2f.UNIT_Y.negate(), Complexf.fromAngleDeg(270));
    private final Vector2f unit;
    private final Complexf rotation;

    private Direction(Vector2f unit, Complexf rotation) {
        this.unit = unit;
        this.rotation = rotation;
    }

    public Vector2f getUnit() {
        return unit;
    }

    public Complexf getRotation() {
        return rotation;
    }
}
