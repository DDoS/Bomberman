/**
* @author Lianna Yang
*/
package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Fire;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Breakable;

/**
 * The Class DumbAI
 */
public class DumbAI extends AI {
    /**
    * Override of the method in the AI superclass
    *
    * AI moves in one direction until it hits a bomb or wall and then turns around.
    *
    * Sets an initial nonzero velocity to the Enemy.
    * If the enemy has the wall pass ability, check that the next space is Air, Fire, or a Breakable wall.
    * If the enemy does not have the wall pass ability, check that the next space is Air or Fire.
    * If the next space is empty, set the new position to the velocity * poll time.
    * If the next space is a wall, set the Enemy velocity to -velocity and set the new position to
    * -velocity * poll time.
    *
    * @param Enemy target
    * @param long dt
    * @param Map map
    * @param Player player
    */
    @Override
    public Vector2f nextPosition(Enemy target, long dt, Map map, Player player) {
        float timeSec = dt / TO_SECS;
        Vector2f enemyPos = target.getPosition();
        Vector2f eVelocity = target.getVelocity();
        float eSpeed = target.getSpeed();

        if (eVelocity.length() == 0) {
            if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
                target.setVelocity(new Vector2f(eSpeed, 0f));
                return enemyPos.add(1f * timeSec, 0f);
            } else if (map.isTile(enemyPos.add(-1f * timeSec, 0f), Air.class)) {
                target.setVelocity(new Vector2f(-eSpeed, 0f));
                return enemyPos.sub(eSpeed * timeSec, 0f);
            } else if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
                target.setVelocity(new Vector2f(0f, eSpeed));
                return enemyPos.add(0f, eSpeed * timeSec);
            } else {
                target.setVelocity(new Vector2f(0f, -eSpeed));
                return enemyPos.sub(0f, eSpeed * timeSec);
            }
        } else if (!target.isWallPass()) {
            float xs = eVelocity.getX();
            float ys = eVelocity.getY();

            if (xs == 0f) {
                if (ys < 0) {
                    if (map.isTile(enemyPos.add(0f, timeSec * ys), Air.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else if (map.isTile(enemyPos.add(0f, timeSec * ys), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, eSpeed));
                        return enemyPos.add(0f, eSpeed * timeSec);
                    }
                } else {
                    if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else if (map.isTile(enemyPos.add(0f, 1f), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, -eSpeed));
                        return enemyPos.add(0f, -eSpeed * timeSec);
                    }
                }
            } else {
                if (xs < 0) {
                    if (map.isTile(enemyPos.add(xs * timeSec, 0f), Air.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else if (map.isTile(enemyPos.add(xs * timeSec, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(eSpeed, 0f));
                        return enemyPos.add(eSpeed * timeSec, 0f);
                    }
                } else {
                    if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else if (map.isTile(enemyPos.add(1f, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(-eSpeed, 0f));
                        return enemyPos.add(-eSpeed * timeSec, 0f);
                    }
                }
            }        
        } else {
            float xs = eVelocity.getX();
            float ys = eVelocity.getY();

            if (xs == 0f) {
                if (ys < 0) {
                    if (map.isTile(enemyPos.add(0f, timeSec * ys), Air.class) ||
                        map.isTile(enemyPos.add(0f, timeSec * ys), Breakable.class) ||
                        map.isTile(enemyPos.add(0f, timeSec * ys), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, eSpeed));
                        return enemyPos.add(0f, eSpeed * timeSec);
                    }
                } else {
                    if (map.isTile(enemyPos.add(0f, 1f), Air.class) ||
                        map.isTile(enemyPos.add(0f, 1f), Breakable.class) ||
                        map.isTile(enemyPos.add(0f, 1f), Fire.class)) {
                        return enemyPos.add(0f, ys * timeSec);
                    } else {
                        target.setVelocity(new Vector2f(0f, -eSpeed));
                        return enemyPos.add(0f, -eSpeed * timeSec);
                    }
                }
            } else {
                if (xs < 0) {
                    if (map.isTile(enemyPos.add(xs * timeSec, 0f), Air.class) ||
                        map.isTile(enemyPos.add(xs * timeSec, 0f), Breakable.class) ||
                        map.isTile(enemyPos.add(xs * timeSec, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(eSpeed, 0f));
                        return enemyPos.add(eSpeed * timeSec, 0f);
                    }
                } else {
                    if (map.isTile(enemyPos.add(1f, 0f), Air.class) ||
                        map.isTile(enemyPos.add(1f, 0f), Breakable.class) ||
                        map.isTile(enemyPos.add(1f, 0f), Fire.class)) {
                        return enemyPos.add(xs * timeSec, 0f);
                    } else {
                        target.setVelocity(new Vector2f(-eSpeed, 0f));
                        return enemyPos.add(-eSpeed * timeSec, 0f);
                    }
                }
            }
        }
    }
}
