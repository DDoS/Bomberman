package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class FlameUpgrade extends PowerUP {
    private static final SpriteInfo FLAME_POWERUP_SPRITE = new SpriteInfo("Sprite", 49, Vector2f.ONE);

    public FlameUpgrade(Vector2f position) {
        super(position, true);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FLAME_POWERUP_SPRITE;
    }
}
