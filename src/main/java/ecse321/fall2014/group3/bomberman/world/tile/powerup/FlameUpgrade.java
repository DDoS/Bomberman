/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class FlameUpgrade PowerUp.
 */
public class FlameUpgrade extends PowerUP {
    private static final SpriteInfo FLAME_POWERUP_SPRITE = new SpriteInfo("Sprite", 49, Vector2f.ONE);

    /**
     * Instantiates a new flame PoweUP.
     *
     * @param position the position
     */
    public FlameUpgrade(Vector2f position) {
        super(position, true);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FLAME_POWERUP_SPRITE;
    }
}
