package ecse321.fall2014.group3.bomberman.physics.ai;

import java.util.Random;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

/**
 *
 */
public class DumbAI extends AI {
   public DumbAI(Mob target) {
      super(target);
   }

   @Override
   public Vector2f nextPosition(long dt, Map map, Player player) {
      float timeSec = dt / TO_SECS;
      Set<Tile> colTiles = target.getTileCollisionList();

      if ((target.getVelocity()).length() == 0) {
         return target.getPosition();
      } else if (colTiles.isEmpty()) {
         Random random = new Random();
         switch (random.nextInt(4)) {
            case 0:
               return (target.getPosition()).sub((target.getVelocity()).getX() * timeSec, 0f);
            case 1:
               return (target.getPosition()).sub(0f, (target.getVelocity()).getY() * timeSec);
            case 2:
               return (target.getPosition()).add(0f, (target.getVelocity()).getY() * timeSec);
            case 3:
               return (target.getPosition()).add((target.getVelocity()).getX() * timeSec, 0f);
         }
      } else {
         boolean frontCollide = false;

         for (Tile tile : colTiles) {
            if ((tile.getPosition()).dot(target.getPosition()) > 0) {
               frontCollide = true;
            }
         }

         if (frontCollide) {
            Random random = new Random();
            switch (random.nextInt(3)) {
               case 0:
                  return (target.getPosition()).add(0f, (target.getVelocity()).getY() * timeSec);
               case 1:
                  return (target.getPosition()).sub(0f, (target.getVelocity()).getY() * timeSec);
               case 2:
                  return (target.getPosition()).sub((target.getVelocity()).getX() * timeSec, 0f);
            }
         }
      }
      return target.getPosition();
   }
}
