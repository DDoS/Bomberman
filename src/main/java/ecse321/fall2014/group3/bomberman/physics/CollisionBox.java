package ecse321.fall2014.group3.bomberman.physics;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public class CollisionBox {
    static final CollisionBox NULL_BOX = new CollisionBox(Vector2f.ZERO);
    private final Vector2f size;
    private final Vector2f halfSize;

    public CollisionBox(Vector2f size) {
        this.size = size;
        halfSize = size.div(2);
    }

    public Vector2f getSize() {
        return size;
    }

    Vector2f getMaxPoint(Vector2f position) {
        return position.add(halfSize);
    }

    Vector2f getMinPoint(Vector2f position) {
        return position.sub(halfSize);
    }
}
