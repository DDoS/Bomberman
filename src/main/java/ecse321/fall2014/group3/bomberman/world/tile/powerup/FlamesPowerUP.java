package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

import com.flowpowered.math.vector.Vector2f;

public class FlamesPowerUP extends PowerUP {

    private static final SpriteInfo FLAME_POWERUP_SPRITE = new SpriteInfo("terrain", 33, Vector2f.ONE);

    public FlamesPowerUP(Vector2f position) {
        super(position);

    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return FLAME_POWERUP_SPRITE;
    }

}
