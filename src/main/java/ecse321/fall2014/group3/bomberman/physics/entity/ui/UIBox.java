/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.FontInfo;
import ecse321.fall2014.group3.bomberman.nterface.TextTextured;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;

/**
 * The Class UIBox.
 */
public abstract class UIBox extends Entity implements TextTextured {
    private static final FontInfo FONT = new FontInfo("Arial Bold", 16);

    /**
     * Instantiates a new UI box.
     *
     * @param position the position
     * @param size the size
     */
    public UIBox(Vector2f position, Vector2f size) {
        super(position, new CollisionBox(size));
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.Textured#getModelSize()
     */
    @Override
    public Vector2f getModelSize() {
        return collisionBox.getSize();
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.TextTextured#getFontInfo()
     */
    @Override
    public FontInfo getFontInfo() {
        return FONT;
    }
}
