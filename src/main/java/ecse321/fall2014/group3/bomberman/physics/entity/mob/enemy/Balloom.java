/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;
import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.DumbAI;

/**
 * The Class Balloom.
 */
public class Balloom extends Enemy {
    private static final SpriteInfo BALLOOM_ENEMY_SPRITE = new SpriteInfo("Sprite", 32, Vector2f.ONE);
    private static final AI BALLOOM_ENEMY_AI = new DumbAI();

    /**
     * Instantiates a new balloom Enemy.
     *
     * @param position the position
     */
    public Balloom(Vector2f position) {
        super(position);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#getAI()
     */
    @Override
    public AI getAI() {
        return BALLOOM_ENEMY_AI;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return BALLOOM_ENEMY_SPRITE;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#getSpeed()
     */
    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#isWallPass()
     */
    @Override
    public boolean isWallPass() {
        return false;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#getScore()
     */
    @Override
    public int getScore() {
        return 100;
    }
}
