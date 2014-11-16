package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class ButtonEntity extends TextBoxEntity {
    private static final SpriteInfo BUTTON_SPRITE = new SpriteInfo("ui", 224, Vector2f.ONE);

    public ButtonEntity(Vector2f position) {
        super(position);
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return BUTTON_SPRITE;
    }
}
