package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public abstract class TimedTile extends CollidableTile {
    public TimedTile(Vector2f position) {
        super(position);
    }

    public abstract long getRemainingTime();
}
