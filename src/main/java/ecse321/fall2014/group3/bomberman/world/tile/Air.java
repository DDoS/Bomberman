package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Air extends Tile {
    private static final SpriteInfo AIR_SPRITE = new SpriteInfo("terrain", 64, Vector2f.ONE);

    public Air(Vector2f position) {
        super(position, null);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return AIR_SPRITE;
    }
}
