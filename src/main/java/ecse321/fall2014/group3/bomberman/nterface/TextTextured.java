package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.math.vector.Vector4f;

/**
 *
 */
public interface TextTextured extends Textured{
    FontInfo getFontInfo();

    String getText();

    Vector4f getTextColor();
}
