package ecse321.fall2014.group3.bomberman.nterface;

/**
 *
 */
public class FontInfo {
    private final String name;
    private final int size;
    private final String typeString;

    public FontInfo(String name, int size) {
        this.name = name;
        this.size = size;
        typeString = name + " " + size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getTypeString() {
        return typeString;
    }
}
