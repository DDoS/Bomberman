/*
 * This file is part of Bomberman, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Group 3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * @author Group 3
 */
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

/**
 * The Class Map.
 */
public class Map {

    /** The Constant Height. */
    public static final int HEIGHT = 13;
    /** The Constant Width. */
    public static final int WIDTH = 31;
    private final Tile tiles[][] = new Tile[HEIGHT][WIDTH];
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile long version = 0;

    /**
     * Instantiates a new map.
     */
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

    /**
     * Gets the tile.
     *
     * @param x the x position
     * @param y the y position
     * @return the tile
     */
    public Tile getTile(int x, int y) {
        return getTile(new Vector2i(x, y));
    }

    /**
     * Gets the tile.
     *
     * @param pos the vector2f position
     * @return the tile
     */
    public Tile getTile(Vector2f pos) {
        return getTile(pos.toInt());
    }

    /**
     * Gets the tile.
     *
     * @param pos the vector2i position
     * @return the tile
     */
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

    /**
     * Gets the tiles.
     *
     * @param <T> the generic type
     * @param type the type
     * @return the tiles
     */
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

    /**
     * Checks if there is tile at the position and it has the right type.
     *
     * @param position the position
     * @param type the type
     * @return true, if is tile
     */
    public boolean isTile(Vector2f position, Class<? extends Tile> type) {
        return type.isAssignableFrom(getTile(position).getClass());
    }

    /**
     * Sets the tile.
     *
     * @param x the x position
     * @param y the y position
     * @param type the type of tile
     */
    void setTile(int x, int y, Class<? extends Tile> type) {
        setTile(new Vector2i(x, y), type);
    }

    /**
     * Sets the tile.
     *
     * @param pos the vector2f position
     * @param type the type of tile
     */
    void setTile(Vector2f pos, Class<? extends Tile> type) {
        setTile(pos.toInt(), type);
    }

    /**
     * Sets the tile.
     *
     * @param pos the vector2i position
     * @param type the type of tile
     */
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
        } catch (NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException ex) {
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

    /**
     * Increment version.
     */
    void incrementVersion() {
        version++;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    //checks for out of bounds
    private boolean outOfBounds(Vector2i pos) {
        return pos.getX() < 0 || pos.getX() >= WIDTH || pos.getY() < 0 || pos.getY() >= HEIGHT;
    }
}
