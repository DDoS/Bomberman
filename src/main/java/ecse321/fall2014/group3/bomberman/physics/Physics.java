package ecse321.fall2014.group3.bomberman.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.SubscribableQueue;
import ecse321.fall2014.group3.bomberman.database.Leaderboard.Leader;
import ecse321.fall2014.group3.bomberman.event.EnemyDeathEvent;
import ecse321.fall2014.group3.bomberman.event.Event;
import ecse321.fall2014.group3.bomberman.event.ExitWayOrPowerUPDestroyedEvent;
import ecse321.fall2014.group3.bomberman.event.PlayerLostLifeEvent;
import ecse321.fall2014.group3.bomberman.event.PowerUPCollectedEvent;
import ecse321.fall2014.group3.bomberman.input.Key;
import ecse321.fall2014.group3.bomberman.input.KeyboardState;
import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Balloom;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Doll;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Enemy;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Kondoria;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Minvo;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Oneal;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Ovapi;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Pass;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.enemy.Pontan;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.Button;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.Slider;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBox;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.UIBox;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.Level;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.World;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombPass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlamePass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.WallPass;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Bomb;
import ecse321.fall2014.group3.bomberman.world.tile.timed.Fire;
import ecse321.fall2014.group3.bomberman.world.tile.wall.Breakable;

/**
 *
 */
public class Physics extends TickingElement {
    private static final float PERPENDICULAR_CONTACT_THRESHOLD = 0.05f;
    private static final float SLIDING_CONTACT_THRESHOLD = 0.9f;
    private static final float OVERLAP_CONTACT_THRESHOLD = 0.5f;
    private final Game game;
    private final SubscribableQueue<Event> events = new SubscribableQueue<>(false);
    private final SweepAndPruneAlgorithm collisionDetection = new SweepAndPruneAlgorithm();
    private final Set<Tile> collidableTiles = new HashSet<>();
    private final Set<Entity> entities = Collections.newSetFromMap(new ConcurrentHashMap<Entity, Boolean>());
    private final Player player = new Player(Vector2f.ZERO);
    private final List<Button> buttonOrder = Collections.synchronizedList(new ArrayList<Button>());
    private volatile int selectedButtonIndex;
    private Level currentLevel;
    private long mapVersion = 0;
    private TextBox levelStateText;

    public Physics(Game game) {
        super("Physics", 60);
        this.game = game;
    }

    @Override
    public void onStart() {
        events.becomePublisher();
        game.getWorld().subscribeToEvents();
    }

    @Override
    public void onTick(long dt) {
        final Level level = game.getWorld().getLevel();
        if (currentLevel != level) {
            currentLevel = level;
            clearEntities();
            if (currentLevel.isMenu()) {
                setupMenu();
            } else {
                setupGame();
            }
        }
        if (currentLevel.isMenu()) {
            doMenuTick(dt);
        } else {
            doGameTick(dt);
        }
    }

    private void clearEntities() {
        // Clear collision detection
        entities.clear();
        collisionDetection.clear();
        collidableTiles.clear();
        // Clear UI
        buttonOrder.clear();
    }

    private void setupMenu() {
        // Add UI entities
        final List<UIBox> uiEntities = game.getWorld().getLevel().buildUI(game.getSession().getLevel());
        entities.addAll(uiEntities);
        for (UIBox uiEntity : uiEntities) {
            if (uiEntity instanceof Button) {
                buttonOrder.add((Button) uiEntity);
            }
        }
        selectedButtonIndex = 0;
        // Add extra entities for leaderboard menu
        if (currentLevel == Level.LEADER_BOARD) {
            final Leader[] top = game.getLeaderboard().getTop(10);
            for (int i = 0; i < top.length && top[i] != null; i++) {
                entities.add(new TextBox(new Vector2f(4, Interface.VIEW_HEIGHT_TILE - (6 + i * 0.5f)), Vector2f.ONE, top[i].getFormatted()));
            }
        }
    }

    private void doMenuTick(long dt) {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        final int selectedShift = keyboardState.getAndClearPressCount(Key.DOWN) - keyboardState.getAndClearPressCount(Key.UP);
        final int sliderShift = keyboardState.getAndClearPressCount(Key.RIGHT) - keyboardState.getAndClearPressCount(Key.LEFT);
        final int buttonCount = buttonOrder.size();
        final int oldSelected = selectedButtonIndex;
        final int newSelected = ((oldSelected + selectedShift) % buttonCount + buttonCount) % buttonCount;
        if (buttonCount > 0) {
            buttonOrder.get(oldSelected).setSelected(false);
            buttonOrder.get(newSelected).setSelected(true);
        }
        selectedButtonIndex = newSelected;
        final Button selectedButton = getSelectedButton();
        if (selectedButton instanceof Slider) {
            ((Slider) selectedButton).add(sliderShift);
        }
    }

