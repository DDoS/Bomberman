package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class SpeedPowerUP extends PowerUP {
    private static final SpriteInfo SPEED_POWERUP_SPRITE = new SpriteInfo("terrain", 34, Vector2f.ONE);

    public SpeedPowerUP(Vector2f position) {
        super(position, true);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return SPEED_POWERUP_SPRITE;
    }
}
