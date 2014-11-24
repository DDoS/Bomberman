package ecse321.fall2014.group3.bomberman.physics.ai;

import java.util.Random;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

/**
 *
 */
public class DumbAI extends AI {
   @Override
   public Vector2f nextPosition(Entity target, long dt, Map map, Player player) {
      float timeSec = dt / TO_SECS;
      Vector2f enemyPos = target.getPosition();
      Vector2f eVelocity = target.getVelocity();
   
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
         float xs = eVelocity.getX();
         float ys = eVelocity.getY();
         
         if (xs == 0f) {
            if (ys < 0) {
               if (map.isTile(enemyPos.add(0f, timeSec * ys), Air.class)) {
                  return enemyPos.add(0f, ys * timeSec);
               }
               else {
                  target.setVelocity(new Vector2f(0f, 1f));
                  return enemyPos.add(0f, 1f * timeSec);
               }
            }
            else {
               if (map.isTile(enemyPos.add(0f, ys), Air.class)) {
                  return enemyPos.add(0f, ys * timeSec);
               }
               else {
                  target.setVelocity(new Vector2f(0f, -1f));
                  return enemyPos.add(0f, -1f * timeSec);
               }
            }
         }
         else {
            if (xs < 0) {
               if (map.isTile(enemyPos.add(xs * timeSec, 0f), Air.class)) {
                  return enemyPos.add(xs * timeSec, 0f);
               }
               else {
                  target.setVelocity(new Vector2f(1f, 0f));
                  return enemyPos.add(1f * timeSec, 0f);
               }
            }
            else {
               if (map.isTile(enemyPos.add(xs, 0f), Air.class)) {
                  return enemyPos.add(xs * timeSec, 0f);
               }
               else {
                  target.setVelocity(new Vector2f(-1f, 0f));
                  return enemyPos.add(-1f * timeSec, 0f);
               }
            }
         }
      } 
   }
}