    private void setupGame() {
        // Add player
        entities.add(player);
        player.setPosition(new Vector2f(1, 11));
        player.clearPowerUPs();
        game.getSession().getPowerUPs(player.getPowerUPs());
        collisionDetection.add(player);
        // Add UI
        final World world = game.getWorld();
        final String levelString =
                currentLevel.isBonus() ? "Bonus level " + -currentLevel.getNumber() : "Level " + currentLevel.getNumber()
                        + " | Score " + world.getScore() + " |  Timer " + world.getTimer() + "| Lives " + world.getLives();
        levelStateText = new TextBox(new Vector2f(Map.WIDTH / 6f, Map.HEIGHT - 1.25f), new Vector2f(2, 2), levelString);
        entities.add(levelStateText);
        // Add enemies
        final List<Vector2f> freePositions = getFreePositions(world.getMap());
        Collections.shuffle(freePositions);
        // get the number of enemies on the level
        int[] enemies = currentLevel.getEnemyForLevel();
        int i = 0;
        // add balloom
        for (int j = 0; j < enemies[0] && i < freePositions.size(); j++, i++) {
            Balloom balloom = new Balloom(freePositions.get(i));
            entities.add(balloom);
            collisionDetection.add(balloom);
        }
        // add oneal
        for (int j = 0; j < enemies[1] && i < freePositions.size(); j++, i++) {
            Oneal oneal = new Oneal(freePositions.get(i));
            entities.add(oneal);
            collisionDetection.add(oneal);
        }
        // add doll
        for (int j = 0; j < enemies[2] && i < freePositions.size(); j++, i++) {
            Doll doll = new Doll(freePositions.get(i));
            entities.add(doll);
            collisionDetection.add(doll);
        }
        // add minvo
        for (int j = 0; j < enemies[3] && i < freePositions.size(); j++, i++) {
            Minvo minvo = new Minvo(freePositions.get(i));
            entities.add(minvo);
            collisionDetection.add(minvo);
        }
        // add kondoria
        for (int j = 0; j < enemies[4] && i < freePositions.size(); j++, i++) {
            Kondoria kondoria = new Kondoria(freePositions.get(i));
            entities.add(kondoria);
            collisionDetection.add(kondoria);
        }
        // add ovapi
        for (int j = 0; j < enemies[5] && i < freePositions.size(); j++, i++) {
            Ovapi ovapi = new Ovapi(freePositions.get(i));
            entities.add(ovapi);
            collisionDetection.add(ovapi);
        }
        // add pass
        for (int j = 0; j < enemies[6] && i < freePositions.size(); j++, i++) {
            Pass pass = new Pass(freePositions.get(i));
            entities.add(pass);
            collisionDetection.add(pass);
        }
        // add pontan
        for (int j = 0; j < enemies[7] && i < freePositions.size(); j++, i++) {
            Pontan pontan = new Pontan(freePositions.get(i));
            entities.add(pontan);
            collisionDetection.add(pontan);
        }
    }

