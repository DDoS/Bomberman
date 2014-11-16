package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class FlamepassPowerUP extends PowerUP {

    private static final SpriteInfo FLAMEPASS_POWERUP_SPRITE = new SpriteInfo("terrain", 38, Vector2f.ONE);

    public FlamepassPowerUP(Vector2f position) {
        super(position);

    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FLAMEPASS_POWERUP_SPRITE;
    }
}
