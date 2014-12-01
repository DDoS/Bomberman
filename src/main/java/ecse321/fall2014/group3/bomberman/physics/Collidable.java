package ecse321.fall2014.group3.bomberman.physics;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.flowpowered.math.vector.Vector2f;

/**
 * A collidable object, which can be feed to {@link ecse321.fall2014.group3.bomberman.physics.SweepAndPruneAlgorithm} to perform collision detection. It has a position and a {@link
 * ecse321.fall2014.group3.bomberman.physics.CollisionBox}, including a collision list of other collidables that are colliding with it.
 */
public abstract class Collidable {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);
    private final int id = ID_COUNTER.getAndIncrement();
    private final Set<Collidable> collisionList = Collections.newSetFromMap(new ConcurrentHashMap<Collidable, Boolean>());
    protected volatile Vector2f position = Vector2f.ZERO;
    protected volatile CollisionBox collisionBox;

    /**
     * Constructs a new collidable from its initial position and its collision box.
     *
     * @param position The initial position
     * @param collisionBox The collision box
     */
    protected Collidable(Vector2f position, CollisionBox collisionBox) {
        this.position = position;
        this.collisionBox = collisionBox;
    }

    /**
     * Returns the position of the collidable
     *
     * @return The position
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Sets the position of the collidable. <b>For use by the physics thread only!</b>
     *
     * @param position The new position
     */
    protected void setPosition(Vector2f position) {
        this.position = position;
    }

    /**
     * Returns whether or not this collidable can actually report collisions.
     *
     * @return Whether or not collision is enabled for this collidable
     */
    public boolean isCollisionEnabled() {
        return collisionBox != null;
    }

    /**
     * Returns the collision box.
     *
     * @return The collision box
     */
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    /**
     * Returns whether or not this collidable is a "ghost", that is it reports collisions, but no collision physics are performed.
     *
     * @return Whether or not this object is a "ghost" collidable
     */
    public boolean isGhost() {
        return false;
    }

    /**
     * Returns the set of collidables this collidable is colliding with. <b>Do not edit!</b>.
     *
     * @return The colliding bodies
     */
    public Set<Collidable> getCollisionList() {
        return collisionList;
    }

    /**
     * Returns whether or not this collidable is colliding with this object.
     *
     * @param collidable The collidable to check
     * @return If a collision is occurring
     */
    public boolean isCollidingWith(Collidable collidable) {
        return collisionList.contains(collidable);
    }

    /**
     * Returns whether or not this collidable is colliding with an object of this type.
     *
     * @param type The type collidable to check
     * @return If a collision is occurring
     */
    public boolean isCollidingWith(Class<? extends Collidable> type) {
        for (Collidable collidable : collisionList) {
            if (type.isAssignableFrom(collidable.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Clears the collision list. <b>For use by the physics thread only!</b>
     */
    void clearCollisionList() {
        collisionList.clear();
    }

    /**
     * Adds a colliding object to the list. <b>For use by the physics thread only!</b>
     *
     * @param colliding The object to add
     */
    void addColliding(Collidable colliding) {
        collisionList.add(colliding);
    }

    /**
     * Returns the max point of the collision box, taking in account the position
     *
     * @return The max point
     */
    Vector2f getBoxMaxPoint() {
        return collisionBox.getMaxPoint(getPosition());
    }

    /**
     * Returns the min point of the collision box, taking in account the position
     *
     * @return The min point
     */
    Vector2f getBoxMinPoint() {
        return collisionBox.getMinPoint(getPosition());
    }

    /**
     * Returns the unique ID of this collidable.
     *
     * @return The ID
     */
    int getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collidable)) {
            return false;
        }
        final Collidable that = (Collidable) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
