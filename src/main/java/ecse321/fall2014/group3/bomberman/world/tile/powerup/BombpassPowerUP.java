package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class BombpassPowerUP extends PowerUP {
    private static final SpriteInfo BOMBPASS_POWERUP_SPRITE = new SpriteInfo("terrain", 37, Vector2f.ONE);

    public BombpassPowerUP(Vector2f position) {
        super(position, false);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMBPASS_POWERUP_SPRITE;
    }
}
