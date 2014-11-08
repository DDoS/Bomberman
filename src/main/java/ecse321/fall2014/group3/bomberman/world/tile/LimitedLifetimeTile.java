package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

import com.flowpowered.math.vector.Vector2f;

public abstract class LimitedLifetimeTile extends Tile {

    public LimitedLifetimeTile(Vector2f position) {
        super(position, Tile.COLLISION_BOX);
    }

    @Override
    public abstract SpriteInfo getSpriteInfo();

    public abstract int getTime();
}
