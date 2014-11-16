package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Breakable extends Tile {
    private static final SpriteInfo BREAKABLE_SPRITE = new SpriteInfo("terrain", 128, Vector2f.ONE);

    public Breakable(Vector2f position) {
        super(position, Tile.COLLISION_BOX);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BREAKABLE_SPRITE;
    }
}
