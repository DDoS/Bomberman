package ecse321.fall2014.group3.bomberman.world;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Breakable;
import ecse321.fall2014.group3.bomberman.world.tile.Wall;
import net.royawesome.jlibnoise.NoiseQuality;
import net.royawesome.jlibnoise.module.source.Perlin;

/**
 *
 */
public class World extends TickingElement {
    private final Game game;
    private final Map map = new Map();

    public World(Game game) {
        super("World", 20);
        this.game = game;
    }

    @Override
    public void onStart() {
        generateMap(0.5);
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

    private void generateMap(double density) {
        // Invert the block density to get the air density
        density = 1 - density;
        // Seed a perlin noise generator to the current time
        final Perlin perlin = new Perlin();
        perlin.setSeed((int) System.currentTimeMillis());
        perlin.setNoiseQuality(NoiseQuality.BEST);
        // 0.3 provides features about 3 tiles in size
        perlin.setFrequency(0.3);
        // After 3 octaves, the frequency is 1.2, which is smaller than a tile
        perlin.setOctaveCount(3);
        // Generate the breakable and unbreakable walls
        for (int y = 0; y < Map.HEIGHT; y++) {
            for (int x = 0; x < Map.WIDTH; x++) {
                if (y == 0 || y == Map.HEIGHT - 1 || x == 0 || x == Map.WIDTH - 1
                        || y % 2 == 0 && x % 2 == 0) {
                    map.setTile(x, y, Wall.class);
                } else {
                    // Normalize the value from (-1, 1) to (0, 1)
                    if ((perlin.getValue(x, y, 0) + 1) / 2 >= density) {
                        map.setTile(x, y, Breakable.class);
                    } else {
                        map.setTile(x, y, Air.class);
                    }
                }
            }
        }
        // Make the starting position air
        map.setTile(1, 1, Air.class);
        map.setTile(2, 1, Air.class);
        map.setTile(1, 2, Air.class);
        // Signal a new map version to the physics and rendering
        map.incrementVersion();
    }
}
