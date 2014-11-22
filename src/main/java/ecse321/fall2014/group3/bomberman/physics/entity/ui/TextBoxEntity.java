package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector4f;

import org.spout.renderer.api.util.CausticUtil;

public class TextBoxEntity extends UIBoxEntity {
    private volatile Vector4f textColor = CausticUtil.BLUE;
    private volatile String text;

    public TextBoxEntity(Vector2f position, Vector2f size, String text) {
        super(position, size);
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Vector4f getTextColor() {
        return textColor;
    }

    public void setTextColor(Vector4f textColor) {
        this.textColor = textColor;
    }

    public void setText(String text) {
        this.text = text;
    }
}
