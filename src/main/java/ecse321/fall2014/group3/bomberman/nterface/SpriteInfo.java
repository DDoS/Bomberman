package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public class SpriteInfo {
    private final String sheetID;
    private final int spriteNumber;
    private final Vector2f spriteSize;

    public SpriteInfo(String sheetID, int spriteNumber, Vector2f spriteSize) {
        this.sheetID = sheetID;
        this.spriteNumber = spriteNumber;
        this.spriteSize = spriteSize;
    }

    public String getSheetName() {
        return sheetID;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public Vector2f getSpriteSize() {
        return spriteSize;
    }
}
