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
package ecse321.fall2014.group3.bomberman.world.tile.powerup;

import java.util.Map;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.world.tile.CollidableTile;

/**
 * The Class PowerUP.
 */
public abstract class PowerUP extends CollidableTile {
    private final boolean canUpgrade;

    /**
     * Instantiates a new power up.
     *
     * @param position the position
     * @param canUpgrade if powerUP can be upgraded
     */
    protected PowerUP(Vector2f position, boolean canUpgrade) {
        super(position);
        this.canUpgrade = canUpgrade;
    }

    /**
     * Can be upgraded.
     *
     * @return true, if powerUp can be upgraded
     */
    public boolean canBeUpgraded() {
        return canUpgrade;
    }

    /**
     * Serialize the powerUPS.
     * <pre>
     * Bit format:
     *     0..2: Bomb upgrade level
     *     3..5: Flame upgrade level
     *     6..8: Speed upgrade level
     *     9   : Has bomb pass
     *     10  : Has detonator
     *     11  : Has flame pass
     *     12  : Has mystery
     *     13  : Has wall pass
     * </pre>
     *
     * @param powerUPs the powerUPs
     * @return the int
     */
    public static int serialize(Map<Class<? extends PowerUP>, Integer> powerUPs) {
        int serialized = 0;
        serialized = setLevelBits(serialized, 0, powerUPs.get(BombUpgrade.class));
        serialized = setLevelBits(serialized, 3, powerUPs.get(FlameUpgrade.class));
        serialized = setLevelBits(serialized, 6, powerUPs.get(SpeedUpgrade.class));
        serialized = setBitIfNotNull(serialized, 9, powerUPs.get(BombPass.class));
        serialized = setBitIfNotNull(serialized, 10, powerUPs.get(Detonator.class));
        serialized = setBitIfNotNull(serialized, 11, powerUPs.get(FlamePass.class));
        serialized = setBitIfNotNull(serialized, 12, powerUPs.get(Mystery.class));
        serialized = setBitIfNotNull(serialized, 13, powerUPs.get(WallPass.class));
        return serialized;
    }

    //set the bits for powerUps
    private static int setLevelBits(int bits, int n, Integer level) {
        if (level != null) {
            bits |= (level & 0b111) << n;
        }
        return bits;
    }

    //set the bits for powerUps in its not null
    private static int setBitIfNotNull(int bits, int n, Integer i) {
        if (i != null) {
            bits |= 0b1 << n;
        }
        return bits;
    }

    /**
     * Deserialize the PowerUPS.
     *
     * @param serialized the serialized PowerUps
     * @param powerUPs the powerUPs
     */
    public static void deserialize(int serialized, Map<Class<? extends PowerUP>, Integer> powerUPs) {
        addLevelFromBits(serialized, 0, BombUpgrade.class, powerUPs);
        addLevelFromBits(serialized, 3, FlameUpgrade.class, powerUPs);
        addLevelFromBits(serialized, 6, SpeedUpgrade.class, powerUPs);
        addIfBitSet(serialized, 9, BombPass.class, powerUPs);
        addIfBitSet(serialized, 10, Detonator.class, powerUPs);
        addIfBitSet(serialized, 11, FlamePass.class, powerUPs);
        addIfBitSet(serialized, 12, Mystery.class, powerUPs);
        addIfBitSet(serialized, 13, WallPass.class, powerUPs);
    }

    //adds level from bits
    private static void addLevelFromBits(int bits, int n, Class<? extends PowerUP> add, Map<Class<? extends PowerUP>, Integer> to) {
        final int level = bits >>> n & 0b111;
        if (level != 0) {
            to.put(add, level);
        }
    }

    //add if bits are set
    private static void addIfBitSet(int bits, int n, Class<? extends PowerUP> add, Map<Class<? extends PowerUP>, Integer> to) {
        if ((bits >>> n & 0b1) == 1) {
            to.put(add, 1);
        }
    }
}
