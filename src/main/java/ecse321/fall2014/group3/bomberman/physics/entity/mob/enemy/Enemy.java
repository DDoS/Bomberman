package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;

/**
 *
 */
public abstract class Enemy extends Mob {
    private static final Vector2f SIZE = Vector2f.ONE;
    private static final CollisionBox COLLISION_BOX = new CollisionBox(SIZE);
    public static final float ENEMY_BASE_SPEED = 2;

    public Enemy(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    public abstract AI getAI();

    public abstract float getSpeed();

    public abstract boolean isWallPass();

    public abstract float getScore();

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
