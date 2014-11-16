package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;

/**
 *
 */
public abstract class Enemy extends Mob {
    public Enemy(Vector2f position, CollisionBox collisionBox) {
        super(position, collisionBox);
    }
}
