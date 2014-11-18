package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class FlamePass extends PowerUP {
    private static final SpriteInfo FLAMEPASS_POWERUP_SPRITE = new SpriteInfo("terrain", 38, Vector2f.ONE);

    public FlamePass(Vector2f position) {
        super(position, false);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FLAMEPASS_POWERUP_SPRITE;
    }
}
