package ecse321.fall2014.group3.bomberman.physics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public class SweepAndPruneAlgorithm {
    private final List<EndPoint> xPoints = new LinkedList<>();
    private final List<EndPoint> yPoints = new LinkedList<>();
    private final boolean doAsserts;

    public SweepAndPruneAlgorithm() {
        this(false);
    }

    public SweepAndPruneAlgorithm(boolean doAsserts) {
        this.doAsserts = doAsserts;
    }

    public void add(Collidable collidable) {
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

    public void remove(Collidable collidable) {
        removeEndPoints(xPoints, collidable);
        removeEndPoints(yPoints, collidable);

        if (doAsserts) {
            assertSorted(xPoints);
            assertSorted(yPoints);
        }
    }

    public void update() {
        updateEndPoints(xPoints);
        updateEndPoints(yPoints);

        sortEndPoints(xPoints);
        sortEndPoints(yPoints);

        if (doAsserts) {
            assertSorted(xPoints);
            assertSorted(yPoints);
        }
    }

    public int getCount() {
        return xPoints.size() / 2;
    }

    private static void sortEndPoints(List<EndPoint> points) {
        int size = points.size() - 1;
        for (int i = 0; i < size; i++) {
            final ListIterator<EndPoint> iterator = points.listIterator(i);
            EndPoint previous = iterator.next();
            while (iterator.hasNext()) {
                final EndPoint current = iterator.next();
                if (current.compareTo(previous) < 0) {
                    iterator.remove();
                    boolean wasSmaller = false;
                    while (iterator.hasPrevious() && (wasSmaller = current.compareTo(iterator.previous()) < 0))
                        ;
                    if (!wasSmaller) {
                        iterator.next();
                    }
                    iterator.add(current);
                    break;
                }
                previous = current;
                i++;
            }
        }
    }

    private static void updateEndPoints(List<EndPoint> points) {
        for (EndPoint point : points) {
            point.update();
        }
    }

    private static void removeEndPoints(List<EndPoint> points, Collidable collidable) {
        for (Iterator<EndPoint> iterator = points.iterator(); iterator.hasNext(); ) {
            if (iterator.next().hasOwner(collidable)) {
                iterator.remove();
            }
        }
    }

    private static <T extends Comparable<T>> void insertSorted(List<T> list, T element) {
        int i = 0;
        for (Comparable<T> existing : list) {
            if (existing.compareTo(element) >= 0) {
                break;
            }
            i++;
        }
        list.add(i, element);
    }

    private static void assertSorted(List<EndPoint> points) {
        if (points.size() <= 1) {
            return;
        }
        final Iterator<EndPoint> iterator = points.iterator();
        EndPoint previous = iterator.next();
        while (iterator.hasNext()) {
            EndPoint current = iterator.next();
            if (current.compareTo(previous) < 0) {
                throw new AssertionError("End points are not sorted");
            }
            previous = current;
        }
    }

    private static class EndPoint implements Comparable<EndPoint> {
        private final Collidable owner;
        private final boolean isX;
        private final boolean isMax;
        private float point;

        private EndPoint(Collidable owner, boolean isX, boolean isMax) {
            this.owner = owner;
            this.isX = isX;
            this.isMax = isMax;
            update();
        }

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

        private boolean hasOwner(Collidable collidable) {
            // we need to check references here, not values
            return owner == collidable;
        }

        @Override
        public int compareTo(EndPoint other) {
            return (int) Math.signum(this.point - other.point);
        }

        @Override
        public String toString() {
            return Float.toString(point);
        }
    }
}
