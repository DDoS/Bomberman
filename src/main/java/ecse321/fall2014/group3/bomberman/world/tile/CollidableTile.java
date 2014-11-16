package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;

/**
 *
 */
public abstract class CollidableTile extends Tile {
    private static final CollisionBox COLLISION_BOX = new CollisionBox(Tile.SIZE.mul(0.99f));

    protected CollidableTile(Vector2f position) {
        super(position, COLLISION_BOX);
    }
}
