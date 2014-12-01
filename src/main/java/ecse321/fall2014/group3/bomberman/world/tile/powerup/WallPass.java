/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class WallPass.
 */
public class WallPass extends PowerUP {
    private static final SpriteInfo WALLPASS_POWERUP_SPRITE = new SpriteInfo("Sprite", 51, Vector2f.ONE);

    /**
     * Instantiates a new wall pass.
     *
     * @param position the position
     */
    public WallPass(Vector2f position) {
        super(position, false);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return WALLPASS_POWERUP_SPRITE;
    }
}
