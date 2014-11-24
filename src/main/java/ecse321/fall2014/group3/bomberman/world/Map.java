package ecse321.fall2014.group3.bomberman.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector2i;

import ecse321.fall2014.group3.bomberman.world.tile.Air;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;

public class Map {
    public static final int HEIGHT = 13, WIDTH = 31;
    private final Tile tiles[][] = new Tile[HEIGHT][WIDTH];
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile long version = 0;

    public Map() {
        final Lock write = lock.writeLock();
        write.lock();
        try {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    tiles[y][x] = new Air(new Vector2f(x, y));
                }
            }
        } finally {
            write.unlock();
        }
    }

    public Tile getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    public Tile getTile(Vector2f pos) {
        return getTile(pos.toInt());
    }

    public Tile getTile(Vector2i pos) {
        if (outOfBounds(pos)) {
            return null;
        }
        final Lock read = lock.readLock();
        read.lock();
        try {
            return tiles[pos.getY()][pos.getX()];
        } finally {
            read.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Tile> List<T> getTiles(Class<T> type) {
        final List<T> tileList = new ArrayList<>();
        final Lock read = lock.readLock();
        read.lock();
        try {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    final Tile tile = tiles[y][x];
                    if (type.isAssignableFrom(tile.getClass())) {
                        tileList.add((T) tile);
                    }
                }
            }
            return tileList;
        } finally {
            read.unlock();
        }
    }

    public boolean isTile(Vector2f position, Class<? extends Tile> type) {
        return type.isAssignableFrom(getTile(position).getClass());
    }

    void setTile(int x, int y, Class<? extends Tile> type) {
        setTile(new Vector2i(x, y), type);
    }

    void setTile(Vector2f pos, Class<? extends Tile> type) {
        setTile(pos.toInt(), type);
    }

    void setTile(Vector2i pos, Class<? extends Tile> type) {
        if (outOfBounds(pos)) {
            return;
        }
        final Tile tile;
        try {
            // The use of reflection to init the tile ourselves guarantees that its position will be correct
            // This has the disadvantage of no compile time type checks and being a bit slower
            final Constructor<? extends Tile> constructor = type.getDeclaredConstructor(Vector2f.class);
            constructor.setAccessible(true);
            tile = constructor.newInstance(pos.toFloat());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalArgumentException("Tile couldn't be created", ex);
        }
        final Lock write = lock.writeLock();
        write.lock();
        try {
            tiles[pos.getY()][pos.getX()] = tile;
        } finally {
            write.unlock();
        }
    }

    void incrementVersion() {
        version++;
    }

    public long getVersion() {
        return version;
    }

    Lock getWriteLock() {
        return lock.writeLock();
    }

    private boolean outOfBounds(Vector2i pos) {
        return pos.getX() < 0 || pos.getX() >= WIDTH || pos.getY() < 0 || pos.getY() >= HEIGHT;
    }
}
