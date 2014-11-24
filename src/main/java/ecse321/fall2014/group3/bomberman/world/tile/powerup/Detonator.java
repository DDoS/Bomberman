package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Detonator extends PowerUP {
    private static final SpriteInfo DETONATOR_POWERUP_SPRITE = new SpriteInfo("Sprite", 52, Vector2f.ONE);

    public Detonator(Vector2f position) {
        super(position, false);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return DETONATOR_POWERUP_SPRITE;
    }
}