    private void doGameTick(long dt) {
        processGameEvents();

        final World world = game.getWorld();
        final Map map = world.getMap();
        final long newVersion = map.getVersion();

        if (mapVersion < newVersion) {
            for (Tile tile : collidableTiles) {
                collisionDetection.remove(tile);
            }
            collidableTiles.clear();

            for (int y = 0; y < Map.HEIGHT; y++) {
                for (int x = 0; x < Map.WIDTH; x++) {
                    final Tile tile = map.getTile(x, y);
                    if (tile.isCollisionEnabled()) {
                        collidableTiles.add(tile);
                        collisionDetection.add(tile);
                    }
                }
            }
            mapVersion = newVersion;
        }

        collisionDetection.update();

        final float timeSeconds = dt / 1e9f;
        // Process player input
        final Vector2f inputVector = getInputVector().mul(player.getSpeed() * timeSeconds);
        // Compute the motion for the tick
        Vector2f movement = inputVector;
        for (Collidable collidable : player.getCollisionList()) {
            // ghost collidables only report collisions, but don't actually collide
            if (collidable.isGhost()) {
                continue;
            }
            // Powerup collision exceptions
            if (collidable instanceof Bomb && player.hasPowerUP(BombPass.class)) {
                continue;
            }
            if (collidable instanceof Breakable && player.hasPowerUP(WallPass.class)) {
                continue;
            }
            if (collidable instanceof Fire && player.hasPowerUP(FlamePass.class)) {
                continue;
            }
            // Find the intersection of the collision (a box) and the direction
            final Intersection intersection = getIntersection(player, collidable);
            final Direction direction = getCollisionDirection(intersection, collidable);
            // Allow for a small amount of contact on the sides to prevent the player from getting stuck in adjacent tiles
            if (intersection.size.dot(direction.getPerpendicularUnit()) < PERPENDICULAR_CONTACT_THRESHOLD) {
                continue;
            }
            // When the most of the player is intersecting, ignore the collision to prevent him from getting stuck
            if (intersection.area / player.getCollisionBox().getArea() >= OVERLAP_CONTACT_THRESHOLD) {
                continue;
            }
            // Block the movement in the direction if sufficient contact
            movement = blockDirection(movement, direction.getUnit());
            // Attempt to shift the player to the nearest free tile when close to one to ease motion in tight spaces
            if (collidable instanceof Tile) {
                // Check if the percentage of collision is lower than a threshold, signifying that the player is colliding by a minimum amount
                if (intersection.size.dot(direction.getPerpendicularUnit()) / player.getCollisionBox().getSize().dot(direction.getPerpendicularUnit()) < SLIDING_CONTACT_THRESHOLD) {
                    // Get the direction in which to attempt to shift as a unit
                    final Vector2f offset = intersection.center.sub(collidable.getPosition());
                    final Vector2f shiftDirection = direction.getPerpendicularUnit().mul(offset).normalize();
                    // Check if we can shift, by looking for a path around the tile in the shift direction
                    final Vector2f adjacentPosition = collidable.getPosition().add(shiftDirection);
                    if (map.isTile(adjacentPosition, Air.class) && map.isTile(adjacentPosition.sub(direction.getUnit()), Air.class)) {
                        // Redirect the blocked motion towards the free path
                        movement = movement.add(shiftDirection.mul(inputVector.dot(direction.getUnit())));
                    }
                }
            }
        }
        // Update player movement
        player.setPosition(player.getPosition().add(movement));
        player.setVelocity(movement.div(timeSeconds));
        // Update enemy positions and remove dead ones
        for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext(); ) {
            final Entity entity = iterator.next();
            if (entity instanceof Enemy) {
                if (entity.isCollidingWith(Fire.class)) {
                    iterator.remove();
                    // Adding the enemy score (unique to each enemy) to the total enemy score
                    events.add(new EnemyDeathEvent((Enemy) entity));
                    collisionDetection.remove(entity);
                }
                final Enemy enemy = (Enemy) entity;
                final Vector2f currentPosition = enemy.getPosition();
                final Vector2f nextPosition = enemy.getAI().nextPosition(enemy, dt, map, player);
                enemy.setPosition(nextPosition);
                enemy.setVelocity(nextPosition.sub(currentPosition).div(timeSeconds));
            }
        }

