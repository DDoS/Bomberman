package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Mystery extends PowerUP {
    private static final SpriteInfo MYSTERY_POWERUP_SPRITE = new SpriteInfo("terrain", 32, Vector2f.ONE);

    public Mystery(Vector2f position) {
        super(position, false);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return MYSTERY_POWERUP_SPRITE;
    }
}
