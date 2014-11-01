package ecse321.fall2014.group3.bomberman.test.physics;

import java.util.ArrayList;
import java.util.List;

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
    @Test
    public void test() {
        // Perform test in assertion mode, the algorithm will detect if its state is invalid and throw an exception
        final SweepAndPruneAlgorithm algorithm = new SweepAndPruneAlgorithm(true);

        final List<TestCollidable> testCollidables = new ArrayList<>();
        // Test adding
        final int size = 100;
        for (int i = 0; i < size; i++) {
            final TestCollidable testCollidable = new TestCollidable();
            testCollidables.add(testCollidable);
            algorithm.add(testCollidable);
        }
        Assert.assertEquals(size, algorithm.getCount());
        // Test updates
        for (TestCollidable testCollidable : testCollidables) {
            testCollidable.randomize();
        }
        algorithm.update();
        Assert.assertEquals(size, algorithm.getCount());
        // Test removal
        for (TestCollidable testCollidable : testCollidables) {
            algorithm.remove(testCollidable);
        }
        Assert.assertEquals(0, algorithm.getCount());
    }

    private static class TestCollidable extends Collidable {
        private TestCollidable() {
            randomize();
        }

        private void randomize() {
            position = randomVector2f();
            collisionBox = new CollisionBox(randomVector2f());
        }
    }

    private static Vector2f randomVector2f() {
        return new Vector2f(Math.random(), Math.random()).sub(0.5, 0.5).mul(20);
    }
}
