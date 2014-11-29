package ecse321.fall2014.group3.bomberman.input;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

public class KeyboardState {
    private final AtomicLongArray pressTimes = new AtomicLongArray(Key.getCount());
    private final AtomicIntegerArray pressCounts = new AtomicIntegerArray(Key.getCount());

    void incrementPressTime(Key key, long dt) {
        pressTimes.getAndAdd(key.ordinal(), dt);
    }

    void incrementPressCount(Key key, int count) {
        pressCounts.getAndAdd(key.ordinal(), count);
    }

    public long getAndClearPressTime(Key key) {
        return pressTimes.getAndSet(key.ordinal(), 0);
    }

    public int getAndClearPressCount(Key key) {
        return pressCounts.getAndSet(key.ordinal(), 0);
    }
}




