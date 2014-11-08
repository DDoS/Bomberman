package ecse321.fall2014.group3.bomberman.physics.entity;

import com.flowpowered.math.vector.Vector2f;

import ecse321.fall2014.group3.bomberman.nterface.SpriteInfo;

public class Button extends Entity {

    //TODO: create sprite sheet for button
    private static final SpriteInfo Button_SPRITE = new SpriteInfo("main", 224, Vector2f.ONE);
    private volatile SpriteInfo currentSprite = Button_SPRITE;
    private volatile float speed = 0;
    String text = null;

    public Button(Vector2f position, String t) {
        super(position, COLLISION_BOX);
        this.text = t;
    }

    public String getText() {
        return text;
    }

    @Override
    public SpriteInfo getSpriteInfo() {
        return currentSprite;
    }
}
