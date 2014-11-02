package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.math.vector.Vector2i;

/**
 *
 */
public class SpriteInfo {
    private final String sheetID;
    private final int spriteNumber;
    private final Vector2i spriteSize;

    public SpriteInfo(String sheetID, int spriteNumber, Vector2i spriteSize) {
        this.sheetID = sheetID;
        this.spriteNumber = spriteNumber;
        this.spriteSize = spriteSize;
    }

    public String getSheetID() {
        return sheetID;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public Vector2i getSpriteSize() {
        return spriteSize;
    }
}
