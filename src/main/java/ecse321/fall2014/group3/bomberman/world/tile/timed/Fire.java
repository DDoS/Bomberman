/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class Fire tile.
 */
public class Fire extends TimedTile {
    private static final SpriteInfo FIRE_SPRITE = new SpriteInfo("Sprite", 69, Vector2f.ONE);
    private static final long LIFE_TIME = 1000;

    /**
     * Instantiates a new fire tile.
     *
     * @param position the position
     */
    public Fire(Vector2f position) {
        super(position, 0.8f, LIFE_TIME);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.Collidable#isGhost()
     */
    @Override
    public boolean isGhost() {
        return true;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return FIRE_SPRITE;
    }
}
