package ecse321.fall2014.group3.bomberman.world.tile.wall;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public class Breakable extends CollidableTile {
    private static final SpriteInfo BREAKABLE_SPRITE = new SpriteInfo("terrain", 128, Vector2f.ONE);

    public Breakable(Vector2f position) {
        super(position);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BREAKABLE_SPRITE;
    }
}
