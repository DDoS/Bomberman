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
package ecse321.fall2014.group3.bomberman.event;

import ecse321.fall2014.group3.bomberman.world.tile.ExitWay;
import ecse321.fall2014.group3.bomberman.world.tile.Tile;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

/**
 * The Class ExitWayOrPowerUPDestroyedEvent.
 */
public class ExitWayOrPowerUPDestroyedEvent extends Event {
    private final boolean exitWay;

    /**
     * Instantiates a new exitWay or powerUP destroyed event.
     *
     * @param destroyed the destroyed tile
     */
    public ExitWayOrPowerUPDestroyedEvent(Tile destroyed) {
        exitWay = destroyed instanceof ExitWay;
        if (!exitWay && !(destroyed instanceof PowerUP)) {
            throw new IllegalArgumentException("Expected either an exit way or power UP tile");
        }
    }

    /**
     * Checks if a exitWay tile was destroyed.
     *
     * @return true, if is exitWay tile was destroyed.
     */
    public boolean isExitWay() {
        return exitWay;
    }

    /**
     * Checks if a powerUP tile was destroyed.
     *
     * @return true, if is powerUp tile was destroyed.
     */
    public boolean isPowerUP() {
        return !isExitWay();
    }
}
