package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;
import ecse321.fall2014.group3.bomberman.world.Map;

/**
 *
 */
public abstract class AI {
    protected final Mob target;

    protected AI(Mob target) {
        this.target = target;
    }

    public abstract Vector2f nextPosition(long dt, Map map);
}
