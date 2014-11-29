package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.DumbAI;

public class Kondoria extends Enemy {
    private static final SpriteInfo KONDORIA_ENEMY_SPRITE = new SpriteInfo("Sprite", 36, Vector2f.ONE);
    private static final AI KONDORIA_ENEMY_AI = new DumbAI();

    public Kondoria(Vector2f position) {
        super(position);
    }

    @Override
    public AI getAI() {
        return KONDORIA_ENEMY_AI;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return KONDORIA_ENEMY_SPRITE;
    }

    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED - 1;
    }

    @Override
    public boolean isWallPass() {
        return true;
    }

    @Override
    public int getScore() {
        return 1000;
    }
}
