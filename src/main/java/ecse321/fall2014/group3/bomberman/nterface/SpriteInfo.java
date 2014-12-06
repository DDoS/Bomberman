/*
 * This file is part of Bomberman, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Group 3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.math.vector.Vector2f;

/**
 * An immutable class that stores the information for rendering a sprite. This includes the ID of the sprite sheet (name of the file without the extension), the number of the sprite in the sheet
 * (continuous index from row to row) and the size of the sprite in the sheet (in units of 64 px).
 * <p/>
 * Sprite should be organized in a square sheet with a size that is a power of two and a multiple of 64 (px). Each sprite is 64x64 pixels. They are numbered in successive rows starting from the top
 * left corner, starting at 0.
 */
public class SpriteInfo {
    private final String sheetID;
    private final int spriteNumber;
    private final Vector2f spriteSize;

    /**
     * Constructs a new sprite sheet from the ID of the sprite sheet (name of the file without the extension), the number of the sprite in the sheet (continuous index from row to row) and the size of
     * the sprite in the sheet (in units of 64 px).
     *
     * @param sheetID The sheet ID
     * @param spriteNumber The sprite number (x + y * size)
     * @param spriteSize The size of the sprite
     */
    public SpriteInfo(String sheetID, int spriteNumber, Vector2f spriteSize) {
        this.sheetID = sheetID;
        this.spriteNumber = spriteNumber;
        this.spriteSize = spriteSize;
    }

    /**
     * Get the name of the sprite sheet.
     *
     * @return The sprite sheet name
     */
    public String getSheetName() {
        return sheetID;
    }

    /**
     * Gets the number of the sprite in the sheet.
     *
     * @return The sprite number
     */
    public int getSpriteNumber() {
        return spriteNumber;
    }

    /**
     * Returns the sprite size in units of 64 pixels, for the width (x) and height (y).
     *
     * @return The sprite size
     */
    public Vector2f getSpriteSize() {
        return spriteSize;
    }
}
