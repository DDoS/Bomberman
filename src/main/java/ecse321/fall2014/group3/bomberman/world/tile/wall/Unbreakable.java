/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile.wall;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

/**
 * The Class Unbreakable tile.
 */
public class Unbreakable extends CollidableTile {
    private static final SpriteInfo WALL_SPRITE = new SpriteInfo("Sprite", 64, Vector2f.ONE);

    /**
     * Instantiates a new unbreakable tile.
     *
     * @param position the position
     */
    public Unbreakable(Vector2f position) {
        super(position);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return WALL_SPRITE;
    }
}
