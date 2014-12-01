/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class Air tile.
 */
public class Air extends Tile {
    private static final SpriteInfo AIR_SPRITE = new SpriteInfo("Sprite", 66, Vector2f.ONE);

    /**
     * Instantiates a new air.
     *
     * @param position the position
     */
    public Air(Vector2f position) {
        super(position, null);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return AIR_SPRITE;
    }
}
