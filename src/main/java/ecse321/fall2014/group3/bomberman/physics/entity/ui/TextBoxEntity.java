package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector4f;

import org.spout.renderer.api.util.CausticUtil;

public class TextBoxEntity extends UIBoxEntity {
    private volatile Vector4f foregroundColor = CausticUtil.WHITE;
    private volatile Vector4f backgroundColor = CausticUtil.BLACK;
    private volatile String text = "";

    public TextBoxEntity(Vector2f position, Vector2f size) {
        super(position, size);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Vector4f getForegroundColor() {
        return foregroundColor;
    }

    @Override
    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setForegroundColor(Vector4f foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setText(String text) {
        this.text = text;
    }
}
