package ecse321.fall2014.group3.bomberman.world.tile;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;
import com.flowpowered.math.vector.Vector2f;

public class Fire extends LimitedLifetimeTile {

    private static final SpriteInfo fire_SPRITE = new SpriteInfo("main", 1, Vector2f.ONE);
    private int time = 0;
    private int size = 0;

    public Fire(Vector2f position, int t, int s) {
        super(position);
        this.time = t;
        this.size = s;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return fire_SPRITE;
    }

    public int getTime() {
        return time;
    }

    public int getSize() {
        return size;
    }

}
