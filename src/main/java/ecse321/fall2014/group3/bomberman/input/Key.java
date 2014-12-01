/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.input;

import org.lwjgl.input.Keyboard;

/**
 * The Enum Key.
 */
public enum Key {

    /** The up key. */
    UP(Keyboard.KEY_UP),

    /** The down key. */
    DOWN(Keyboard.KEY_DOWN),

    /** The left key. */
    LEFT(Keyboard.KEY_LEFT),

    /** The right key. */
    RIGHT(Keyboard.KEY_RIGHT),

    /** The place bomb key. */
    PLACE(Keyboard.KEY_A),

    /** The exit. */
    EXIT(Keyboard.KEY_ESCAPE),

    /** The detonate. */
    DETONATE(Keyboard.KEY_B);

    /** The Constant COUNT. */
    private static final int COUNT = values().length;
    private final int keyCode;

    private Key(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * Gets the key code.
     *
     * @return the key code
     */
    int getKeyCode() {
        return keyCode;
    }

    /**
     * Checks if is key is down.
     *
     * @return true, if key is down
     */
    boolean isDown() {
        return Keyboard.isKeyDown(keyCode);
    }

    /**
     * Gets the key count.
     *
     * @return the key count
     */
    public static int getCount() {
        return COUNT;
    }
}
