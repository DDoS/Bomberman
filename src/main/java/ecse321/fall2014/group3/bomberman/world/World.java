/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.world;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector2i;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.SubscribableQueue;
import ecse321.fall2014.group3.bomberman.database.Session;
import ecse321.fall2014.group3.bomberman.event.EnemyDeathEvent;
import ecse321.fall2014.group3.bomberman.event.Event;
import ecse321.fall2014.group3.bomberman.event.ExitWayOrPowerUPDestroyedEvent;
import ecse321.fall2014.group3.bomberman.event.PlayerLostLifeEvent;
import ecse321.fall2014.group3.bomberman.event.PowerUPCollectedEvent;
import ecse321.fall2014.group3.bomberman.input.Key;
import ecse321.fall2014.group3.bomberman.input.KeyboardState;
import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.Button;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.Slider;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.ExitWay;
import ecse321.fall2014.group3.bomberman.world.tile.MenuBackground;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.Detonator;
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
    private final SubscribableQueue<Event> events = new SubscribableQueue<>(false);
    private final Map map = new Map();
    private volatile Level level = Level.MAIN_MENU;
    private int activeBombs;
    private Vector2f exitwayTile;
    private Vector2f powerUPTile;
    private volatile int score;
    private volatile int timer;
    private long lastTime = 0;
    private int lives;
    private final Queue<Vector2i> bombLocations = new LinkedList<>();

    /**
     * Instantiates a new world.
     *
     * @param game the game
     */
    public World(Game game) {
        super("World", 20);
        this.game = game;
        score = 0;
        timer = 500;
        lives = 3;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.ticking.TickingElement#onStart()
     */
    //start the world
    @Override
    public void onStart() {
        events.becomePublisher();
        game.getPhysics().subscribeToEvents();
        level = Level.MAIN_MENU;
        generateMenuBackground();
        // Signal a new map version to the physics and rendering
        map.incrementVersion();
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.ticking.TickingElement#onTick(long)
     */
    //updates the world on every tick
    @Override
    public void onTick(long dt) {
        if (level.isMenu()) {
            doMenuTick(dt);
        } else {
            doGameTick(dt);
        }
    }

    //updates the menu every tick
    private void doMenuTick(long dt) {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        final int enterCount = keyboardState.getAndClearPressCount(Key.PLACE);
        // Don't remove even if unused, this is to reset the state after each tick
        final int exitCount = keyboardState.getAndClearPressCount(Key.EXIT);
        final int detonateCount = keyboardState.getAndClearPressCount(Key.DETONATE);

        if (enterCount <= 0) {
            return;
        }
        final Button selectedButton = game.getPhysics().getSelectedButton();
        final String[] action = selectedButton.getAction();
        switch (action[0]) {
            case "levelload": {
                final Level nextLevel;
                switch (action[1]) {
                    case "restore":
                        nextLevel = Level.fromNumber(game.getSession().getLevel());
                        break;
                    case "number":
                        nextLevel = Level.fromNumber(((Slider) selectedButton).getValue());
                        break;
                    default:
                        throw new IllegalStateException("Unknown button action: " + action[1]);
                }
                generateLevel(nextLevel);
                map.incrementVersion();
                activeBombs = 0;
                lives = game.getSession().getLives();
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

    //updates the game every tick
    private void doGameTick(long dt) {
        processGameEvents();

        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        final int placeCount = keyboardState.getAndClearPressCount(Key.PLACE);
        final int exitCount = keyboardState.getAndClearPressCount(Key.EXIT);
        final int detonateCount = keyboardState.getAndClearPressCount(Key.DETONATE);

        final Player player = game.getPhysics().getPlayer();
        if (System.currentTimeMillis() - lastTime > 1000) {
            lastTime = System.currentTimeMillis();
            timer--;
        }

        final Session session = game.getSession();
        if (player.isCollidingWith(Fire.class) && !player.hasPowerUP(FlamePass.class) && !level.isBonus() || player.isCollidingWith(Enemy.class) || timer <= 0) {
            lives--;
            events.add(new PlayerLostLifeEvent());
            session.setLives(lives);
        }
        // Game over
        if (lives <= 0) {
            lives = 3;
            if (level.getNumber() != 1) {
                score += session.getScore();
            }
            session.setScore(score);
            session.setLives(lives);
            session.setPowerUPs(Collections.EMPTY_MAP);
            score = 0;
            timer = 500;
            level = Level.GAME_OVER;
            generateMenuBackground();
            map.incrementVersion();
            return;
        }
        // Game win
        if (player.isCollidingWith(ExitWay.class) && enemiesAllDead()) {
            if (level.isBonus()) {
                score += 150 * Math.abs(level.getNumber()) + timer;
            } else {
                score += 50 * level.getNumber() + timer;
            }
            if (level.getNumber() != 1) {
                score += session.getScore();
            }
            session.setScore(score);
            session.setPowerUPs(player.getPowerUPs());
            score = 0;
            timer = 500;
            if (level.getNumber() == 50) {
                level = Level.MAIN_MENU;
                generateMenuBackground();
                map.incrementVersion();
                return;
            }
            final Level nextLevel = level.next();
            if (session.getLevel() < nextLevel.getNumber()) {
                session.setLevel(nextLevel.getNumber());
            }
            generateLevel(nextLevel);
            map.incrementVersion();
            level = nextLevel;
            return;
        }
        // Game exit
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
                bombLocations.add(position.toInt());
                activeBombs++;
                updatedMap = true;
            }
        }
        // Explode bombs and remove dead flames
        final int blastRadius = player.getBlastRadius();
        final boolean detonator = player.hasPowerUP(Detonator.class);
        if (detonator) {
            for (int i = 0; i < detonateCount && activeBombs > 0; i++, activeBombs--) {
                generateFlames(bombLocations.poll().toFloat(), blastRadius);
                updatedMap = true;
            }
        }
        for (TimedTile timed : map.getTiles(TimedTile.class)) {
            final boolean isBomb = timed instanceof Bomb;
            if (timed.hasExpired() && (!detonator || !isBomb)) {
                final Vector2f position = timed.getPosition();
                if (isBomb) {
                    generateFlames(position, blastRadius);
                    bombLocations.remove(position.toInt());
                    activeBombs--;
                } else {
                    map.setTile(position, Air.class);
                }
                updatedMap = true;
            }
        }
        // Remove collected powerups
        for (Collidable tile : player.getCollisionList()) {
            if (tile instanceof PowerUP) {
                map.setTile(tile.getPosition(), Air.class);
                events.add(new PowerUPCollectedEvent((PowerUP) tile));
                updatedMap = true;
            }
        }
        // Update new map version if needed
        if (updatedMap) {
            map.incrementVersion();
        }
    }

    //processes the game events
    private void processGameEvents() {
        final Queue<Event> physicsEvents = game.getPhysics().getEvents();
        while (!physicsEvents.isEmpty()) {
            final Event event = physicsEvents.poll();
            if (event instanceof EnemyDeathEvent) {
                score += ((EnemyDeathEvent) event).getEnemy().getScore();
            }
        }
    }

    //generates the flames
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
                } else if (tile instanceof ExitWay || tile instanceof PowerUP) {
                    events.add(new ExitWayOrPowerUPDestroyedEvent(tile));
                } else if (i > 0 && tile instanceof Bomb) {
                    generateFlames(flamePosition, blastRadius);
                    bombLocations.remove(flamePosition.toInt());
                    activeBombs--;
                }
            }
        }
    }

    //checks if all the enemies are dead
    private boolean enemiesAllDead() {
        for (Entity entity : game.getPhysics().getEntities()) {
            if (entity instanceof Enemy) {
                return false;
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.ticking.TickingElement#onStop()
     */
    //stops the world
    @Override
    public void onStop() {
        events.unsubscribeAll();
    }

    /**
     * Subscribe to world events.
     */
    public void subscribeToEvents() {
        events.subscribe();
    }

    /**
     * Gets the events.
     *
     * @return the events
     */
    public Queue<Event> getEvents() {
        return events;
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the timer.
     *
     * @return the timer
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Gets the lives.
     *
     * @return the lives
     */
    public int getLives() {
        return lives;
    }

    //generates the menu background
    private void generateMenuBackground() {
        for (int y = 0; y < Interface.VIEW_HEIGHT_TILE; y++) {
            for (int x = 0; x < Interface.VIEW_WIDTH_TILE; x++) {
                map.setTile(x, y, MenuBackground.class);
            }
        }
        map.incrementVersion();
    }

    //generates the level
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
