/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class BombUpgrade PowerUP.
 */
public class BombUpgrade extends PowerUP {
    private static final SpriteInfo BOMB_POWERUP_SPRITE = new SpriteInfo("Sprite", 48, Vector2f.ONE);

    /**
     * Instantiates a new bomb PowerUP.
     *
     * @param position the position
     */
    public BombUpgrade(Vector2f position) {
        super(position, true);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMB_POWERUP_SPRITE;
    }
}
