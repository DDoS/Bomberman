package ecse321.fall2014.group3.bomberman.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public abstract class Collidable {
    private static final AtomicInteger ID_COUNTER = new AtomicInteger(0);
    protected Vector2f position = Vector2f.ZERO;
    protected CollisionBox collisionBox = CollisionBox.NULL_BOX;
    protected boolean ghost = false;
    private final List<Collidable> collisionList = new ArrayList<>();
    private final int id = ID_COUNTER.getAndIncrement();

    public Vector2f getPosition() {
        return position;
    }

    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public boolean isGhost() {
        return ghost;
    }

    public List<Collidable> getCollisionList() {
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
