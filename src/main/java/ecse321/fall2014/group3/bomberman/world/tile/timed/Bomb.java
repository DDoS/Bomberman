/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class Bomb tile.
 */
public class Bomb extends TimedTile {
    private static final SpriteInfo BOMB_SPRITE = new SpriteInfo("Sprite", 68, Vector2f.ONE);
    private static final long LIFE_TIME = 3000;
    private static final float ANIMATION_GROWTH_PERCENT = 0.25f;

    /**
     * Instantiates a new bomb tile.
     *
     * @param position the position
     */
    public Bomb(Vector2f position) {
        super(position, LIFE_TIME);
    }

    @Override
    public Vector2f getModelSize() {
        final float timePercent = 1 - getRemainingTime() / (float) LIFE_TIME;
        return super.getModelSize().mul(timePercent * ANIMATION_GROWTH_PERCENT + (1 - ANIMATION_GROWTH_PERCENT));
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMB_SPRITE;
    }
}
