package ecse321.fall2014.group3.bomberman.world.tile.wall;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

public class Unbreakable extends CollidableTile {
    private static final SpriteInfo WALL_SPRITE = new SpriteInfo("terrain", 192, Vector2f.ONE);

    public Unbreakable(Vector2f position) {
        super(position);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return WALL_SPRITE;
    }
}
