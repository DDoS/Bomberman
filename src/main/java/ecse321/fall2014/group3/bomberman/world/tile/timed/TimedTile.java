/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

/**
 * The Class TimedTile.
 */
public abstract class TimedTile extends CollidableTile {
    private final long lifeTime, placeTime;

    /**
     * Instantiates a new timed tile.
     *
     * @param position the position
     * @param size the size
     * @param lifeTime the life time
     */
    protected TimedTile(Vector2f position, float size, long lifeTime) {
        super(position, size);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
    }

    /**
     * Instantiates a new timed tile.
     *
     * @param position the position
     * @param lifeTime the life time
     */
    protected TimedTile(Vector2f position, long lifeTime) {
        super(position);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
    }

    /**
     * Gets the remaining time.
     *
     * @return the remaining time
     */
    public long getRemainingTime() {
        return Math.max(lifeTime - (System.currentTimeMillis() - placeTime), 0);
    }

    /**
     * Checks to see if tile is expired.
     *
     * @return true, if successful
     */
    public boolean hasExpired() {
        return getRemainingTime() <= 0;
    }
}
