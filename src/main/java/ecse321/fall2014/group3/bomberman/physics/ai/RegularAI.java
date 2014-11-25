package ecse321.fall2014.group3.bomberman.physics.ai;

import java.util.Random;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Fire;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Breakable;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Unbreakable;

/**
 *
 */
public class RegularAI extends AI {
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
                if (random.nextInt(100) < 10) {
                    return intersection(map, target, enemyPos, timeSec);
                } else {
                    return returnPosition(map, target, enemyPos, playPos, timeSec);
                }
            }
        }
    }

    public Vector2f setInitial(Map map, Entity target, Vector2f enemyPos, float timeSec) {
        if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
            target.setVelocity(new Vector2f(1f, 0f));
            return enemyPos.add(1f * timeSec, 0f);
        } else if (map.isTile(enemyPos.sub(1f, 0f), Air.class)) {
            target.setVelocity(new Vector2f(-1f, 0f));
            return enemyPos.sub(1f * timeSec, 0f);
        } else if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
            target.setVelocity(new Vector2f(0f, 1f));
            return enemyPos.add(0f, 1f * timeSec);
        } else {
            target.setVelocity(new Vector2f(0f, -1f));
            return enemyPos.sub(0f, 1f * timeSec);
        }
    }

    public Vector2f intersection(Map map, Entity tar, Vector2f eP, float ts) {
        Vector2f v = tar.getVelocity();
        float xs = v.getX();
        float ys = v.getY();
        int count = getCount(map, eP, v, ts);

        // System.out.println(count);
        if (count >= 3) {
            boolean choseDir = false;
            Random r = new Random();
            while (!choseDir) {
                switch (r.nextInt(4)) {
                    case 0:
                        if (!map.isTile(eP.add(0f, 1f), Unbreakable.class) && !map.isTile(eP.add(0f, 1f), Breakable.class)) {
                            tar.setVelocity(new Vector2f(0f, 1f));
                            return eP.add(0f, 1f * ts);
                        }
                        break;
                    case 1:
                        if (!map.isTile(eP.add(1f, 0f), Unbreakable.class) && !map.isTile(eP.add(1f, 0f), Breakable.class)) {
                            tar.setVelocity(new Vector2f(1f, 0f));
                            return eP.add(1f * ts, 0f);
                        }
                        break;
                    case 2:
                        if (!map.isTile(eP.add(0f, -1f * ts), Unbreakable.class) && !map.isTile(eP.add(0f, -1f * ts), Breakable.class)) {
                            tar.setVelocity(new Vector2f(0f, -1f));
                            return eP.add(0f, -1f * ts);
                        }
                        break;
                    case 3:
                        if (!map.isTile(eP.add(-1f * ts, 0f), Unbreakable.class) && !map.isTile(eP.add(-1f * ts, 0f), Breakable.class)) {
                            tar.setVelocity(new Vector2f(-1f, 0f));
                            return eP.add(-1f * ts, 0f);
                        }
                        break;
                }
            }
        }
        return eP;
    }

    public Vector2f returnPosition(Map map, Entity tar, Vector2f eP, Vector2f playPos, float ts) {
        float xs = tar.getVelocity().getX();
        float ys = tar.getVelocity().getY();

        if (xs == 0f) {
            if (ys < 0) {
                if (!map.isTile(eP.add(0f, ts * ys), Unbreakable.class) && !map.isTile(eP.add(0f, ts * ys), Breakable.class)) {
                    return eP.add(0f, ys * ts);
                } else {
                    tar.setVelocity(new Vector2f(0f, 1f));
                    return eP.add(0f, 1f * ts);
                }
            } else {
                if (!map.isTile(eP.add(0f, ys), Unbreakable.class) && !map.isTile(eP.add(0f, ys), Breakable.class)) {
                    return eP.add(0f, ys * ts);
                } else {
                    tar.setVelocity(new Vector2f(0f, -1f));
                    return eP.add(0f, -1f * ts);
                }
            }
        } else {
            if (xs < 0) {
                if (!map.isTile(eP.add(xs * ts, 0f), Unbreakable.class) && !map.isTile(eP.add(xs * ts, 0f), Breakable.class)) {
                    return eP.add(xs * ts, 0f);
                } else {
                    tar.setVelocity(new Vector2f(1f, 0f));
                    return eP.add(1f * ts, 0f);
                }
            } else {
                if (!map.isTile(eP.add(xs, 0f), Unbreakable.class) && !map.isTile(eP.add(xs, 0f), Breakable.class)) {
                    return eP.add(xs * ts, 0f);
                } else {
                    tar.setVelocity(new Vector2f(-1f, 0f));
                    return eP.add(-1f * ts, 0f);
                }
            }
        }
    }

    public int getCount(Map map, Vector2f eP, Vector2f v, float ts) {
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

    public Vector2f followPlayer(Map map, Entity tar, Vector2f eP, Vector2f pP, float ts) {
        float ex = eP.getX();
        float ey = eP.getY();
        float px = pP.getX();
        float py = pP.getY();

        if (ex == px) {
            if (ey > py) {
                if (map.isTile(eP.add(0f, -1f * ts), Air.class) || map.isTile(eP.add(0f, -1f * ts), Fire.class)) {
                    tar.setVelocity(new Vector2f(0f, -1f));
                    return eP.add(0f, -1f * ts);
                }
            } else {
                if (map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) {
                    tar.setVelocity(new Vector2f(0f, 1f));
                    return eP.add(0f, 1f * ts);
                }
            }
        } else {
            if (ex > px) {
                if (map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                    tar.setVelocity(new Vector2f(-1f, 0f));
                    return eP.add(-1f * ts, 0f);
                }
            } else {
                if (map.isTile(eP.add(1f * ts, 0f), Air.class) || map.isTile(eP.add(1f * ts, 0f), Fire.class)) {
                    tar.setVelocity(new Vector2f(1f, 0f));
                    return eP.add(1f * ts, 0f);
                }
            }
        }
        return eP;
    }
}
