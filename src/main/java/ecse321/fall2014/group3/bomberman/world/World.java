package ecse321.fall2014.group3.bomberman.world;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Breakable;
import ecse321.fall2014.group3.bomberman.world.tile.Wall;

/**
 *
 */
public class World extends TickingElement {
    private static final float BREAKABLE_WALL_THRESHOLD = 0.3f;
    private final Game game;
    private final Map map = new Map();

    public World(Game game) {
        super("World", 20);
        this.game = game;
    }

    @Override
    public void onStart() {
        for (int y = 0; y < Map.HEIGHT; y++) {
            for (int x = 0; x < Map.WIDTH; x++) {
                if (y == 0 || y == Map.HEIGHT - 1 || x == 0 || x == Map.WIDTH - 1
                        || y % 2 == 0 && x % 2 == 0) {
                    map.setTile(x, y, Wall.class);
                } else {
                    // TODO: generate this better
                    if (y < 2 || x < 2 || Math.random() > BREAKABLE_WALL_THRESHOLD) {
                        map.setTile(x, y, Air.class);
                    } else {
                        map.setTile(x, y, Breakable.class);
                    }
                }
            }
        }
        map.incrementVersion();
    }

    @Override
    public void onTick(long dt) {

    }

    @Override
    public void onStop() {

    }

    public Map getMap() {
        return map;
    }
}
