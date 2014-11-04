package ecse321.fall2014.group3.bomberman.physics;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.input.Key;
import ecse321.fall2014.group3.bomberman.input.KeyboardState;
import ecse321.fall2014.group3.bomberman.physics.entity.Direction;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.Player;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

/**
 *
 */
public class Physics extends TickingElement {
    private static final float PERPENDICULAR_CONTACT_THRESHOLD = 0.05f;
    private final Game game;
    private final SweepAndPruneAlgorithm collisionDetection = new SweepAndPruneAlgorithm();
    private final Set<Tile> collidableTiles = new HashSet<>();
    private final Set<Entity> entities = Collections.synchronizedSet(new HashSet<Entity>());
    private final Player player = new Player(Vector2f.ZERO);
    private long mapVersion = 0;

    public Physics(Game game) {
        super("Physics", 60);
        this.game = game;
    }

    @Override
    public void onStart() {
        entities.add(player);
        player.setPosition(Vector2f.ONE);
        collisionDetection.add(player);
    }

    @Override
    public void onTick(long dt) {
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

        Vector2f movement = getInputVector().mul(player.getSpeed() * dt / 1e9f);
        for (Collidable collidable : player.getCollisionList()) {
            final Intersection intersection = getIntersection(player, collidable);
            final Direction direction = getCollisionDirection(intersection, collidable);

            if (intersection.size.dot(direction.getPerpendicularUnit()) < PERPENDICULAR_CONTACT_THRESHOLD) {
                continue;
            }

            movement = blockDirection(movement, direction);
        }
        player.setPosition(player.getPosition().add(movement));
        // TODO: update velocity
    }

    private Vector2f getInputVector() {
        final KeyboardState keyboardState = game.getInput().getKeyboardState();
        keyboardState.lock();
        try {
            Vector2f input = Vector2f.ZERO;
            for (Direction direction : Direction.values()) {
                final Key key = direction.getKey();
                input = input.add(direction.getUnit().mul(keyboardState.getPressTime(key) / 1e9f));
                keyboardState.reset(key);
            }
            // Make sure we're not trying to normalize the zero vector
            if (input.lengthSquared() > 0) {
                input = input.normalize();
            }
            return input;
        } finally {
            keyboardState.unlock();
        }
    }

    @Override
    public void onStop() {
        collisionDetection.clear();
        entities.clear();
        mapVersion = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    private static Vector2f blockDirection(Vector2f movement, Direction direction) {
        switch (direction) {
            case RIGHT:
                return movement.min(0, Float.MAX_VALUE);
            case UP:
                return movement.min(Float.MAX_VALUE, 0);
            case LEFT:
                return movement.max(0, -Float.MAX_VALUE);
            case DOWN:
                return movement.max(-Float.MAX_VALUE, 0);
            default:
                return movement;
        }
    }

    private static Direction getCollisionDirection(Intersection intersection, Collidable other) {
        final Vector2f offset = other.getPosition().sub(intersection.center);
        return Direction.fromUnit(offset);
    }

    private static Intersection getIntersection(Collidable object, Collidable other) {
        final Vector2f intersectMax = object.getBoxMaxPoint().min(other.getBoxMaxPoint());
        final Vector2f intersectMin = object.getBoxMinPoint().max(other.getBoxMinPoint());
        return new Intersection(intersectMax, intersectMin);
    }

    private static class Intersection {
        private final Vector2f max, min, size, center;

        private Intersection(Vector2f max, Vector2f min) {
            this.max = max;
            this.min = min;
            size = max.sub(min);
            center = min.add(size.div(2));
        }
    }
}
