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
 * This file is part of Client, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013-2014 Spoutcraft <http://spoutcraft.org/>
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
package ecse321.fall2014.group3.bomberman;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import ecse321.fall2014.group3.bomberman.database.Leaderboard;
import ecse321.fall2014.group3.bomberman.database.Session;
import ecse321.fall2014.group3.bomberman.input.Input;
import ecse321.fall2014.group3.bomberman.nterface.Interface;
import ecse321.fall2014.group3.bomberman.physics.Physics;
import ecse321.fall2014.group3.bomberman.world.World;

/**
 * The game class. This is based on an implementation used in older project of
 * mine: https://github.com/Spoutcraft/Client/blob/master/src/main/java/org/
 * spoutcraft/client/Game.java
 */
public class Game {
    // A semaphore with no permits, so that the first acquire() call blocks
    private final Semaphore semaphore = new Semaphore(0);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final Session session;
    private final Leaderboard leaderboard;
    private final World world;
    private final Physics physics;
    private final Interface nterface;
    private final Input input;

    /**
     * Instantiates a new game.
     *
     * @param session the session
     * @param leaderboard the leaderboard
     */
    public Game(Session session, Leaderboard leaderboard) {
        this.session = session;
        this.leaderboard = leaderboard;
        world = new World(this);
        physics = new Physics(this);
        nterface = new Interface(this);
        input = new Input(this);
    }

    private void start() {
        world.start();
        physics.start();
        nterface.start();
        input.start();
    }

    private void stop() {
        nterface.stop();
        physics.stop();
        world.stop();
        input.stop();
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Gets the leaderboard.
     *
     * @return the leaderboard
     */
    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    /**
     * Gets the world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets the physics.
     *
     * @return the physics
     */
    public Physics getPhysics() {
        return physics;
    }

    /**
     * Gets the interface.
     *
     * @return the interface
     */
    public Interface getInterface() {
        return nterface;
    }

    /**
     * Gets the input.
     *
     * @return the input
     */
    public Input getInput() {
        return input;
    }

    /**
     * Starts the game and causes the current thread to wait until the
     * {@link #close()} method is called. When this happens, the thread resumes
     * and the game is stopped. Interrupting the thread will not cause it to
     * close, only calling {@link #close()} will. Calls to {@link #close()}
     * before open() are not counted.
     */
    public void open() {
        // Only start the game if running has a value of false, in which case it's set to true and the if statement passes
        if (running.compareAndSet(false, true)) {
            // Start the threads, which might release permits by calling close() before all are started
            start();
            // Attempts to acquire a permit, but since none are available (except for the situation stated above), the thread blocks
            semaphore.acquireUninterruptibly();
            // A permit was acquired, which means close() was called; so we stop game. The available permit count returns to zero
            stop();
        }
    }

    /**
     * Wakes up the thread that has opened the game (by having called
     * {@link #open()}) and allows it to resume it's activity to trigger the end
     * of the game.
     */
    public void close() {
        // Only stop the game if running has a value of true, in which case it's set to false and the if statement passes
        if (running.compareAndSet(true, false)) {
            // Release a permit (which doesn't need to be held by the thread in the first place),
            // allowing the main thread to acquire one and resume to close the game
            semaphore.release();
            // The available permit count is now non-zero
        }
    }

    /**
     * Returns true if the game is running, false if otherwise.
     *
     * @return Whether or not the game is running
     */
    public boolean isRunning() {
        return running.get();
    }
}
