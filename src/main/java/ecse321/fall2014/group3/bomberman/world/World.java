package ecse321.fall2014.group3.bomberman.world;

import java.util.Random;

import com.flowpowered.commons.ticking.TickingElement;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.world.tile.*;


/**
 *
 */
public class World extends TickingElement {
    private final Game game;
    private final Map map = new Map();
    Random rand = new Random(); 
    int limit = 30;
 
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
                	if ((rand.nextInt(100) > limit)|| (y < 2) ||(x< 2)){
                		map.setTile(x, y, Air.class);
                	}else {
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
