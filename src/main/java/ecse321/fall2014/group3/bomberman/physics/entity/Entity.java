package ecse321.fall2014.group3.bomberman.physics.entity;

import java.util.Collections;
import java.util.Set;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.nterface.SpriteTextured;
import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

public abstract class Entity extends Collidable implements SpriteTextured {
    private static final Vector2f SIZE = Vector2f.ONE.mul(0.75f);
    static final CollisionBox COLLISION_BOX = new CollisionBox(Vector2f.ONE);
    protected volatile Vector2f velocity = Vector2f.ZERO;
    protected volatile Direction direction = Direction.UP;

    protected Entity(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
        // Only update the direction if we're actually moving, else preserve the current direction
        if (velocity.lengthSquared() >= GenericMath.FLT_EPSILON * GenericMath.FLT_EPSILON) {
            direction = Direction.fromUnit(velocity);
        }
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public Set<Entity> getEntityCollisionList() {
        // TODO: implement me
        return Collections.EMPTY_SET;
    }

    public Set<Tile> getTileCollisionList() {
        // TODO: implement me
        return Collections.EMPTY_SET;
    }

    public boolean isCollidingWith(Tile tile) {
        // TODO: implement me
        return false;
    }

    public boolean isCollidingWith(Entity entity) {
        // TODO: implement me
        return false;
    }

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
