package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.*;

public class Doll  extends Enemy{
    private static final SpriteInfo DOLL_ENEMY_SPRITE = new SpriteInfo("entity", 32, Vector2f.ONE);
    private static final AI DOLL_ENEMY_AI = new DumbAI(Enemy.target);
    
    public Doll(Vector2f position) {
        super(position);
    }
    
    
    public AI getAI(){
        return DOLL_ENEMY_AI;
    }
    
    public  SpriteInfo getSpriteInfo(){
        return DOLL_ENEMY_SPRITE;
    }
    
    
    public float getSpeed(){
        return Enemy.ENEMY_BASE_SPEED+1;
        
    }
    
    
    public  boolean isWallPass(){
        return false;
        
    }
    
    public  float getScore(){
        return 400;
        
    }


}
