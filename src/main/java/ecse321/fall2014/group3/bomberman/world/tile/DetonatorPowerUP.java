package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class DetonatorPowerUP extends PowerUp {

    private static final SpriteInfo DETONATOR_POWERUP_SPRITE = new SpriteInfo("terrain", 36, Vector2f.ONE);

    public DetonatorPowerUP(Vector2f position) {
        super(position);

    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return DETONATOR_POWERUP_SPRITE;
    }

}
