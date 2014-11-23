package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public abstract class TimedTile extends CollidableTile {
    private final long lifeTime, placeTime;

    protected TimedTile(Vector2f position, long lifeTime) {
        super(position);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
    }

    public long getRemainingTime() {
        return Math.max(lifeTime - (System.currentTimeMillis() - placeTime), 0);
    }

    public boolean hasExpired() {
        return getRemainingTime() <= 0;
    }
}
