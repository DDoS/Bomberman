package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.DumbAI;

public class Pass extends Enemy {
    private static final SpriteInfo PASS_ENEMY_SPRITE = new SpriteInfo("Sprite", 38, Vector2f.ONE);
    private static final AI PASS_ENEMY_AI = new DumbAI();

    public Pass(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return PASS_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return PASS_ENEMY_SPRITE;
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
    public int getScore() {
        return 4000;
    }
}
