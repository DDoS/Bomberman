package ecse321.fall2014.group3.bomberman.physics.entity.mob;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteTextured;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;

/**
 *
 */
public abstract class Mob extends Entity implements SpriteTextured {
    public Mob(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }
}
