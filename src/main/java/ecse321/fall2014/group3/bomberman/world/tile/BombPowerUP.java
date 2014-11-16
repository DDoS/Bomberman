package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class BombPowerUP extends PowerUp {

    private static final SpriteInfo BOMB_POWERUP_SPRITE = new SpriteInfo("terrain", 32, Vector2f.ONE);

    public BombPowerUP(Vector2f position) {
        super(position);

    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BOMB_POWERUP_SPRITE;
    }

}
