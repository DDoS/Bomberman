package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Bomb extends LimitedLifetimeTile {
    private static final SpriteInfo BOMB_SPRITE = new SpriteInfo("terrain", 32, Vector2f.ONE);

    public Bomb(Vector2f position) {
        super(position);
    }

    @Override
    public int getRemainingTime() {
        return 0;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMB_SPRITE;
    }
}
