package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.*;

public class Pass  extends Enemy{
    private static final SpriteInfo PASS_ENEMY_SPRITE = new SpriteInfo("entity", 32, Vector2f.ONE);
    private static final AI PASS_ENEMY_AI = new SmartAI(Enemy.target);
    
    public Pass(Vector2f position) {
        super(position);
    }
    
    public AI getAI(){
        return PASS_ENEMY_AI;
    }
    
    public  SpriteInfo getSpriteInfo(){
        return PASS_ENEMY_SPRITE;
    }
    
    public float getSpeed(){
        return Enemy.ENEMY_BASE_SPEED +2;
        
    }
    
    public  boolean isWallPass(){
        return false;
    }
    public  float getScore(){
        return 4000;
    }
    


}
