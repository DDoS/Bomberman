package ecse321.fall2014.group3.bomberman.physics;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.flowpowered.math.vector.Vector2f;

/**
 * An implementation of sweep and prune for collision detection on 2D axis-aligned bounding boxes (AABB). Detection is achieved using sorting on the x and y axes of the box end points. After an
 * update, the collision list of the {@link ecse321.fall2014.group3.bomberman.physics.Collidable}s present in the detection will reflect the current state of collisions.
 * <p/>
 * This uses the temporal coherence of the physics to it's advantage: it's very unlikely that the boxes will move a lot between updates because of their high frequency (60Hz). This means that
 * insertion sort can be performed with little cost on each update thanks to it's good performance and mostly sorted lists.
 */
public class SweepAndPruneAlgorithm {
    private final Set<Collidable> collidables = new HashSet<>();
    private final List<EndPoint> xPoints = new LinkedList<>();
    private final List<EndPoint> yPoints = new LinkedList<>();
    private final boolean doAsserts;

    /**
     * Constructs a new sweep and prune algorithm with disabled asserts.
     */
    public SweepAndPruneAlgorithm() {
        this(false);
    }

    /**
     * Constructs a new sweep and prune algorithm.
     *
     * @param doAsserts Whether or not to enable asserts. This is meant for testing and you should keep this disabled as it adds a lot of overhead. Assert mode throws exception whenever the internal
     * state of the algorithm becomes invalid.
     */
    public SweepAndPruneAlgorithm(boolean doAsserts) {
        this.doAsserts = doAsserts;
    }

    /**
     * Adds a collidable to the algorithm so that detection is now performed on it. The collision list will be updated on the next call to {@link #update()}.
     *
     * @param collidable The collidable to add
     */
    public void add(Collidable collidable) {
        collidables.add(collidable);

        final EndPoint xMax = new EndPoint(collidable, true, true);
        final EndPoint xMin = new EndPoint(collidable, true, false);
        insertSorted(xPoints, xMax);
        insertSorted(xPoints, xMin);

        final EndPoint yMax = new EndPoint(collidable, false, true);
        final EndPoint yMin = new EndPoint(collidable, false, false);
        insertSorted(yPoints, yMax);
        insertSorted(yPoints, yMin);

        if (doAsserts) {
            assertSorted(xPoints);
            assertSorted(yPoints);
        }
    }

    /**
     * Removes a collidable from the algorithm. The collision list is cleared.
     *
     * @param collidable The collidable to remove
     */
    public void remove(Collidable collidable) {
        collidables.remove(collidable);

        removeEndPoints(xPoints, collidable);
        removeEndPoints(yPoints, collidable);

        if (doAsserts) {
            assertSorted(xPoints);
            assertSorted(yPoints);
        }

        collidable.clearCollisionList();
    }

    /**
     * Updates the collision lists of the collidables in the detector to reflect the new state of the collisions.
     */
    public void update() {
        updateEndPoints(xPoints);
        updateEndPoints(yPoints);

        insertionSort(xPoints);
        insertionSort(yPoints);

        if (doAsserts) {
            assertSorted(xPoints);
            assertSorted(yPoints);
        }

        final Set<CollidingPair> colliding = intersect(computeCollisions(xPoints), computeCollisions(yPoints));

        for (Collidable collidable : collidables) {
            collidable.clearCollisionList();
            collidable.clearCollisionList();
        }

        for (CollidingPair pair : colliding) {
            pair.updateCollisionLists();
        }
    }

    /**
     * Returns the number of collidables in the detector.
     *
     * @return The count of collidables
     */
    public int getCount() {
        return collidables.size();
    }

    /**
     * Removes all collidables from the detector. The collision list are cleared.
     */
    public void clear() {
        for (Collidable collidable : collidables) {
            collidable.clearCollisionList();
        }
        collidables.clear();
        xPoints.clear();
        yPoints.clear();
    }

    // Finds all colliding pairs in the sorted list of endpoints
    private static Set<CollidingPair> computeCollisions(List<EndPoint> points) {
        final Set<CollidingPair> collidingPairs = new HashSet<>();

        final Set<Collidable> openSet = new HashSet<>();
        for (EndPoint point : points) {
            final Collidable current = point.getOwner();
            if (point.isMin()) {
                // minimum points are first, so add box to open set
                openSet.add(current);
            } else {
                // max points are last, so remove box from open set
                openSet.remove(current);
                // collide with all the remaining objects in open set
                for (Collidable collidable : openSet) {
                    collidingPairs.add(new CollidingPair(current, collidable));
                }
            }
        }

        return collidingPairs;
    }

