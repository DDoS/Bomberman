package ecse321.fall2014.group3.bomberman.world.tile;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Wall extends Tile {
    private static final SpriteInfo WALL_SPRITE = new SpriteInfo("terrain", 192, Vector2f.ONE);

    public Wall(Vector2f position) {
        super(position, Tile.COLLISION_BOX);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return WALL_SPRITE;
    }
}
