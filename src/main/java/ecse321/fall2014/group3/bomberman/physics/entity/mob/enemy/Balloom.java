package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.DumbAI;

public class Balloom extends Enemy {
    private static final SpriteInfo BALLOOM_ENEMY_SPRITE = new SpriteInfo("Sprite", 32, Vector2f.ONE);
    private static final AI BALLOOM_ENEMY_AI = new DumbAI();

    public Balloom(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return BALLOOM_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BALLOOM_ENEMY_SPRITE;
    }

    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED;
    }

    @Override
    public boolean isWallPass() {
        return false;
    }

    @Override
    public float getScore() {
        return 100;
    }
}
