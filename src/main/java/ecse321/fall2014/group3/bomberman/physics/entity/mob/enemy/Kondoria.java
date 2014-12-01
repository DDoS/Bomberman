/**
 * @author Phil Douyon
*/
package ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.physics.ai.AI;
import ecse321.fall2014.group3.bomberman.physics.ai.DumbAI;


/**
 * The Class Kondoria.
 */
public class Kondoria extends Enemy {
    private static final SpriteInfo KONDORIA_ENEMY_SPRITE = new SpriteInfo("Sprite", 36, Vector2f.ONE);
    private static final AI KONDORIA_ENEMY_AI = new DumbAI();

    /**
     * Instantiates a new kondoria Enemy.
     *
     * @param position the position
     */
    public Kondoria(Vector2f position) {
        super(position);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#getAI()
     */
    @Override
    public AI getAI() {
        return KONDORIA_ENEMY_AI;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return KONDORIA_ENEMY_SPRITE;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#getSpeed()
     */
    @Override
    public float getSpeed() {
        return Enemy.ENEMY_BASE_SPEED - 1;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#isWallPass()
     */
    @Override
    public boolean isWallPass() {
        return true;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy#getScore()
     */
    @Override
    public int getScore() {
        return 1000;
    }
}
