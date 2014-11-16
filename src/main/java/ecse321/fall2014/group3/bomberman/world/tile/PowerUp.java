package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public abstract class PowerUp extends Tile {

    public PowerUp(Vector2f position) {
        super(position, Tile.COLLISION_BOX);
    }

}
