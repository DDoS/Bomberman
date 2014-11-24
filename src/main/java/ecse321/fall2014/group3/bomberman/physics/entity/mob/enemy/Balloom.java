package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.*;

public class Balloom extends Enemy {
    private static final SpriteInfo BALLOOM_ENEMY_SPRITE = new SpriteInfo("entity", 32, Vector2f.ONE);
    private static final AI BALLOOM_ENEMY_AI = new DumbAI(Enemy.target);
    
    public Balloom(Vector2f position) {
        super(position);
    }
    

    public AI getAI(){
        return BALLOOM_ENEMY_AI;
    }

    public  SpriteInfo getSpriteInfo(){
        return BALLOOM_ENEMY_SPRITE;
    }
    

    public float getSpeed(){
        return Enemy.ENEMY_BASE_SPEED;
        
    }

    public  boolean isWallPass(){
        return false;
        
    }

    public  float getScore(){
        return  100;
        
    }
   

}
