/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class BombPass PowerUP.
 */
public class BombPass extends PowerUP {
    private static final SpriteInfo BOMBPASS_POWERUP_SPRITE = new SpriteInfo("Sprite", 53, Vector2f.ONE);

    /**
     * Instantiates a new bomb pass PowerUP.
     *
     * @param position the position
     */
    public BombPass(Vector2f position) {
        super(position, false);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMBPASS_POWERUP_SPRITE;
    }
}
