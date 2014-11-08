package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class Bomb extends LimitedLifetimeTile {

    private static final SpriteInfo bomb_SPRITE = new SpriteInfo("main", 32, Vector2f.ONE);
    private int time = 0;

    public Bomb(Vector2f position, int t) {
        super(position);
        this.time = t;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return bomb_SPRITE;
    }

    public int getTime() {
        return time;
    }

}
