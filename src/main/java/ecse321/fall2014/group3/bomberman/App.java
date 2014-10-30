package ecse321.fall2014.group3.bomberman;

import org.spout.renderer.lwjgl.LWJGLUtil;

public class App {
    public static void main(String[] args) {
        LWJGLUtil.deployNatives(null);

        new Game().open();
    }
}
