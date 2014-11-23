package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Fire extends TimedTile {
    private static final SpriteInfo FIRE_SPRITE = new SpriteInfo("terrain", 1, Vector2f.ONE);
    private static final long LIFE_TIME = 1000;

    public Fire(Vector2f position) {
        super(position, LIFE_TIME);
    }

    @Override
    public boolean isGhost() {
        return true;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FIRE_SPRITE;
    }
}
