package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector2i;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Wall extends Tile {
    private static final SpriteInfo WALL_SPRITE = new SpriteInfo("main", 192, Vector2i.ONE);

    public Wall(Vector2f position) {
        super(position, Tile.COLLISION_BOX);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return WALL_SPRITE;
    }
}
