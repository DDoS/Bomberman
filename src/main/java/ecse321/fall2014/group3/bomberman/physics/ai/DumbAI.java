package ecse321.fall2014.group3.bomberman.physics.ai;

import com.flowpowered.math.vector.Vector2f;
import java.util.Set;
import java.util.Random;

import ecse321.fall2014.group3.bomberman.physics.entity.mob.Mob;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
/**
 *
 */
public class DumbAI extends AI {
   public DumbAI(Mob target) {
      super(target);
   }

<<<<<<< HEAD
   @Override
   public Vector2f nextPosition(float dt, Map map) {
      float timeSec = dt/TOSECS;
      Set<Tile> colTiles = target.getTileCollisionList();
      
      if((target.getVelocity()).length() == 0)
         return target.getPosition();
      else if(colTiles.isEmpty()) {
         Random random = new Random();
         switch(random.nextInt(4)) {
            case 0:
               return (target.getPosition()).sub((target.getVelocity()).getX()*timeSec, 0f);
               break;
            case 1:
               return (target.getPosition()).sub(0f, (target.getVelocity()).getY()*timeSec);
               break;
            case 2:
               return (target.getPosition()).add(0f, (target.getVelocity()).getY()*timeSec);
               break; 
            case 3:
               return (target.getPosition()).add((target.getVelocity()).getX()*timeSec, 0f);
               break;
         } 
      }
      else {
         boolean frontCollide = false;
         
         for(Tile tile : colTiles) {
            if((tile.getPosition()).dot(target.getPosition()) > 0)
               frontCollide = true;
         }
         
         if(frontCollide == true) {   
            Random random = new Random();
            switch(random.nextInt(3)) {
               case 0:
                  return (target.getPosition()).add(0f, (target.getVelocity()).getY()*timeSec);
                  break;
               case 1:
                  return (target.getPosition()).sub(0f, (target.getVelocity()).getY()*timeSec);
                  break;
               case 2:
                  return (target.getPosition()).sub((target.getVelocity()).getX()*timeSec, 0f); 
                  break; 
            }    
         }
      }
   }
=======
    @Override
    public Vector2f nextPosition(long dt, Map map) {
        // TODO implement me, for now just return the same position
        return target.getPosition();
    }
>>>>>>> 65222e1ed0242cecb6346ea84a6e5090e6010175
}
