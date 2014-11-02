package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;

public abstract class Tile extends Collidable {
    static final CollisionBox COLLISION_BOX = new CollisionBox(Vector2f.ONE.mul(0.97));

    protected Tile(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }
}
