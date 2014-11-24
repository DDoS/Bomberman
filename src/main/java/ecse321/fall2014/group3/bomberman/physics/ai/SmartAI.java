package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

import java.util.Set;
import java.lang.Math;

/**
 *
 */
public class SmartAI extends AI {
   public SmartAI(Mob target) {
      super(target);
   }

   @Override
    public Vector2f nextPosition(long dt, Map map, Player player) {
      Vector2f enemyPos = target.getPosition();
      Vector2f eVelocity = target.getVelocity();
      Vector2f playerPos = player.getPosition();
      
      float timeSec = dt / TO_SECS;
      float enemyX = enemyPos.getX();
      float enemyY = enemyPos.getY();
      float playX = playerPos.getX();
      float playY = playerPos.getY();
      float dx = Math.abs(enemyX - playX);
      float dy = Math.abs(enemyY - playY);
      
      Set<Tile> colTiles = target.getTileCollisionList();
   
      if (eVelocity.length() == 0) {
         return enemyPos;
      } 
      else if (colTiles.isEmpty()) {   
         if (playX < enemyX && dx != 0 && dx < dy) {
            return enemyPos.sub(eVelocity.getX() * timeSec, 0f);
         }
         else if (playX > enemyX && dx != 0 && dx < dy) {
            return enemyPos.add(eVelocity.getX() * timeSec, 0f);
         }
         else if (playY < enemyY && dy != 0 && dy < dx) {
            return enemyPos.sub(0f, eVelocity.getY() * timeSec);
         }
         else if (playY > enemyY && dy != 0 && dy < dx) {
            return enemyPos.add(0f, eVelocity.getY() * timeSec);
         } 
      } 
      else {
         boolean frontCollide = false;
      
         for (Tile tile : colTiles) {
            if ((tile.getPosition()).dot(enemyPos) > 0) {
               frontCollide = true;
            }
         }
      
         if (frontCollide) {
            if (playX < enemyX && dx != 0 && dx < dy) {
               return enemyPos.sub(eVelocity.getX() * timeSec, 0f);
            }
            else if ((playY < enemyY && dy != 0 && dy < dx) ||
                     (playY < enemyY && playX > enemyX)) {
               return enemyPos.sub(0f, eVelocity.getY() * timeSec);
            }
            else if ((playY > enemyY && dy != 0 && dy < dx) ||
                     (playY > enemyY && playX > enemyX)) {
               return enemyPos.add(0f, eVelocity.getY() * timeSec);
            }
         }
      }
      return target.getPosition();
   }
}
