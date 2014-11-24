package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.SmartAI;

public class Pontan extends Enemy {
    private static final SpriteInfo PONTAN_ENEMY_SPRITE = new SpriteInfo("entity", 32, Vector2f.ONE);
    private static final AI PONTAN_ENEMY_AI = new SmartAI();

    public Pontan(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return PONTAN_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return PONTAN_ENEMY_SPRITE;
    }

    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED + 2;
    }

    @Override
    public boolean isWallPass() {
        return true;
    }

    @Override
    public float getScore() {
        return 8000;
    }
}