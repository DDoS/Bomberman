package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class WallPass extends PowerUP {
    private static final SpriteInfo WALLPASS_POWERUP_SPRITE = new SpriteInfo("terrain", 35, Vector2f.ONE);

    public WallPass(Vector2f position) {
        super(position, false);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return WALLPASS_POWERUP_SPRITE;
    }
}
