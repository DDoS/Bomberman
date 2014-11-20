package ecse321.fall2014.group3.bomberman.world.tile.timed;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Bomb extends TimedTile {
    private static final SpriteInfo BOMB_SPRITE = new SpriteInfo("terrain", 32, Vector2f.ONE);
    private static final long LIFE_TIME = 3000;
    private static final float ANIMATION_GROWTH_PERCENT = 0.25f;
    private final long placeTime;

    public Bomb(Vector2f position) {
        super(position);
        placeTime = System.currentTimeMillis();
    }

    @Override
    public long getRemainingTime() {
        return Math.max(LIFE_TIME - (System.currentTimeMillis() - placeTime), 0);
    }

    @Override
    public Vector2f getModelSize() {
        final float timePercent = 1 - getRemainingTime() / (float) LIFE_TIME;
        return super.getModelSize().mul(timePercent * ANIMATION_GROWTH_PERCENT + (1 - ANIMATION_GROWTH_PERCENT));
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMB_SPRITE;
    }
}
