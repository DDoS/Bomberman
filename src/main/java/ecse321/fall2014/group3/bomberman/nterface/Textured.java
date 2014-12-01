package ecse321.fall2014.group3.bomberman.nterface;

import com.flowpowered.math.vector.Vector2f;

/**
 * Represents an element that is textured. This is not meant to be used on its own, but as a super-interface to {@link ecse321.fall2014.group3.bomberman.nterface.SpriteTextured} and {@link
 * ecse321.fall2014.group3.bomberman.nterface.TextTextured}.
 */
public interface Textured {
    /**
     * Returns the model size, as a scale multiplier.
     *
     * @return The model size
     */
    Vector2f getModelSize();
}
