package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 *
 */
public class MenuBackground extends Tile {
    private static final SpriteInfo MENU_BACKGROUND_SPRITE = new SpriteInfo("terrain", 224, Vector2f.ONE);

    public MenuBackground(Vector2f position) {
        super(position, null);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return MENU_BACKGROUND_SPRITE;
    }
}
