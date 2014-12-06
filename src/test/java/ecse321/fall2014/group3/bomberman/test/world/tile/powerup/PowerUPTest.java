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
package ecse321.fall2014.group3.bomberman.test.world.tile.powerup;

import java.util.HashMap;
import java.util.Map;

import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombPass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.BombUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlamePass;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.FlameUpgrade;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;
import ecse321.fall2014.group3.bomberman.world.tile.powerup.WallPass;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class PowerUPTest {
    @Test
    public void testSerialize() {
        Map<Class<? extends PowerUP>, Integer> powerUPs = new HashMap<>();
        powerUPs.put(BombPass.class, 1);
        powerUPs.put(BombUpgrade.class, 4);
        powerUPs.put(FlamePass.class, 1);
        powerUPs.put(FlameUpgrade.class, 2);
        powerUPs.put(WallPass.class, 1);
        int serialize = PowerUP.serialize(powerUPs);
        Map<Class<? extends PowerUP>, Integer> recovered = new HashMap<>();
        PowerUP.deserialize(serialize, recovered);
        Assert.assertEquals(powerUPs, recovered);
    }
}
