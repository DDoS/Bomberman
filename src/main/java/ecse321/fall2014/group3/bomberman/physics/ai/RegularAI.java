package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.world.Map;

import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

import java.util.Random;
import java.util.LinkedList;

/**
 *
 */
public class RegularAI extends AI {
   @Override
    public Vector2f nextPosition(Entity target, long dt, Map map, Player player) {
      float timeSec = dt / TO_SECS;
      Vector2f enemyPos = target.getPosition();
      Vector2f eVelocity = target.getVelocity();
      Vector2f playPos = player.getPosition();
      
      if (eVelocity.length() == 0) {
         if (map.isTile(enemyPos.add(1f, 0f), Air.class)) {
            target.setVelocity(new Vector2f(1f, 0f));
            return enemyPos.add(1f * timeSec, 0f);
         }
         else if (map.isTile(enemyPos.sub(1f, 0f), Air.class)) {
            target.setVelocity(new Vector2f(-1f, 0f));
            return enemyPos.sub(1f * timeSec, 0f);
         }
         else if (map.isTile(enemyPos.add(0f, 1f), Air.class)) {
            target.setVelocity(new Vector2f(0f, 1f));
            return enemyPos.add(0f, 1f * timeSec);
         }
         else {
            target.setVelocity(new Vector2f(0f, -1f));
            return enemyPos.sub(0f, 1f * timeSec);
         }
      } 
      else {
         if (enemyPos.distance(playPos) <= 1.5f) {
            float ex = enemyPos.getX();
            float ey = enemyPos.getY();
            float px = playPos.getX();
            float py = playPos.getY();
         
            if (ex == px) {
               if (ey > py) {
                  target.setVelocity(new Vector2f(0f, -1f));
                  return enemyPos.add(0f, -1f * timeSec);
               }
               else {
                  target.setVelocity(new Vector2f(0f, 1f));
                  return enemyPos.add(0f, 1f * timeSec);
               }
            }
            else {
               if (ex > px) {
                  target.setVelocity(new Vector2f(-1f, 0f));
                  return enemyPos.add(-1f * timeSec, 0f);
               }
               else {
                  target.setVelocity(new Vector2f(1f, 0f));
                  return enemyPos.add(1f * timeSec, 0f);
               }
            }
         }
         else {
            LinkedList intersection = returnIntersections(map, enemyPos, timeSec);
            
            if (checkForAir(map, enemyPos, eVelocity, timeSec)) {
               return returnPosition(target, intersection, enemyPos, eVelocity, timeSec, false);
            }
            else {
               return returnPosition(target, intersection, enemyPos, eVelocity, timeSec, true);
            }
         }
      } 
   }
   
   public LinkedList returnIntersections(Map map, Vector2f eP, float ts) {
      LinkedList i = new LinkedList();
      if (map.isTile(eP.add(1f, 0f), Air.class)) {
         i.add("right");
      }
      if (map.isTile(eP.add(0f, 1f), Air.class)) {
         i.add("up");
      }
      if (map.isTile(eP.add(-1f * ts, 0f), Air.class)) {
         i.add("left");
      }
      if (map.isTile(eP.add(0f, -1f * ts), Air.class)) {
         i.add("down");
      }
      return i;
   }
   
   public boolean checkForAir(Map map, Vector2f eP, Vector2f vel, float ts) {
      float xs = vel.getX();
      float ys = vel.getY();
   
      if (xs == 0) {
         if (ys < 0 && map.isTile(eP.add(0f, ts * ys), Air.class)) {
            return true;
         }
         else if (ys > 0 && map.isTile(eP.add(0f, ys), Air.class)) {
            return true;
         }
      }
      else {
         if (xs < 0 && map.isTile(eP.add(xs * ts, 0f), Air.class)) {
            return true;
         }
         else if (xs > 0 && map.isTile(eP.add(xs, 0f), Air.class)) {
            return true;
         }
      }
      return false;
   }  
   public Vector2f returnPosition(Entity tar, LinkedList ll, Vector2f eP, Vector2f vel, float ts, boolean collide) {
   return null;
   }
}
