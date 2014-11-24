package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.*;

public class Kondoria extends Enemy {
    private static final SpriteInfo KONDORIA_ENEMY_SPRITE = new SpriteInfo("entity", 32, Vector2f.ONE);
    private static final AI KONDORIA_ENEMY_AI = new SmartAI(Enemy.target);
    
    public Kondoria(Vector2f position) {
        super(position);
    }
    
    public AI getAI(){
        return KONDORIA_ENEMY_AI;
    }
    
    public  SpriteInfo getSpriteInfo(){
        return KONDORIA_ENEMY_SPRITE;
    }
    
    public float getSpeed(){
        return Enemy.ENEMY_BASE_SPEED-1;
        
    }
    
    public  boolean isWallPass(){
        return true;
        
    }
    
    public float getScore(){
        return 1000;
        
    }
    


}
