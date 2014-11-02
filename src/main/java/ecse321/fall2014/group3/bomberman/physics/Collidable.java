package ecse321.fall2014.group3.bomberman.physics;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public abstract class Collidable {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);
    private final int id = ID_COUNTER.getAndIncrement();
    private final Set<Collidable> collisionList = Collections.synchronizedSet(new HashSet<Collidable>());
    protected volatile Vector2f position = Vector2f.ZERO;
    protected volatile CollisionBox collisionBox;
    protected volatile boolean ghost = false;

    protected Collidable(Vector2f position, CollisionBox collisionBox) {
        this.position = position;
        this.collisionBox = collisionBox;
    }

    public Vector2f getPosition() {
        return position;
    }

    protected void setPosition(Vector2f position) {
        this.position = position;
    }

    public boolean isCollisionEnabled() {
        return collisionBox != null;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public boolean isGhost() {
        return ghost;
    }

    public Set<Collidable> getCollisionList() {
        return collisionList;
    }

    void clearCollisionList() {
        collisionList.clear();
    }

    void addColliding(Collidable colliding) {
        collisionList.add(colliding);
    }

    Vector2f getBoxMaxPoint() {
        return collisionBox.getMaxPoint(getPosition());
    }

    Vector2f getBoxMinPoint() {
        return collisionBox.getMinPoint(getPosition());
    }

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
