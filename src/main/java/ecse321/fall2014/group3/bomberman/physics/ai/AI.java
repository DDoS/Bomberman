package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.world.Map;

/**
 * The AI superclass.
 *
 * @author Lianna Yang
 */
public abstract class AI {
    /**
    * Float used to convert the poll time from nanoseconds to seconds
    */
    protected static final float TO_SECS = 1e9f;
    
    /**
    * Returns a Vector2f that represents the next position of the Enemy
    * 
    * @param target The enemy to apply the AI on
    * @param dt The time since the last AI update
    * @param map The map in which the enemy and player are
    * @param player The player, to chase if needed
    * @return Vector2f The next position of the Enemy
    */
    public abstract Vector2f nextPosition(Enemy target, long dt, Map map, Player player);
}
