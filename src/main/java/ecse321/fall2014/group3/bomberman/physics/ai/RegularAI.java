package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.world.Map;

/**
 *
 */
public class RegularAI extends AI {
    @Override
    public Vector2f nextPosition(Entity target, long dt, Map map, Player player) {
        // TODO: implement me, for now just return the same position
        return target.getPosition();
    }
}
