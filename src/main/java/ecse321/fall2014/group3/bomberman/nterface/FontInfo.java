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
