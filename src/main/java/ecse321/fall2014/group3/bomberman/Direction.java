/**
 * @author Phil Douyon
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
     * Gets the angle.
     *
     * @return the angle
     */
    public float getAngleDeg() {
        return angleDeg;
    }

    /**
     * Gets the rotation.
     *
     * @return the rotation
     */
    public Complexf getRotation() {
        return rotation;
    }

    /**
     * Gets the unit vector.
     *
     * @return the unit vector
     */
    public Vector2f getUnit() {
        return unit;
    }

    /**
     * Gets the perpendicular unit vector.
     *
     * @return the perpendicular unit vector
     */
    public Vector2f getPerpendicularUnit() {
        return perpendicularUnit;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * From unit.
     *
     * @param unit the unit
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
