/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.input;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * The Class KeyboardState.
 */
public class KeyboardState {
    private final AtomicLongArray pressTimes = new AtomicLongArray(Key.getCount());
    private final AtomicIntegerArray pressCounts = new AtomicIntegerArray(Key.getCount());

    /**
     * Increment press time.
     *
     * @param key the key
     * @param dt the time
     */
    void incrementPressTime(Key key, long dt) {
        pressTimes.getAndAdd(key.ordinal(), dt);
    }

    /**
     * Increment press count.
     *
     * @param key the key
     * @param count the count
     */
    void incrementPressCount(Key key, int count) {
        pressCounts.getAndAdd(key.ordinal(), count);
    }

    /**
     * Gets the press time.
     *
     * @param key the key
     * @return the press time
     */
    public long getAndClearPressTime(Key key) {
        return pressTimes.getAndSet(key.ordinal(), 0);
    }

    /**
     * Gets the press count.
     *
     * @param key the key
     * @return the press count
     */
    public int getAndClearPressCount(Key key) {
        return pressCounts.getAndSet(key.ordinal(), 0);
    }
}
