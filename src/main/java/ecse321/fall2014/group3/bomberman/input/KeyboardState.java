package ecse321.fall2014.group3.bomberman.input;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KeyboardState {
    private final Lock lock = new ReentrantLock();
    private final long[] pressTimes = new long[Key.COUNT];
    private final int[] pressCounts = new int[Key.COUNT];

    void incrementPressTime(Key key, long dt) {
        pressTimes[key.ordinal()] += dt;
    }

    void incrementPressCount(Key key, int count) {
        pressCounts[key.ordinal()] += count;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public long getPressTime(Key key) {
        return pressTimes[key.ordinal()];
    }

    public int getPressCount(Key key) {
        return pressCounts[key.ordinal()];
    }

    public void reset(Key key) {
        pressCounts[key.ordinal()] = 0;
        pressTimes[key.ordinal()] = 0;
    }
}




