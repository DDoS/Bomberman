/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 * The Class MenuBackground.
 */
public class MenuBackground extends Tile {
    private static final SpriteInfo MENU_BACKGROUND_SPRITE = new SpriteInfo("terrain", 224, Vector2f.ONE);

    /**
     * Instantiates a new menu background.
     *
     * @param position the position
     */
    public MenuBackground(Vector2f position) {
        super(position, null);
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.SpriteTextured#getSpriteInfo()
     */
    @Override
    public SpriteInfo getSpriteInfo() {
        return MENU_BACKGROUND_SPRITE;
    }
}
