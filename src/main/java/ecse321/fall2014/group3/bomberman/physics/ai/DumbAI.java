package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;
import ecse321.fall2014.group3.bomberman.world.Map;

/**
 *
 */
public class DumbAI extends AI {
    public DumbAI(Mob target) {
        super(target);
    }

    @Override
    public Vector2f nextPosition(long dt, Map map) {
        if((target.getTileCollisionList()).isEmpty())
            return (target.getPosition() + target.getVelocity()*dt);
        else {
            Random random = new Random();
            switch(random.nextInt(3)) {
            case 0:
                return (target.getPosition() + target.getVelocity()*dt).negate();
                break;
            case 1:
                return (target.getPosition() + target.getVelocity()*dt).getPerpendicular();
                break;
            case 2:
                return (target.getPosition() + target.getVelocity()*dt).getPerpendicular().negate(); 
                break; 
            }  
        }
    }
}
