package ecse321.fall2014.group3.bomberman.nterface;

/**
 * Represents an element that is textured using a sprite.
 */
public interface SpriteTextured extends Textured {
    /**
     * Returns the information for the sprite.
     *
     * @return The sprite info
     */
    SpriteInfo getSpriteInfo();
}
