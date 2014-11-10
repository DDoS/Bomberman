package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Fire extends LimitedLifetimeTile {
    private static final SpriteInfo FIRE_SPRITE = new SpriteInfo("terrain", 1, Vector2f.ONE);

    public Fire(Vector2f position) {
        super(position);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FIRE_SPRITE;
    }

    @Override
    public int getRemainingTime() {
        return 0;
    }
}
