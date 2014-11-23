package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.FontInfo;
import ecse321.fall2014.group3.bomberman.nterface.TextTextured;
import ecse321.fall2014.group3.bomberman.physics.CollisionBox;
import ecse321.fall2014.group3.bomberman.physics.entity.Entity;

/**
 *
 */
public abstract class UIBoxEntity extends Entity implements TextTextured {
    private static final FontInfo FONT = new FontInfo("Arial Bold", 16);
    public UIBoxEntity(Vector2f position, Vector2f size) {
        super(position, new CollisionBox(size));
    }

    @Override
    public Vector2f getModelSize() {
        return collisionBox.getSize();
    }

    @Override
    public FontInfo getFontInfo() {
        return FONT;
    }
}
