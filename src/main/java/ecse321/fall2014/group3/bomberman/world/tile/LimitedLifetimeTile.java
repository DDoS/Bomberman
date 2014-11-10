package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

public abstract class LimitedLifetimeTile extends Tile {
    public LimitedLifetimeTile(Vector2f position) {
        super(position, Tile.COLLISION_BOX);
    }

    public abstract int getRemainingTime();
}
