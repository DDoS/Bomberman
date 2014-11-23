package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.world.Map;

/**
 *
 */
public class RegularAI extends AI {
    public RegularAI(Mob target) {
        super(target);
    }

    @Override
    public Vector2f nextPosition(long dt, Map map, Player player) {
        // TODO: implement me, for now just return the same position
        return target.getPosition();
    }
}
