package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.TrigMath;
import com.flowpowered.math.imaginary.Complexf;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.input.Key;

/**
 *
 */
public enum Direction {
    RIGHT(0, Key.RIGHT),
    UP(90, Key.UP),
    LEFT(180, Key.LEFT),
    DOWN(270, Key.DOWN);
    private final float angleDeg;
    private final Vector2f unit;
    private final Complexf rotation;
    private final Key key;

    private Direction(float angleDeg, Key key) {
        this.angleDeg = angleDeg;
        rotation = Complexf.fromAngleDeg(angleDeg);
        unit = rotation.getDirection();
        this.key = key;
    }

    public float getAngleDeg() {
        return angleDeg;
    }

    public Complexf getRotation() {
        return rotation;
    }

    public Vector2f getUnit() {
        return unit;
    }

    public Key getKey() {
        return key;
    }

    public static Direction fromUnit(Vector2f unit) {
        unit = unit.normalize();
        float cosAngle = (float) Math.toDegrees(TrigMath.acos(unit.getX()));
        if (unit.getY() >= 0) {
            return fromAngleDeg(cosAngle);
        } else {
            return fromAngleDeg(360 - cosAngle);
        }
    }

    public static Direction fromRotation(Complexf rotation) {
        return fromAngleDeg(rotation.getAngleDeg());
    }

    public static Direction fromAngleDeg(float angle) {
        return values()[Math.round((angle % 360 + 360) % 360 / 90) % 4];
    }
}
