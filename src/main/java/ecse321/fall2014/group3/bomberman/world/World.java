package ecse321.fall2014.group3.bomberman.world;

import java.util.List;
import java.util.Random;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.database.Session;
import ecse321.fall2014.group3.bomberman.input.Key;
import ecse321.fall2014.group3.bomberman.input.KeyboardState;
import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.ButtonEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.SliderEntity;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.ExitWay;
import ecse321.fall2014.group3.bomberman.world.tile.MenuBackground;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Bomb;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Fire;
import ecse321.fall2014.group3.bomberman.world.tile.timed.TimedTile;
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
    private int activeBombs;
    private Vector2f exitwayTile;

    public World(Game game) {
        super("World", 20);
        this.game = game;
    }

    @Override
    public void onStart() {
        level = Level.MAIN_MENU;
        generateMenuBackground();
        // Signal a new map version to the physics and rendering
        map.incrementVersion();
    }

    @Override
    public void onTick(long dt) {
        if (level.isMenu()) {
            menuTick(dt);
        } else {
            gameTick(dt);
        }
        game.getInput().getKeyboardState().clearAll();
    }

    private void menuTick(long dt) {
        if (game.getInput().getKeyboardState().getAndClearPressCount(Key.PLACE) <= 0) {
            return;
        }
        final ButtonEntity selectedButton = game.getPhysics().getSelectedButton();
        final String[] action = selectedButton.getAction();
        switch (action[0]) {
            case "levelload":
                switch (action[1]) {
                    case "restore":
                        level = Level.fromNumber(game.getSession().getLevel());
                        break;
                    case "number":
                        level = Level.fromNumber(((SliderEntity) selectedButton).getValue());
                        break;
                    default:
                        throw new IllegalStateException("Unknown button action: " + action[1]);
                }
                generateLevel(level);
                map.incrementVersion();
                activeBombs = 0;
                break;
            case "menuload":
                switch (action[1]) {
                    case "main":
                        level = Level.MAIN_MENU;
                        break;
                    case "levelselect":
                        level = Level.LEVEL_SELECT;
                        break;
                    case "loaderboard":
                        level = Level.LEADER_BOARD;
                        break;
                    default:
                        throw new IllegalStateException("Unknown button action: " + action[1]);
                }
                generateMenuBackground();
                map.incrementVersion();
                break;
            default:
                throw new IllegalStateException("Unknown button action target: " + action[0]);
        }
    }

    private void gameTick(long dt) {
        final Player player = game.getPhysics().getPlayer();
        if (player.isCollidingWith(Fire.class) || player.isCollidingWith(Enemy.class)) {
            level = Level.GAME_OVER;
            game.getSession().setLevel(1);
            generateMenuBackground();
            map.incrementVersion();
            return;
        }
        if (player.isCollidingWith(ExitWay.class)) {
            if (level.getNumber() == 50) {
                level = Level.MAIN_MENU;
                generateMenuBackground();
                map.incrementVersion();
                return;
            }
            level = level.next();
            final Session session = game.getSession();
            if (session.getLevel() < level.getNumber()) {
                session.setLevel(level.getNumber());
            }
            generateLevel(level);
            map.incrementVersion();
            return;
        }
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        if (keyboardState.getAndClearPressCount(Key.EXIT) > 0) {
            level = Level.MAIN_MENU;
            generateMenuBackground();
            map.incrementVersion();
            return;
        }

        boolean updatedMap = false;
        // Do bomb placement
        final int bombPlaceInput = keyboardState.getAndClearPressCount(Key.PLACE);
        final int bombsToPlace = Math.min(player.getBombPlacementCount() - activeBombs, bombPlaceInput);
        for (int i = 0; i < bombsToPlace; i++) {
            final Vector2f inFront = player.getPosition().round().add(player.getDirection().getUnit());
            if (map.isTile(inFront, Air.class)) {
                map.setTile(inFront, Bomb.class);
                updatedMap = true;
                activeBombs++;
            }
        }
        // Explode expired bombs and remove dead flames
        final int blastRadius = player.getBlastRadius();
        for (TimedTile timed : map.getTiles(TimedTile.class)) {
            if (timed.hasExpired()) {
                if (timed instanceof Bomb) {
                    generateFlames(timed.getPosition(), blastRadius);
                    activeBombs--;
                } else {
                    map.setTile(timed.getPosition(), Air.class);
                }
                updatedMap = true;
            }
        }
        // Update new map version if needed
        if (updatedMap) {
            map.incrementVersion();
        }
    }

    private void generateFlames(Vector2f position, int blastRadius) {
        for (Direction direction : Direction.values()) {
            for (int i = 0; i <= blastRadius; i++) {
                final Vector2f flamePosition = position.add(direction.getUnit().mul(i));
                final Tile tile = map.getTile(flamePosition);
                if (tile instanceof Unbreakable) {
                    break;
                }
                if (flamePosition.equals(exitwayTile)) {
                    map.setTile(flamePosition, ExitWay.class);
                } else {
                    map.setTile(flamePosition, Fire.class);
                }
                if (i > 0 && tile instanceof Bomb) {
                    generateFlames(flamePosition, blastRadius);
                    activeBombs--;
                }
            }
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

    private void generateMenuBackground() {
        for (int y = 0; y < Interface.VIEW_HEIGHT_TILE; y++) {
            for (int x = 0; x < Interface.VIEW_WIDTH_TILE; x++) {
                map.setTile(x, y, MenuBackground.class);
            }
        }
        map.incrementVersion();
    }

    private void generateLevel(Level level) {
        // Compute the density from the level difficulty
        double density = level.isBonus() ? 0.5 : level.getNumber() / 100d + 0.25;
        // Invert the block density to get the air density
        density = 1 - density;
        // Seed a perlin noise generator to the level index
        final Perlin perlin = new Perlin();
        perlin.setSeed(level.getNumber());
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
        // Select a random tile for the exitway
        final List<Breakable> possibleTiles = map.getTiles(Breakable.class);
        exitwayTile = possibleTiles.get(new Random().nextInt(possibleTiles.size())).getPosition();
    }
}
