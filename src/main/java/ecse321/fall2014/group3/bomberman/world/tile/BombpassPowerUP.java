package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class BombpassPowerUP extends PowerUp {

    private static final SpriteInfo BOMBPASS_POWERUP_SPRITE = new SpriteInfo("terrain", 37, Vector2f.ONE);

    public BombpassPowerUP(Vector2f position) {
        super(position);

    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMBPASS_POWERUP_SPRITE;
    }

}