        // Update UI
        levelStateText.setText(currentLevel.isBonus() ? "Bonus level " + -currentLevel.getNumber() : "Level " + currentLevel.getNumber()
                + " | Score " + world.getScore() + " |  Timer " + world.getTimer() + "|  Lives " + world.getLives());
    }

    private void processGameEvents() {
        final Queue<Event> worldEvents = game.getWorld().getEvents();
        while (!worldEvents.isEmpty()) {
            final Event event = worldEvents.poll();
            if (event instanceof PlayerLostLifeEvent) {
                player.setPosition(new Vector2f(1, 11));
                player.onDeath();
            } else if (event instanceof PowerUPCollectedEvent) {
                player.addPowerUP(((PowerUPCollectedEvent) event).getPowerUP());
            } else if (event instanceof ExitWayOrPowerUPDestroyedEvent) {
                entities.clear();
                entities.add(player);
                collisionDetection.clear();
                collisionDetection.add(player);
                //get highest enemies
                int[] enemies = currentLevel.getEnemyForLevel();
                int highestEnemy = 0;
                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i] > 0) {
                        highestEnemy = i;
                    }
                }
                
                //need to get one higher than highest of level
                if (highestEnemy < 7){
                    highestEnemy++;
                }
                //get free positions 
                final World world = game.getWorld();
                final List<Vector2f> freePositions = getFreePositions(world.getMap());
                Collections.shuffle(freePositions);
                int i = 0;
                //add 8 of the highest enemy
                switch (highestEnemy) {
                    case 0:
                        //add ballom
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Balloom balloom = new Balloom(freePositions.get(i));
                            entities.add(balloom);
                            collisionDetection.add(balloom);
                        }
                        break;
                    case 1:
                        // add oneal
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Oneal oneal = new Oneal(freePositions.get(i));
                            entities.add(oneal);
                            collisionDetection.add(oneal);
                        }
                        break;
                    case 2:
                        // add doll
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Doll doll = new Doll(freePositions.get(i));
                            entities.add(doll);
                            collisionDetection.add(doll);
                        }
                        break;
                    case 3:
                        // add minvo
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Minvo minvo = new Minvo(freePositions.get(i));
                            entities.add(minvo);
                            collisionDetection.add(minvo);
                        }
                        break;
                    case 4:
                        // add kondoria
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Kondoria kondoria = new Kondoria(freePositions.get(i));
                            entities.add(kondoria);
                            collisionDetection.add(kondoria);
                        }
                        break;
                    case 5:
                        // add ovapi
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Ovapi ovapi = new Ovapi(freePositions.get(i));
                            entities.add(ovapi);
                            collisionDetection.add(ovapi);
                        }
                        break;
                    case 6:
                        // add pass
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Pass pass = new Pass(freePositions.get(i));
                            entities.add(pass);
                            collisionDetection.add(pass);
                        }
                        break;
                    case 7:
                        // add pontan
                        for (int j = 0; j < 8 && i < freePositions.size(); j++, i++) {
                            Pontan pontan = new Pontan(freePositions.get(i));
                            entities.add(pontan);
                            collisionDetection.add(pontan);
                        }
                        break;
                }
            }
        }
    }

    private Vector2f getInputVector() {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        Vector2f input = Vector2f.ZERO;
        for (Direction direction : Direction.values()) {
            final Key key = direction.getKey();
            input = input.add(direction.getUnit().mul(keyboardState.getAndClearPressTime(key) / 1e9f));
        }
        // Make sure we're not trying to normalize the zero vector
        if (input.lengthSquared() > 0) {
            input = input.normalize();
        }
        return input;
    }

    @Override
    public void onStop() {
        clearEntities();
        events.unsubscribeAll();
        mapVersion = 0;
    }

    public void subscribeToEvents() {
        events.subscribe();
    }

    public Queue<Event> getEvents() {
        return events;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Button getSelectedButton() {
        if (buttonOrder.size() <= selectedButtonIndex) {
            return null;
        }
        return buttonOrder.get(selectedButtonIndex);
    }

    private static List<Vector2f> getFreePositions(Map map) {
        final List<Vector2f> free = new ArrayList<>();
        final List<Air> freeTiles = map.getTiles(Air.class);
        for (Air freeTile : freeTiles) {
            final Vector2f position = freeTile.getPosition();
            // Reject player starting positions
            if ((position.getFloorX() != 1 || position.getFloorY() != 11 && position.getFloorY() != 10) && (position.getFloorX() != 2 || position.getFloorY() != 11)) {
                free.add(position);
            }
        }
        return free;
    }

    /**
     * Blocks the movement in the desired direction, which is represented as a unit vector.
     *
     * @param movement The movement as a vector
     * @param unitDirection The unit direction to block. Must be a unit to function correctly!
     * @return The new movement but with all motion in the given direction removed
     */
    private static Vector2f blockDirection(Vector2f movement, Vector2f unitDirection) {
        // Check if we have movement in the direction
        if (movement.dot(unitDirection) > 0) {
            // Get motion in the direction and subtracted from total movement
            return movement.sub(movement.mul(unitDirection.abs()));
        }
        // If we don't have any motion, don't change anything
        return movement;
    }

    /**
     * Gets the direction of a collision. This uses the intersection between the two collided objects, which can be obtained with {@link #getIntersection(Collidable, Collidable)}, the object that was
     * collided. The direction found points towards the collided object.
     *
     * @param intersection The intersection from the collision
     * @param other The object that was collided
     * @return The direction of the collision
     */
    private static Direction getCollisionDirection(Intersection intersection, Collidable other) {
        final Vector2f offset = other.getPosition().sub(intersection.center);
        return Direction.fromUnit(offset);
    }

    /**
     * Gets the collision intersection information for two object that are colliding. If the object aren't colliding, the resulting information is undefined.
     *
     * @param object The first object of the collision
     * @param other The second object of the collision
     * @return An intersection object containing the collision information
     */
    private static Intersection getIntersection(Collidable object, Collidable other) {
        final Vector2f intersectMax = object.getBoxMaxPoint().min(other.getBoxMaxPoint());
        final Vector2f intersectMin = object.getBoxMinPoint().max(other.getBoxMinPoint());
        return new Intersection(intersectMax, intersectMin);
    }

    /**
     * Represents an intersection between two colliding objects.
     */
    private static class Intersection {
        /**
         * The size of the intersection box as the diagonal vector.
         */
        private final Vector2f size;
        /**
         * The center of the intersection box (halfway up the diagonal).
         */
        private final Vector2f center;
        /**
         * The area of the intersection box
         */
        private final float area;

        private Intersection(Vector2f max, Vector2f min) {
            size = max.sub(min);
            center = min.add(size.div(2));
            area = size.getX() * size.getY();
        }
    }
}
