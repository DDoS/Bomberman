package ecse321.fall2014.group3.bomberman.world;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.input.Key;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Bomb;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Breakable;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Unbreakable;
import net.royawesome.jlibnoise.NoiseQuality;
import net.royawesome.jlibnoise.module.source.Perlin;

/**
 *
 */
public class World extends TickingElement {
    private final Game game;
    private final Map map = new Map();
    private volatile Level level = Level.MAIN_MENU;
    private int activeBombs = 0;

    public World(Game game) {
        super("World", 20);
        this.game = game;
    }

    @Override
    public void onStart() {
        generateMap(0.5);
        // Signal a new map version to the physics and rendering
        map.incrementVersion();
    }

    @Override
    public void onTick(long dt) {
        boolean updatedMap = false;
        // Do bomb placement
        final Player player = game.getPhysics().getPlayer();
        final int bombPlaceInput = game.getInput().getKeyboardState().getAndClearPressCount(Key.PLACE);
        final int bombsToPlace = Math.min(player.getBombPlacementCount() - activeBombs, bombPlaceInput);
        for (int i = 0; i < bombsToPlace; i++) {
            final Vector2f inFront = player.getPosition().round().add(player.getDirection().getUnit());
            if (map.isTile(inFront, Air.class)) {
                map.setTile(inFront, Bomb.class);
                updatedMap = true;
                activeBombs++;
            }
        }
        // Explode expired bombs
        for (Bomb bomb : map.getTiles(Bomb.class)) {
            if (bomb.getRemainingTime() <= 0) {
                map.setTile(bomb.getPosition(), Air.class);
                updatedMap = true;
                activeBombs--;
            }
        }
        // Update new map version if needed
        if (updatedMap) {
            map.incrementVersion();
        }
    }

    @Override
    public void onStop() {

    }

    public Map getMap() {
        return map;
    }

    public Level getLevel() {
        return level;
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
                    map.setTile(x, y, Unbreakable.class);
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
    }
}
