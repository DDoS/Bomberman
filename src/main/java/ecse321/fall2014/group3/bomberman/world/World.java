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
import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.ButtonEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.SliderEntity;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.ExitWay;
import ecse321.fall2014.group3.bomberman.world.tile.MenuBackground;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlamePass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;
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
    private Vector2f powerUPTile;
    private volatile int score;
    private volatile int timer;
    private long last = 0;
    private int lives;

    public World(Game game) {
        super("World", 20);
        this.game = game;
        score = 0;
        last = System.currentTimeMillis();
        timer = 500;
        lives = 3;

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
            doMenuTick(dt);
        } else {
            doGameTick(dt);
        }
    }

    private void doMenuTick(long dt) {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        final int enterCount = keyboardState.getAndClearPressCount(Key.PLACE);
        // Don't remove even if unused, this is to reset the state after each tick
        final int exitCount = keyboardState.getAndClearPressCount(Key.EXIT);
        if (enterCount <= 0) {
            return;
        }

        final ButtonEntity selectedButton = game.getPhysics().getSelectedButton();
        final String[] action = selectedButton.getAction();
        switch (action[0]) {
            case "levelload": {
                final Level nextLevel;
                switch (action[1]) {
                    case "restore":
                        nextLevel = Level.fromNumber(game.getSession().getLevel());
                        break;
                    case "number":
                        nextLevel = Level.fromNumber(((SliderEntity) selectedButton).getValue());
                        break;
                    default:
                        throw new IllegalStateException("Unknown button action: " + action[1]);
                }
                generateLevel(nextLevel);
                map.incrementVersion();
                activeBombs = 0;
                level = nextLevel;
                break;
            }
            case "menuload": {
                final Level nextLevel;
                switch (action[1]) {
                    case "main":
                        nextLevel = Level.MAIN_MENU;
                        break;
                    case "levelselect":
                        nextLevel = Level.LEVEL_SELECT;
                        break;
                    case "loaderboard":
                        nextLevel = Level.LEADER_BOARD;
                        break;
                    default:
                        throw new IllegalStateException("Unknown button action: " + action[1]);
                }
                generateMenuBackground();
                map.incrementVersion();
                level = nextLevel;
                break;
            }
            default:
                throw new IllegalStateException("Unknown button action target: " + action[0]);
        }
    }

    private void doGameTick(long dt) {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        final int placeCount = keyboardState.getAndClearPressCount(Key.PLACE);
        final int exitCount = keyboardState.getAndClearPressCount(Key.EXIT);

        final Player player = game.getPhysics().getPlayer();
        if (System.currentTimeMillis() - last > 1000) {
            last = System.currentTimeMillis();
            timer--;
        }

        if (player.isCollidingWith(Fire.class) && !player.hasPowerUP(FlamePass.class)) {
            lives--;
        }
        if (player.isCollidingWith(Enemy.class)) {
            lives--;
        }
       if (timer <= 0) {
           lives--;
       }
       if (lives <= 0) {
           lives = 3;
           score -= 10;
           score += game.getSession().getScore() + game.getPhysics().getEnemyScore();
           game.getSession().setScore(score);
           score = 0;
           timer = 500;
           level = Level.GAME_OVER;
           generateMenuBackground();
           map.incrementVersion();
           return;
       }
       
        if (player.isCollidingWith(ExitWay.class) && enemiesAllDead()) {
            if (level.isBonus()) {
                score += (150 * Math.abs(level.getNumber())) + timer;
            } else {
                score += (50 * level.getNumber()) + timer;
            }
            if (level.getNumber() != 1) {
                score += game.getSession().getScore();
            }
            score += game.getPhysics().getEnemyScore();
            game.getSession().setScore(score);
            score = 0;
            timer = 500;
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
        if (exitCount > 0) {
            score = 0;
            timer = 500;
            level = Level.MAIN_MENU;
            generateMenuBackground();
            map.incrementVersion();
            return;
        }

        boolean updatedMap = false;
        // Do bomb placement
        final int bombsToPlace = Math.min(player.getBombPlacementCount() - activeBombs, placeCount);
        for (int i = 0; i < bombsToPlace; i++) {
            final Vector2f position = player.getPosition().add(0.5f, 0.5f);
            if (map.isTile(position, Air.class)) {
                map.setTile(position, Bomb.class);
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
        // Remove collected powerups
        for (Collidable tile : player.getCollisionList()) {
            if (tile instanceof PowerUP) {
                map.setTile(tile.getPosition(), Air.class);
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
                } else if (flamePosition.equals(powerUPTile)) {
                    map.setTile(flamePosition, level.getPowerUPForLevel());
                    powerUPTile = null;
                } else {
                    map.setTile(flamePosition, Fire.class);
                }
                if (tile instanceof Breakable) {
                    score += 5;
                }
                if (i > 0 && tile instanceof Bomb) {
                    generateFlames(flamePosition, blastRadius);
                    activeBombs--;
                }
            }
        }
    }

    private boolean enemiesAllDead() {
        for (Entity entity : game.getPhysics().getEntities()) {
            if (entity instanceof Enemy) {
                return false;
            }
        }
        return true;
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

    public int getScore() {
        return score;
    }

    public int getTimer() {
        return timer;
    }

    public int getLives() {
        return lives;
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
                if (y == 0 || y == Map.HEIGHT - 1 || x == 0 || x == Map.WIDTH - 1 || y % 2 == 0 && x % 2 == 0) {
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
        map.setTile(1, 11, Air.class);
        map.setTile(2, 11, Air.class);
        map.setTile(1, 10, Air.class);
        // Select a random tile for the exitway
        final List<Breakable> possibleTiles = map.getTiles(Breakable.class);
        exitwayTile = possibleTiles.get(new Random().nextInt(possibleTiles.size())).getPosition();
        powerUPTile = possibleTiles.get(new Random().nextInt(possibleTiles.size())).getPosition();
    }
}
