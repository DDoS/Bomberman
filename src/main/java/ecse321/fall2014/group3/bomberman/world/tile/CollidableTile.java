package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;

/**
 *
 */
public abstract class CollidableTile extends Tile {
    protected CollidableTile(Vector2f position) {
        this(position, 0.99f);
    }

    protected CollidableTile(Vector2f position, float size) {
        super(position, new CollisionBox(Vector2f.ONE.mul(size)));
    }
}
