package ecse321.fall2014.group3.bomberman.physics;

import java.util.ArrayList;
import java.util.List;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public abstract class Collidable {
    protected Vector2f position = Vector2f.ZERO;
    protected CollisionBox collisionBox = CollisionBox.NULL_BOX;
    protected boolean ghost = false;
    private final List<Collidable> collisionList = new ArrayList<>();

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
}
