package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector4f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class TextBoxEntity extends UIBoxEntity {
    private static final SpriteInfo TEXT_BOX_SPRITE = new SpriteInfo("ui", 224, Vector2f.ONE);

    public TextBoxEntity(Vector2f position) {
        super(position, COLLISION_BOX);
    }

    @Override
    public Vector4f getColor() {
        return Vector4f.ONE;
    }

    public String getText() {
        return "";
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return TEXT_BOX_SPRITE;
    }
}
