/**
 * @author Group 3
*/
package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;


/**
 * The Class Enemy.
 */
public abstract class Enemy extends Mob {
    private static final Vector2f SIZE = Vector2f.ONE;
    private static final CollisionBox COLLISION_BOX = new CollisionBox(SIZE.mul(0.8f));
    
    /** The Constant ENEMY_BASE_SPEED. */
    public static final float ENEMY_BASE_SPEED = 2;

    /**
     * Instantiates a new enemy.
     *
     * @param position the position
     */
    public Enemy(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    /**
     * Gets the AI.
     *
     * @return the AI
     */
    public abstract AI getAI();

    /**
     * Gets the of the enemy.
     *
     * @return the speed
     */
    public abstract float getSpeed();

    /**
     * Checks if enemy can wall pass.
     *
     * @return true, if is wall pass
     */
    public abstract boolean isWallPass();

    /**
     * Gets the score of the enemy.
     *
     * @return the score
     */
    public abstract int getScore();

    @Override
    public Vector2f getModelSize() {
        return SIZE;
    }
}
