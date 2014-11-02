package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector2i;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Air extends Tile {
    private static final SpriteInfo AIR_SPRITE = new SpriteInfo("main", 0, Vector2i.ONE);

    Air(Vector2f position) {
        super(position, null);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return AIR_SPRITE;
    }
}
