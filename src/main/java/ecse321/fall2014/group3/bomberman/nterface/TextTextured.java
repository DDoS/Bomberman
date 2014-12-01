package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.math.vector.Vector4f;

/**
 * Represents an element that is textured by text.
 */
public interface TextTextured extends Textured {
    /**
     * Returns the font information for the text
     *
     * @return The font info
     */
    FontInfo getFontInfo();

    /**
     * Returns the actual text.
     *
     * @return The text
     */
    String getText();

    /**
     * Returns the color of the text, in normalized r, g, b, a [0, 1].
     *
     * @return The text color
     */
    Vector4f getTextColor();
}
