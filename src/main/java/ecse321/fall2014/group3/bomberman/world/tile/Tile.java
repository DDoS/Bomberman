package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteTextured;
import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;

public abstract class Tile extends Collidable implements SpriteTextured {
    private static final Vector2f SIZE = Vector2f.ONE;
    static final CollisionBox COLLISION_BOX = new CollisionBox(SIZE.mul(0.99));

    protected Tile(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
