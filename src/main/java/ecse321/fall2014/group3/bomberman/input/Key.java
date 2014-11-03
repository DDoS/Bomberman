package ecse321.fall2014.group3.bomberman.input;

import org.lwjgl.input.Keyboard;

public enum Key {
    UP(Keyboard.KEY_UP),
    DOWN(Keyboard.KEY_DOWN),
    LEFT(Keyboard.KEY_LEFT),
    RIGHT(Keyboard.KEY_RIGHT),
    SPACE(Keyboard.KEY_SPACE),
    PAUSE(Keyboard.KEY_P),
    CLOSE(Keyboard.KEY_ESCAPE);
    public static final int COUNT = values().length;
    private final int keyCode;

    private Key(int keyCode) {
        this.keyCode = keyCode;
    }

    int getKeyCode() {
        return keyCode;
    }

    boolean isDown() {
        return Keyboard.isKeyDown(keyCode);
    }
}
