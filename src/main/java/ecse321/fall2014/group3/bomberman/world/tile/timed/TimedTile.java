package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public abstract class TimedTile extends CollidableTile {
    private final long lifeTime, placeTime;

<<<<<<< HEAD
=======
    protected TimedTile(Vector2f position, float size, long lifeTime) {
        super(position, size);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
    }

>>>>>>> 65222e1ed0242cecb6346ea84a6e5090e6010175
    protected TimedTile(Vector2f position, long lifeTime) {
        super(position);
        this.lifeTime = lifeTime;
        placeTime = System.currentTimeMillis();
<<<<<<< HEAD
    }

    public long getRemainingTime() {
        return Math.max(lifeTime - (System.currentTimeMillis() - placeTime), 0);
    }

=======
    }

    public long getRemainingTime() {
        return Math.max(lifeTime - (System.currentTimeMillis() - placeTime), 0);
    }

>>>>>>> 65222e1ed0242cecb6346ea84a6e5090e6010175
    public boolean hasExpired() {
        return getRemainingTime() <= 0;
    }
}
