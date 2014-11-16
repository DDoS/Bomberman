package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class WallpassPowerUP extends PowerUp {

    private static final SpriteInfo WALLPASS_POWERUP_SPRITE = new SpriteInfo("terrain", 35, Vector2f.ONE);

    public WallpassPowerUP(Vector2f position) {
        super(position);

    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return WALLPASS_POWERUP_SPRITE;
    }

}
