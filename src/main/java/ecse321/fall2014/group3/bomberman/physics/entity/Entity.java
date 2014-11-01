/*Stuff to fix or change:
 * 
 * 1. Unsure about type of textureInfo variable
 * 2. Unsure about collidingWith method algorithms
 * 3. did not include class inheritances because collidable and snapshottable have not been implemented
 * 
 */
package ecse321.fall2014.group3.bomberman.physics.entity;

import java.util.Collections;
import java.util.List;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

public abstract class Entity extends Collidable {
    protected Vector2f velocity = Vector2f.ZERO;
    protected Direction direction = Direction.UP;

    public Vector2f getVelocity() {
        return velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Entity> getEntityCollisionList() {
        // TODO: implement me
        return Collections.EMPTY_LIST;
    }

    public List<Tile> getTileCollisionList() {
        // TODO: implement me
        return Collections.EMPTY_LIST;
    }

    public boolean isCollidingWith(Tile tile) {
        // TODO: implement me
        return false;
    }

    public boolean isCollidingWith(Entity entity) {
        // TODO: implement me
        return false;
    }

    //	public abstract getTextureInfo();
}
