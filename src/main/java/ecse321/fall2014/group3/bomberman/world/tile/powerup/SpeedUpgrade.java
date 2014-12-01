/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class SpeedUpgrade PowerUP.
 */
public class SpeedUpgrade extends PowerUP {
    private static final SpriteInfo SPEED_POWERUP_SPRITE = new SpriteInfo("Sprite", 50, Vector2f.ONE);

    /**
     * Instantiates a new speed PowerUP.
     *
     * @param position the position
     */
    public SpeedUpgrade(Vector2f position) {
        super(position, true);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return SPEED_POWERUP_SPRITE;
    }
}
