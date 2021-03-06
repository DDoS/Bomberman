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
package ecse321.fall2014.group3.bomberman.test.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.physics.Collidable;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.SweepAndPruneAlgorithm;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class SweepAndPruneAlgorithmTest {
    private static final Random RANDOM = new Random();
    @Test
    public void test() {
        // Perform test in assertion mode, the algorithm will detect if its state is invalid and throw an exception
        final SweepAndPruneAlgorithm algorithm = new SweepAndPruneAlgorithm(true);
        final List<TestCollidable> testCollidables = new ArrayList<>();

        // Test adding
        final int size = 100;
        final int halfSize = size / 2;
        for (int i = 0; i < size; i++) {
            final TestCollidable testCollidable = new TestCollidable();
            testCollidables.add(testCollidable);
            algorithm.add(testCollidable);
        }
        Assert.assertEquals(size, algorithm.getCount());

        // Test updates
        // Collide the first half with random collidables in the second half
        for (int i = 0; i < halfSize; i++) {
            testCollidables.get(i).collideWith(testCollidables.get(RANDOM.nextInt(halfSize) + halfSize));
        }
        // Update and verify the validity of the results
        algorithm.update();
        for (TestCollidable testCollidable : testCollidables) {
            testCollidable.verifyCollisions();
        }
        Assert.assertEquals(size, algorithm.getCount());

        // Test removal
        for (TestCollidable testCollidable : testCollidables) {
            algorithm.remove(testCollidable);
        }
        Assert.assertEquals(0, algorithm.getCount());
    }

    private static class TestCollidable extends Collidable {
        private final List<Collidable> expectedCollisions = new ArrayList<>();

        private TestCollidable() {
            super(randomVector2f(50), new CollisionBox(randomVector2f(4).abs()));
        }

        // Moves this box and changes the box shape to guarantee a collision
        private void collideWith(TestCollidable other) {
            final Vector2f offset = Vector2f.createRandomDirection(RANDOM).mul(5);
            position = other.getPosition().add(offset);
            collisionBox = new CollisionBox(offset.mul(2).abs());
            expectedCollisions.add(other);
            other.expectedCollisions.add(this);
        }

        private void verifyCollisions() {
            // Check if all expected collisions are in the actual list
            final Set<Collidable> actualCollisions = getCollisionList();
            int failCount = 0;
            for (Collidable expectedCollision : expectedCollisions) {
                if (!actualCollisions.contains(expectedCollision)) {
                    failCount++;
                }
            }
            if (failCount > 0) {
                Assert.fail(failCount + " out of " + expectedCollisions.size() + " collisions failed to be detected");
            }
            // the lists might not match exactly because the randomness can cause extra unexpected collisions
        }
    }

    private static Vector2f randomVector2f(float range) {
        return new Vector2f(Math.random(), Math.random()).sub(0.5, 0.5).mul(range * 2);
    }
}
