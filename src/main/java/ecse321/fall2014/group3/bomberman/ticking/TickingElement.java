/*
 * This file is part of Flow Commons, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013 Spout LLC <https://spout.org/>
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
package ecse321.fall2014.group3.bomberman.ticking;

/**
 * Represents an element that ticks at a specific TPS.
 * <p/>
 * This is a library file from com.flowpowered.commons.ticking
 */
public abstract class TickingElement {
    private final String name;
    private final int tps;
    private final ThreadGroup group;
    private volatile TPSLimitedThread thread;

    /**
     * Instantiates a new ticking element.
     *
     * @param name the name
     * @param tps the ticks per second
     */
    public TickingElement(String name, int tps) {
        this.name = name;
        this.tps = tps;
        this.group = new ThreadGroup(name + " ThreadGroup");
    }

    /**
     * Start.
     */
    public final void start() {
        synchronized (this) {
            if (thread == null) {
                thread = new TPSLimitedThread(group, name, this, tps);
                thread.start();
            }
        }
    }

    /**
     * Stop.
     */
    public final void stop() {
        synchronized (this) {
            if (thread != null) {
                thread.terminate();
                thread = null;
            }
        }
    }

    /**
     * Checks if is running.
     *
     * @return true, if is running
     */
    public final boolean isRunning() {
        return thread != null && thread.isRunning();
    }

    /**
     * Gets the thread.
     *
     * @return the thread
     */
    public TPSLimitedThread getThread() {
        return thread;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public ThreadGroup getGroup() {
        return group;
    }

    /**
     * On start.
     */
    public abstract void onStart();

    /**
     * On tick.
     *
     * @param dt the dt
     */
    public abstract void onTick(long dt);

    /**
     * On stop.
     */
    public abstract void onStop();
}
