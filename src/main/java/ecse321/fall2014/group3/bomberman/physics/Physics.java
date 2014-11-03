package ecse321.fall2014.group3.bomberman.physics;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.flowpowered.commons.ticking.TickingElement;
import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.Game;
import ecse321.fall2014.group3.bomberman.physics.entity.Direction;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;
import ecse321.fall2014.group3.bomberman.physics.entity.Player;
import ecse321.fall2014.group3.bomberman.world.Map;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

/**
 *
 */
public class Physics extends TickingElement {
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

        Vector2f movement = getInputVector(dt).mul(player.getSpeed());
        for (Collidable collidable : player.getCollisionList()) {
            movement = blockDirection(movement, getCollisionDirection(player, collidable));
        }
        player.setPosition(player.getPosition().add(movement));
        // TODO: update velocity
    }

    private Vector2f getInputVector(long dt) {
        // TODO: implement me when input is done
        return Vector2f.UNIT_X.mul(dt / 1e9f);
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

    private static Direction getCollisionDirection(Collidable object, Collidable with) {
        final Vector2f offset = with.getPosition().sub(object.getPosition());
        return Direction.fromUnit(offset);
    }
}
