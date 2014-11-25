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
public class SmartAI extends AI {
    @Override
    public Vector2f nextPosition(Enemy target, long dt, Map map, Player player) {
        float timeSec = dt / TO_SECS;
        Vector2f enemyPos = target.getPosition();
        Vector2f playPos = player.getPosition();

        if ((target.getVelocity()).length() == 0) {
            return setInitial(map, target, enemyPos, timeSec);
        } else {
            if (enemyPos.distance((double) playPos.getX(), (double) playPos.getY()) <= 2.1f) {
                return followPlayer(map, target, enemyPos, playPos, timeSec);
            } else {
                return returnPosition(map, target, enemyPos, playPos, timeSec);
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

    public Vector2f returnPosition(Map map, Entity tar, Vector2f eP, Vector2f playPos, float ts) {
        Random r = new Random();
        int choice = r.nextInt(100);
        Vector2f v = tar.getVelocity();
        float xs = v.getX();
        float ys = v.getY();
        int[] openSpace = fillArray(map, eP, v, ts);
        int count = 0, forward = 0;

        for (int i : openSpace) {
            if (openSpace[i] != 2) {
                count += openSpace[i];
            } else {
                forward = i;
            }
        }

        if (forward == 0) {
            if ((map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) &&
                    (choice < 50) && (count >= 2)) {
                return chooseDirPosition(openSpace, map, tar, ts);
            } else if (map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) {
                return eP.add(0f, 1f * ts);
            } else {
                tar.setVelocity(new Vector2f(0f, -1f));
                return eP.add(0f, -1f * ts);
            }
        } else if (forward == 1) {
            if ((map.isTile(eP.add(1f, 0f), Air.class) || map.isTile(eP.add(1f, 0f), Fire.class)) &&
                    (choice < 50) && (count >= 2)) {
                return chooseDirPosition(openSpace, map, tar, ts);
            } else if (map.isTile(eP.add(1f, 0f), Air.class) || map.isTile(eP.add(1f, 0f), Fire.class)) {
                return eP.add(1f * ts, 0f);
            } else {
                tar.setVelocity(new Vector2f(-1f, 0f));
                return eP.add(-1f * ts, 0f);
            }
        } else if (forward == 2) {
            if ((map.isTile(eP.add(0f, -1f), Air.class) || map.isTile(eP.add(0f, -1f), Fire.class)) &&
                    (choice < 50) && (count >= 2)) {
                return chooseDirPosition(openSpace, map, tar, ts);
            } else if (map.isTile(eP.add(0f, -1f), Air.class) || map.isTile(eP.add(0f, -1f), Fire.class)) {
                return eP.add(0f, -1f * ts);
            } else {
                tar.setVelocity(new Vector2f(0f, 1f));
                return eP.add(0f, 1f * ts);
            }
        } else {
            if ((map.isTile(eP.add(-1f, 0f), Air.class) || map.isTile(eP.add(-1f, 0f), Fire.class)) &&
                    (choice < 50) && (count >= 2)) {
                return chooseDirPosition(openSpace, map, tar, ts);
            } else if (map.isTile(eP.add(-1f, 0f), Air.class) || map.isTile(eP.add(-1f, 0f), Fire.class)) {
                return eP.add(-1f * ts, 0f);
            } else {
                tar.setVelocity(new Vector2f(1f, 0f));
                return eP.add(1f * ts, 0f);
            }
        }
    }

    public Vector2f chooseDirPosition(int[] oSpace, Map map, Entity tar, float ts) {
        Random ran = new Random();
        Vector2f eP = tar.getPosition();
        int dir = ran.nextInt(oSpace.length);

        while (oSpace[dir] == 0) {
            dir = ran.nextInt(oSpace.length);
        }

        if (dir == 0) {
            if ((map.isTile(eP.add(0f, 1f * (1f + ts)), Air.class) || map.isTile(eP.add(0f, 1f * (1f + ts)), Fire.class)) &&
                    !map.isTile(eP.add(0f, 1f * (1f + ts)), Unbreakable.class) && !map.isTile(eP.add(0f, 1f * (1f + ts)), Breakable.class)) {
                return eP.add(0f, 1f * ts);
            } else {
                tar.setVelocity(new Vector2f(0f, -1f));
                return eP.add(0f, -1f * ts);
            }
        } else if (dir == 1) {
            if ((map.isTile(eP.add(1f * (1f + ts), 0f), Air.class) || map.isTile(eP.add(1f * (1f + ts), 0f), Fire.class)) &&
                    !map.isTile(eP.add(1f * (1f + ts), 0f), Unbreakable.class) && !map.isTile(eP.add(1f * (1f + ts), 0f), Breakable.class)) {
                return eP.add(1f * ts, 0f);
            } else {
                tar.setVelocity(new Vector2f(-1f, 0f));
                return eP.add(-1f * ts, 0f);
            }
        } else if (dir == 2) {
            if ((map.isTile(eP.add(0f, -1f * ts), Air.class) || map.isTile(eP.add(0f, -1f * ts), Fire.class)) &&
                    !map.isTile(eP.add(0f, -1f * ts), Unbreakable.class) && !map.isTile(eP.add(0f, -1f * ts), Breakable.class)) {
                return eP.add(0f, -1f * ts);
            } else {
                tar.setVelocity(new Vector2f(0f, 1f));
                return eP.add(0f, 1f * ts);
            }
        } else {
            if ((map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) &&
                    !map.isTile(eP.add(-1f * ts, 0f), Air.class) && !map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                return eP.add(-1f * ts, 0f);
            } else {
                tar.setVelocity(new Vector2f(1f, 0f));
                return eP.add(1f * ts, 0f);
            }
        }
    }

    public int[] fillArray(Map map, Vector2f eP, Vector2f v, float ts) {
        int[] oSpace = new int[]{0, 0, 0, 0};
        float xs = v.getX();
        float ys = v.getY();

        if ((map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) &&
                !map.isTile(eP.add(0f, 1f), Unbreakable.class) && !map.isTile(eP.add(0f, 1f), Breakable.class)) {
            if (ys <= 0) {
                oSpace[0] = 1;
            } else {
                oSpace[0] = 2;
            }
        }
        if ((map.isTile(eP.add(0f, -1f * ts), Air.class) || map.isTile(eP.add(0f, -1f * ts), Fire.class)) &&
                !map.isTile(eP.add(0f, -1f * ts), Unbreakable.class) && !map.isTile(eP.add(0f, -1f * ts), Breakable.class)) {
            if (ys >= 0) {
                oSpace[2] = 1;
            } else {
                oSpace[2] = 2;
            }
        }
        if ((map.isTile(eP.add(1f, 0f), Air.class) || map.isTile(eP.add(1f, 0f), Fire.class)) &&
                !map.isTile(eP.add(1f, 0f), Unbreakable.class) && !map.isTile(eP.add(1f, 0f), Breakable.class)) {
            if (xs <= 0) {
                oSpace[1] = 1;
            } else {
                oSpace[1] = 2;
            }
        }
        if ((map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) &&
                !map.isTile(eP.add(-1f * ts, 0f), Unbreakable.class) && !map.isTile(eP.add(-1f * ts, 0f), Breakable.class)) {
            if (xs >= 0) {
                oSpace[3] = 1;
            } else {
                oSpace[3] = 2;
            }
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
                } else if (!map.isTile(eP.add(0f, -1f * ts), Air.class) && !map.isTile(eP.add(0f, -1f * ts), Fire.class)) {
                    return aStar(map, tar, eP, pP, ts);
                }
            } else {
                if (map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) {
                    tar.setVelocity(new Vector2f(0f, 1f));
                    return eP.add(0f, 1f * ts);
                } else if (!map.isTile(eP.add(0f, 1f), Air.class) && !map.isTile(eP.add(0f, 1f), Fire.class)) {
                    return aStar(map, tar, eP, pP, ts);
                }
            }
        } else {
            if (ex > px) {
                if (map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                    tar.setVelocity(new Vector2f(-1f, 0f));
                    return eP.add(-1f * ts, 0f);
                } else if (!map.isTile(eP.add(-1f * ts, 0f), Air.class) && !map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                    return aStar(map, tar, eP, pP, ts);
                }
            } else {
                if (map.isTile(eP.add(1f * ts, 0f), Air.class) || map.isTile(eP.add(1f * ts, 0f), Fire.class)) {
                    tar.setVelocity(new Vector2f(1f, 0f));
                    return eP.add(1f * ts, 0f);
                } else if (!map.isTile(eP.add(1f * ts, 0f), Air.class) && !map.isTile(eP.add(1f * ts, 0f), Fire.class)) {
                    return aStar(map, tar, eP, pP, ts);
                }
            }
        }
        return eP;
    }

    public Vector2f aStar(Map map, Entity tar, Vector2f eP, Vector2f pP, float ts) {
        float ex = eP.getX();
        float ey = eP.getY();
        float px = pP.getX();
        float py = pP.getY();
        float vx = tar.getVelocity().getX();
        float vy = tar.getVelocity().getY();

        if (vx == 0f && ex > px) {
            if (map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) {
                tar.setVelocity(new Vector2f(0f, 1f));
                return eP.add(0f, 1f * ts);
            } else if (map.isTile(eP.add(0f, -1f * ts), Air.class) || map.isTile(eP.add(0f, -1f * ts), Fire.class)) {
                tar.setVelocity(new Vector2f(0f, -1f));
                return eP.add(0f, -1f * ts);
            } else if (map.isTile(eP.add(1f, 0f), Air.class) || map.isTile(eP.add(1f, 0f), Fire.class)) {
                tar.setVelocity(new Vector2f(1f, 0f));
                return eP.add(1f * ts, 0f);
            }
        }
        if (vx == 0f && ex < px) {
            if (map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) {
                tar.setVelocity(new Vector2f(0f, 1f));
                return eP.add(0f, 1f * ts);
            } else if (map.isTile(eP.add(0f, -1f * ts), Air.class) || map.isTile(eP.add(0f, -1f * ts), Fire.class)) {
                tar.setVelocity(new Vector2f(0f, -1f));
                return eP.add(0f, -1f * ts);
            } else if (map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                tar.setVelocity(new Vector2f(-1f, 0f));
                return eP.add(-1f * ts, 0f);
            }
        }
        if (vy == 0f && ey < py) {
            if (map.isTile(eP.add(1f, 0f), Air.class) || map.isTile(eP.add(1f, 0f), Fire.class)) {
                tar.setVelocity(new Vector2f(1f, 0f));
                return eP.add(1f * ts, 0f);
            } else if (map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                tar.setVelocity(new Vector2f(-1f, 0f));
                return eP.add(-1f * ts, 0f);
            } else if (map.isTile(eP.add(0f, -1f * ts), Air.class) || map.isTile(eP.add(0f, -1f * ts), Fire.class)) {
                tar.setVelocity(new Vector2f(0f, -1f));
                return eP.add(0f, -1f * ts);
            }
        }
        if (vy == 0f && ey > py) {
            if (map.isTile(eP.add(1f, 0f), Air.class) || map.isTile(eP.add(1f, 0f), Fire.class)) {
                tar.setVelocity(new Vector2f(1f, 0f));
                return eP.add(1f * ts, 0f);
            } else if (map.isTile(eP.add(-1f * ts, 0f), Air.class) || map.isTile(eP.add(-1f * ts, 0f), Fire.class)) {
                tar.setVelocity(new Vector2f(-1f, 0f));
                return eP.add(-1f * ts, 0f);
            } else if (map.isTile(eP.add(0f, 1f), Air.class) || map.isTile(eP.add(0f, 1f), Fire.class)) {
                tar.setVelocity(new Vector2f(0f, 1f));
                return eP.add(0f, 1f);
            }
        }

        return eP;
    }
}
