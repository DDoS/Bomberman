package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector4f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;

/**
 *
 */
public abstract class UIBoxEntity extends Entity {
    public UIBoxEntity(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }

    public abstract Vector4f getColor();
}
