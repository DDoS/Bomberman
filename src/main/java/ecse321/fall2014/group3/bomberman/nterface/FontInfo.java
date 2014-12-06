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

/**
 * An immutable class to store information about a font required by the renderer, which includes the name and the point size.
 */
public class FontInfo {
    private final String name;
    private final int size;
    private final String typeString;

    /**
     * Constructs a new font info from the name of the font and the point size.
     *
     * @param name The name of the font
     * @param size The point size of the font
     */
    public FontInfo(String name, int size) {
        this.name = name;
        this.size = size;
        typeString = name + " " + size;
    }

    /**
     * Returns the name of the font.
     *
     * @return The font name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the font size in points.
     *
     * @return The points size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the font type string, formatted to include the name and size and to be recognizable by {@link java.awt.Font#decode(String)}.
     *
     * @return A formatted string including all the font information
     */
    public String getTypeString() {
        return typeString;
    }
}
