package ecse321.fall2014.group3.bomberman.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Direction;
import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.database.Leaderboard.Leader;
import ecse321.fall2014.group3.bomberman.input.Key;
import ecse321.fall2014.group3.bomberman.input.KeyboardState;
import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.mob.Player;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.ButtonEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.SliderEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBoxEntity;
import ecse321.fall2014.group3.bomberman.physics.entity.ui.UIBoxEntity;
import ecse321.fall2014.group3.bomberman.ticking.TickingElement;
import ecse321.fall2014.group3.bomberman.world.Level;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

/**
 *
 */
public class Physics extends TickingElement {
    private static final float PERPENDICULAR_CONTACT_THRESHOLD = 0.05f;
    private static final float SLIDING_CONTACT_THRESHOLD = 0.9f;
    private final Game game;
    private final SweepAndPruneAlgorithm collisionDetection = new SweepAndPruneAlgorithm();
    private final Set<Tile> collidableTiles = new HashSet<>();
    private final Set<Entity> entities = Collections.synchronizedSet(new HashSet<Entity>());
    private final Player player = new Player(Vector2f.ZERO);
    private final List<ButtonEntity> buttonOrder = Collections.synchronizedList(new ArrayList<ButtonEntity>());
    private volatile int selectedButtonIndex;
    private Level currentLevel;
    private long mapVersion = 0;

    public Physics(Game game) {
        super("Physics", 60);
        this.game = game;
    }

    @Override
    public void onStart() {
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
        game.getInput().getKeyboardState().clearAll();
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
        final List<UIBoxEntity> uiEntities = game.getWorld().getLevel().buildUI(game.getSession().getLevel());
        entities.addAll(uiEntities);
        for (UIBoxEntity uiEntity : uiEntities) {
            if (uiEntity instanceof ButtonEntity) {
                buttonOrder.add((ButtonEntity) uiEntity);
            }
        }
        selectedButtonIndex = 0;
        // Add extra entities for leaderboard menu
        if (currentLevel == Level.LEADER_BOARD) {
            final Leader[] top = game.getLeaderboard().getTop(10);
            for (int i = 0; i < top.length && top[i] != null; i++) {
                entities.add(new TextBoxEntity(new Vector2f(4, Interface.VIEW_HEIGHT_TILE - (6 + i)), Vector2f.ONE, top[i].getFormatted()));
            }
        }
    }

    private void doMenuTick(long dt) {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        final int selectedShift = keyboardState.getAndClearPressCount(Key.DOWN) - keyboardState.getAndClearPressCount(Key.UP);
        final int buttonCount = buttonOrder.size();
        final int oldSelected = selectedButtonIndex;
        final int newSelected = ((oldSelected + selectedShift) % buttonCount + buttonCount) % buttonCount;
        if (buttonCount > 0) {
            buttonOrder.get(oldSelected).setSelected(false);
            buttonOrder.get(newSelected).setSelected(true);
        }
        selectedButtonIndex = newSelected;
        final ButtonEntity selectedButton = getSelectedButton();
        if (selectedButton instanceof SliderEntity) {
            final int sliderShift = keyboardState.getAndClearPressCount(Key.RIGHT) - keyboardState.getAndClearPressCount(Key.LEFT);
            ((SliderEntity) selectedButton).add(sliderShift);
        }
    }

    private void setupGame() {
        // Add player
        entities.add(player);
        player.setPosition(Vector2f.ONE);
        collisionDetection.add(player);
        // Add UI
        final String levelString = currentLevel.isBonus() ? "Bonus level " + -currentLevel.getNumber() : "Level " + currentLevel.getNumber();
        entities.add(new TextBoxEntity(new Vector2f(Map.WIDTH / 4f, Map.HEIGHT - 1.25f), new Vector2f(2, 2), levelString));
        // Add enemies
    }

    private void doGameTick(long dt) {
        final Map map = game.getWorld().getMap();
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
            // Find the intersection of the collision (a box) and the direction
            final Intersection intersection = getIntersection(player, collidable);
            final Direction direction = getCollisionDirection(intersection, collidable);
            // Allow for a small amount of contact on the sides to prevent the player from getting stuck in adjacent tiles
            if (intersection.size.dot(direction.getPerpendicularUnit()) < PERPENDICULAR_CONTACT_THRESHOLD) {
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
        mapVersion = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public ButtonEntity getSelectedButton() {
        if (buttonOrder.size() <= selectedButtonIndex) {
            return null;
        }
        return buttonOrder.get(selectedButtonIndex);
    }

    /**
     * Blocks the movement in the desired direction, which is represented as a unit vector.
     *
     * @param movement The movement as a vector
     * @param unitDirection The unit direction to block. Must be a unit to function correctly!
     * @return The new movement but with all motion in the given direction removed
     */
    public static Vector2f blockDirection(Vector2f movement, Vector2f unitDirection) {
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
    public static Direction getCollisionDirection(Intersection intersection, Collidable other) {
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
    public static Intersection getIntersection(Collidable object, Collidable other) {
        final Vector2f intersectMax = object.getBoxMaxPoint().min(other.getBoxMaxPoint());
        final Vector2f intersectMin = object.getBoxMinPoint().max(other.getBoxMinPoint());
        return new Intersection(intersectMax, intersectMin);
    }

    /**
     * Represents an intersection between two colliding objects.
     */
    public static class Intersection {
        /**
         * The max point of the intersection box.
         */
        public final Vector2f max;
        /**
         * The min point of the intersection box.
         */
        public final Vector2f min;
        /**
         * The size of the intersection box as the diagonal vector.
         */
        public final Vector2f size;
        /**
         * The center of the intersection box (halfway up the diagonal).
         */
        public final Vector2f center;

        private Intersection(Vector2f max, Vector2f min) {
            this.max = max;
            this.min = min;
            size = max.sub(min);
            center = min.add(size.div(2));
        }
    }
}
