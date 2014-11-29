package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.DumbAI;

public class Doll extends Enemy {
    private static final SpriteInfo DOLL_ENEMY_SPRITE = new SpriteInfo("Sprite", 34, Vector2f.ONE);
    private static final AI DOLL_ENEMY_AI = new DumbAI();

    public Doll(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return DOLL_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return DOLL_ENEMY_SPRITE;
    }

    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED + 1;
    }

    @Override
    public boolean isWallPass() {
        return false;
    }

    @Override
    public int getScore() {
        return 400;
    }
}
