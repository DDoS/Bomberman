/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;

/**
 * The Class CollidableTile.
 */
public abstract class CollidableTile extends Tile {

    /**
     * Instantiates a new collidable tile.
     *
     * @param position the position
     */
    protected CollidableTile(Vector2f position) {
        this(position, 0.99f);
    }

    /**
     * Instantiates a new collidable tile.
     *
     * @param position the position
     * @param size the size
     */
    protected CollidableTile(Vector2f position, float size) {
        super(position, new CollisionBox(Vector2f.ONE.mul(size)));
    }
}
