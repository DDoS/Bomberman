package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.RegularAI;

public class Ovapi extends Enemy {
    private static final SpriteInfo OVAPI_ENEMY_SPRITE = new SpriteInfo("entity", 32, Vector2f.ONE);
    private static final AI OVAPI_ENEMY_AI = new RegularAI();

    public Ovapi(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return OVAPI_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return OVAPI_ENEMY_SPRITE;
    }

    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED;
    }

    @Override
    public boolean isWallPass() {
        return true;
    }

    @Override
    public float getScore() {
        return 2000;
    }
}