    // Sorts the list using insertion sort and list iterators
    private static <T extends Comparable<T>> void insertionSort(List<T> points) {
        int size = points.size() - 1;
        for (int i = 0; i < size; i++) {
            final ListIterator<T> iterator = points.listIterator(i);
            T previous = iterator.next();
            while (iterator.hasNext()) {
                final T current = iterator.next();
                // search for an unsorted entry
                if (current.compareTo(previous) < 0) {
                    iterator.remove();
                    boolean wasSmaller = false;
                    while (iterator.hasPrevious() && (wasSmaller = current.compareTo(iterator.previous()) < 0)) {
                        // this loop finds the insertion point to make this list sorted
                    }
                    // if the element was larger then everything, we add to the end of the list
                    if (!wasSmaller) {
                        iterator.next();
                    }
                    iterator.add(current);
                    break;
                }
                previous = current;
                // skip over sorted entries
                i++;
            }
        }
    }

    // Update all the endpoints to reflect the state of their parent collidable
    private static void updateEndPoints(List<EndPoint> points) {
        for (EndPoint point : points) {
            point.update();
        }
    }

    // Remove the collidable's endpoints from the list
    private static void removeEndPoints(List<EndPoint> points, Collidable collidable) {
        for (Iterator<EndPoint> iterator = points.iterator(); iterator.hasNext(); ) {
            if (iterator.next().hasOwner(collidable)) {
                iterator.remove();
            }
        }
    }

    // Inserts the element in the list at a position that keeps it sorted
    private static <T extends Comparable<T>> void insertSorted(List<T> list, T element) {
        final ListIterator<T> iterator = list.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().compareTo(element) >= 0) {
                iterator.previous();
                break;
            }
        }
        iterator.add(element);
    }

    // Performs intersection on the two sets, altering the first argument as the result
    private static <T> Set<T> intersect(Set<T> a, Set<T> b) {
        for (Iterator<T> iterator = a.iterator(); iterator.hasNext(); ) {
            if (!b.contains(iterator.next())) {
                iterator.remove();
            }
        }
        return a;
    }

    // Asserts that a list is sorted, throws an exception if that's not the case
    private static <T extends Comparable<T>> void assertSorted(List<T> points) {
        if (points.size() <= 1) {
            return;
        }
        final Iterator<T> iterator = points.iterator();
        T previous = iterator.next();
        while (iterator.hasNext()) {
            final T current = iterator.next();
            if (current.compareTo(previous) < 0) {
                throw new AssertionError("End points are not sorted");
            }
            previous = current;
        }
    }

    // Represent an endpoint of an AABB on one axis
    private static class EndPoint implements Comparable<EndPoint> {
        private final Collidable owner;
        private final boolean isX;
        private final boolean isMax;
        private float point;

        // Creates an endpoint from the owner, the axis, and the position on the AABB
        private EndPoint(Collidable owner, boolean isX, boolean isMax) {
            this.owner = owner;
            this.isX = isX;
            this.isMax = isMax;
            update();
        }

        // Updates the endpoint to match the state of it's owner
        private void update() {
            final Vector2f vec;
            if (isMax) {
                vec = owner.getBoxMaxPoint();
            } else {
                vec = owner.getBoxMinPoint();
            }
            if (isX) {
                point = vec.getX();
            } else {
                point = vec.getY();
            }
        }

        // Returns true if the collidable is the owner of the endpoint
        private boolean hasOwner(Collidable collidable) {
            return owner.equals(collidable);
        }

        // Returns the owner of this collidable
        private Collidable getOwner() {
            return owner;
        }

        // Returns whether or not this is the min point (if not, it's the max)
        private boolean isMin() {
            return !isMax;
        }

        @Override
        public int compareTo(EndPoint other) {
            return (int) Math.signum(this.point - other.point);
        }

        @Override
        public String toString() {
            return owner + (isMax ? " max :" : " min :") + point;
        }
    }

    // Represents a pair of colliding collidables
    private static class CollidingPair {
        private final Collidable first, second;

        // Constructs the pair from the two involved collidables
        private CollidingPair(Collidable first, Collidable second) {
            if (first.getID() == second.getID()) {
                throw new IllegalArgumentException("A box cannot collide with itself");
            }
            // Sort so pairs of the same objects are guaranteed to be equal
            if (first.getID() > second.getID()) {
                this.first = second;
                this.second = first;
            } else {
                this.first = first;
                this.second = second;
            }
        }

        // Gets the first collidable in the pair
        private Collidable getFirst() {
            return first;
        }

        // Gets the second collidable in the pair
        private Collidable getSecond() {
            return second;
        }

        // Updates the collision lists of each collidable so that they include each other
        private void updateCollisionLists() {
            getFirst().addColliding(getSecond());
            getSecond().addColliding(getFirst());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof CollidingPair)) {
                return false;
            }
            final CollidingPair that = (CollidingPair) o;
            return first.equals(that.first) && second.equals(that.second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() ^ second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.getID() + ", " + second.getID() + ")";
        }
    }
}
