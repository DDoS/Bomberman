/**
* @author Lianna Yang
*/
package ecse321.fall2014.group3.bomberman.physics.ai;

import java.util.Random;
import java.lang.Math;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Fire;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Bomb;
import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Breakable;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Unbreakable;

/**
 * The RegularAI Class
 */
public class RegularAI extends AI {
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
        Vector2f playPos = player.getPosition();
        Random random = new Random();

        if ((target.getVelocity()).length() == 0) {
            return setInitial(map, target, enemyPos, timeSec);
        } else {
            if (enemyPos.distance((double) playPos.getX(), (double) playPos.getY()) <= 1.5f) {
                return followPlayer(map, target, enemyPos, playPos, timeSec);
            } else {
                float threshold = target.getSpeed() * timeSec;
                boolean inThreshold = false;
                boolean atInteger = false;
                
                if((target.getVelocity()).getX() == 0) {
                    if(enemyPos.getY() % 1f < threshold) {
                        inThreshold = true;
                    }
                } else {
                    if(enemyPos.getX() % 1f < threshold) {
                        inThreshold = true;
                    }
                }
                
                if (inThreshold && random.nextInt(100) < 10) {
                    return intersection(map, target, enemyPos, timeSec);
                } else {
                    if (target.isWallPass()) {
                        return returnWallPos(map, target, enemyPos, timeSec);
                    } else {
                        return returnPosition(map, target, enemyPos, timeSec);
                    }
                }
            }
        }
    }
    
    //Sets the initial velocity to have the speed defined by the Enemy
    //Sets the direction of the initial velocity to be the first open direction
    private Vector2f setInitial(Map map, Enemy target, Vector2f enemyPos, float timeSec) {
        float v = target.getSpeed();
        if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
            target.setVelocity(new Vector2f(v, 0f));
            return enemyPos.add(v * timeSec, 0f);
        } else if (map.isTile(enemyPos.sub(1f, 0f), Air.class)) {
            target.setVelocity(new Vector2f(-v, 0f));
            return enemyPos.sub(v * timeSec, 0f);
        } else if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
            target.setVelocity(new Vector2f(0f, v));
            return enemyPos.add(0f, v * timeSec);
        } else {
            target.setVelocity(new Vector2f(0f, -v));
            return enemyPos.sub(0f, v * timeSec);
        }
    }
    
    //Counts the number of potential pathways at an intersection and chooses one
    private Vector2f intersection(Map map, Enemy tar, Vector2f eP, float ts) {
        Vector2f v = tar.getVelocity();
        float xs = v.getX();
        float ys = v.getY();
        float spd = tar.getSpeed();
        int count = getCount(map, eP, v, ts);

        if (count >= 3) {
            boolean choseDir = false;
            Random r = new Random();
            while (!choseDir) {
                switch (r.nextInt(4)) {
                    case 0:
                        if (!map.isTile(eP.add(0f, 1f), Unbreakable.class) && !map.isTile(eP.add(0f, 1f), Breakable.class)) {
                            tar.setVelocity(new Vector2f(0f, spd));
                            return eP.add(0f, spd * ts);
                        }
                        break;
                    case 1:
                        if (!map.isTile(eP.add(1f, 0f), Unbreakable.class) && !map.isTile(eP.add(1f, 0f), Breakable.class)) {
                            tar.setVelocity(new Vector2f(spd, 0f));
                            return eP.add(spd * ts, 0f);
                        }
                        break;
                    case 2:
                        if (!map.isTile(eP.add(0f, -spd * ts), Unbreakable.class) && !map.isTile(eP.add(0f, -spd * ts), Breakable.class)) {
                            tar.setVelocity(new Vector2f(0f, -spd));
                            return eP.add(0f, -spd * ts);
                        }
                        break;
                    case 3:
                        if (!map.isTile(eP.add(-spd * ts, 0f), Unbreakable.class) && !map.isTile(eP.add(-spd * ts, 0f), Breakable.class)) {
                            tar.setVelocity(new Vector2f(-spd, 0f));
                            return eP.add(-spd * ts, 0f);
                        }
                        break;
                }
            }
        }
        return eP;
    }

    //For enemies with wall pass enabled
    //Checks for Air, Fire, and Breakable wall tiles as possible positions
    private Vector2f returnWallPos(Map map, Enemy tar, Vector2f eP, float ts) {
        float xs = tar.getVelocity().getX();
        float ys = tar.getVelocity().getY();

        if (xs == 0f) {
            if (ys < 0) {
                if (map.isTile(eP.add(0f, ts * ys), Air.class) ||
                    map.isTile(eP.add(0f, ts * ys), Breakable.class) ||
                    map.isTile(eP.add(0f, ts * ys), Fire.class)) {
                    return eP.add(0f, ys * ts);
                } else {
                    tar.setVelocity(new Vector2f(0f, -ys));
                    return eP.add(0f, -ys * ts);
                }
            } else {
                if (map.isTile(eP.add(0f, 1f), Air.class) ||
                    map.isTile(eP.add(0f, 1f), Breakable.class) ||
                    map.isTile(eP.add(0f, 1f), Fire.class)) {
                    return eP.add(0f, ys * ts);
                } else {
                    tar.setVelocity(new Vector2f(0f, -ys));
                    return eP.add(0f, -ys * ts);
                }
            }
        } else {
            if (xs < 0) {
                if (map.isTile(eP.add(xs * ts, 0f), Air.class) ||
                    map.isTile(eP.add(xs * ts, 0f), Breakable.class) ||
                    map.isTile(eP.add(xs * ts, 0f), Fire.class)) {
                    return eP.add(xs * ts, 0f);
                } else {
                    tar.setVelocity(new Vector2f(-xs, 0f));
                    return eP.add(-xs * ts, 0f);
                }
            } else {
                if (map.isTile(eP.add(1f, 0f), Air.class) ||
                    map.isTile(eP.add(1f, 0f), Breakable.class) ||
                    map.isTile(eP.add(1f, 0f), Fire.class)) {
                    return eP.add(xs * ts, 0f);
                } else {
                    tar.setVelocity(new Vector2f(-xs, 0f));
                    return eP.add(-xs * ts, 0f);
                }
            }
        }
    }

    //For enemies without wall pass
    //Any tile that is not a wall or a bomb can be passed through
    private Vector2f returnPosition(Map map, Enemy tar, Vector2f eP, float ts) {
        float xs = tar.getVelocity().getX();
        float ys = tar.getVelocity().getY();

        if (xs == 0f) {
            if (ys < 0) {
                if (!map.isTile(eP.add(0f, ts * ys), CollidableTile.class) && !map.isTile(eP.add(0f, ts * ys), Bomb.class)) {
                    return eP.add(0f, ys * ts);
                } else {
                    tar.setVelocity(new Vector2f(0f, -ys));
                    return eP.add(0f, -ys * ts);
                }
            } else {
                if (!map.isTile(eP.add(0f, 1f), CollidableTile.class) && !map.isTile(eP.add(0f, 1f), Bomb.class)) {
                    return eP.add(0f, ys * ts);
                } else {
                    tar.setVelocity(new Vector2f(0f, -ys));
                    return eP.add(0f, -ys * ts);
                }
            }
        } else {
            if (xs < 0) {
                if (!map.isTile(eP.add(xs * ts, 0f), CollidableTile.class) && !map.isTile(eP.add(xs * ts, 0f), Bomb.class)) {
                    return eP.add(xs * ts, 0f);
                } else {
                    tar.setVelocity(new Vector2f(-xs, 0f));
                    return eP.add(-xs * ts, 0f);
                }
            } else {
                if (!map.isTile(eP.add(1f, 0f), CollidableTile.class) && !map.isTile(eP.add(1f, 0f), Bomb.class)) {
                    return eP.add(xs * ts, 0f);
                } else {
                    tar.setVelocity(new Vector2f(-xs, 0f));
                    return eP.add(-xs * ts, 0f);
                }
            }
        }
    }

    //Gets the number of directions with open spaces at an intersection
    private int getCount(Map map, Vector2f eP, Vector2f v, float ts) {
        int oSpace = 0;
        float xs = v.getX();
        float ys = v.getY();

        if (!map.isTile(eP.add(0f, 1f), Unbreakable.class) && !map.isTile(eP.add(0f, 1f), Breakable.class)) {
            oSpace++;
        }
        if (!map.isTile(eP.add(0f, -1f * ts), Unbreakable.class) && !map.isTile(eP.add(0f, -1f * ts), Breakable.class)) {
            oSpace++;
        }
        if (!map.isTile(eP.add(1f, 0f), Unbreakable.class) && !map.isTile(eP.add(1f, 0f), Breakable.class)) {
            oSpace++;
        }
        if (!map.isTile(eP.add(-1f * ts, 0f), Unbreakable.class) && !map.isTile(eP.add(-1f * ts, 0f), Breakable.class)) {
            oSpace++;
        }
        return oSpace;
    }

    //Follows the player based on the direction relative to the enemy
    private Vector2f followPlayer(Map map, Enemy tar, Vector2f eP, Vector2f pP, float ts) {
        float ex = eP.getX();
        float ey = eP.getY();
        float px = pP.getX();
        float py = pP.getY();
        float spd = Math.abs(tar.getSpeed());

        if (Math.abs(ex - px) < spd * ts && ey > py) {
            if (!map.isTile(eP.add(0f, -spd * ts), CollidableTile.class) && !map.isTile(eP.add(0f, -spd * ts), Bomb.class)) {
                tar.setVelocity(new Vector2f(0f, -spd));
                return eP.add(0f, -spd * ts);
            }
        } else if (Math.abs(ex - px) < spd * ts && ey < py){
            if (!map.isTile(eP.add(0f, 1f), CollidableTile.class) && !map.isTile(eP.add(0f, 1f), Bomb.class)) {
                tar.setVelocity(new Vector2f(0f, spd));
                return eP.add(0f, spd * ts);
            }
        } else if (Math.abs(ey - py) < spd * ts && ex > px) {
            if (!map.isTile(eP.add(-spd * ts, 0f), CollidableTile.class) && !map.isTile(eP.add(-spd * ts, 0f), Bomb.class)) {
                tar.setVelocity(new Vector2f(-spd, 0f));
                return eP.add(-spd * ts, 0f);
            }
        } else if (Math.abs(ey - py) < spd * ts && ex < px) {
                if (!map.isTile(eP.add(1f, 0f), CollidableTile.class) && !map.isTile(eP.add(1f, 0f), Bomb.class)) {
                    tar.setVelocity(new Vector2f(spd, 0f));
                    return eP.add(spd * ts, 0f);
                }
        }
        return eP;
    }
}
