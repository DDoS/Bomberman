package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.RegularAI;

public class Minvo extends Enemy {
    private static final SpriteInfo MINVO_ENEMY_SPRITE = new SpriteInfo("entity", 38, Vector2f.ONE);
    private static final AI MINVO_ENEMY_AI = new RegularAI();

    public Minvo(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return MINVO_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return MINVO_ENEMY_SPRITE;
    }

    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED + 2;
    }

    @Override
    public boolean isWallPass() {
        return false;
    }

    @Override
    public float getScore() {
        return 800;
    }
}
