package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.world.Map;

/**
 * The AI superclass.  Contains the float for conversion of the poll time from seconds to nanoseconds 
 *
 * @author 
 */
public abstract class AI {
    /**
    * Float used to convert the poll time from seconds to nanoseconds
    */
    protected static final float TO_SECS = 1e9f;
    
    /**
    * Returns a Vector2f that represents the next position of the Enemy
    * 
    * @param Enemy target
    * @param long dt
    * @param Map map
    * @param Player player
    * @return Vector2f The next position of the Enemy
    */
    public abstract Vector2f nextPosition(Enemy target, long dt, Map map, Player player);
}
