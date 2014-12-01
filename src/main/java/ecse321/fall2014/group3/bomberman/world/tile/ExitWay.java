/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class ExitWay tile.
 */
public class ExitWay extends CollidableTile {
    private static final SpriteInfo EXITWAY_SPRITE = new SpriteInfo("Sprite", 67, Vector2f.ONE);

    /**
     * Instantiates a new exit way.
     *
     * @param position the position
     */
    public ExitWay(Vector2f position) {
        super(position);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return EXITWAY_SPRITE;
    }
}
