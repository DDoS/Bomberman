package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

/**
 *
 */
public class ExitWay extends CollidableTile {
    private static final SpriteInfo EXITWAY_SPRITE = new SpriteInfo("Sprite", 67, Vector2f.ONE);

    public ExitWay(Vector2f position) {
        super(position);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return EXITWAY_SPRITE;
    }
}
