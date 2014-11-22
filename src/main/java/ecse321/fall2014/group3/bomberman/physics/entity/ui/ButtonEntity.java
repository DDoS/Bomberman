package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector4f;

import org.spout.renderer.api.util.CausticUtil;

public class ButtonEntity extends TextBoxEntity {
    private final String[] action;
    private volatile boolean selected = false;
    private volatile Vector4f selectedColor = CausticUtil.GREEN;

    public ButtonEntity(Vector2f position, Vector2f size, String text, String action) {
        super(position, size, text);
        this.action = action.split("\\.");
        if (this.action.length != 2) {
            throw new IllegalArgumentException("Expected two dot separated parts in action string: target and action");
        }
    }

    public String[] getAction() {
        return action;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setSelectedColor(Vector4f selectedColor) {
        this.selectedColor = selectedColor;
    }

    @Override
    public Vector4f getTextColor() {
        if (selected) {
            return selectedColor;
        }
        return super.getTextColor();
    }
}
