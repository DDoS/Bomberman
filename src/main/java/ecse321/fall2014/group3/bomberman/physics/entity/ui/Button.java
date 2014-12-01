/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;

import com.flowpowered.math.vector.Vector4f;

import org.spout.renderer.api.util.CausticUtil;

/**
 * The Class Button.
 */
public class Button extends TextBox {
    private final String[] action;
    private volatile boolean selected = false;
    private volatile Vector4f selectedColor = CausticUtil.YELLOW;

    /**
     * Instantiates a new button.
     *
     * @param position the position
     * @param size the size
     * @param text the text
     * @param action the action
     */
    public Button(Vector2f position, Vector2f size, String text, String action) {
        super(position, size, text);
        this.action = action.split("\\.");
        if (this.action.length != 2) {
            throw new IllegalArgumentException("Expected two dot separated parts in action string: target and action");
        }
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public String[] getAction() {
        return action;
    }

    /**
     * Checks if is selected.
     *
     * @return true, if is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected.
     *
     * @param selected the new selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Sets the selected color.
     *
     * @param selectedColor the new selected color
     */
    public void setSelectedColor(Vector4f selectedColor) {
        this.selectedColor = selectedColor;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.physics.entity.ui.TextBox#getTextColor()
     */
    @Override
    public Vector4f getTextColor() {
        if (selected) {
            return selectedColor;
        }
        return super.getTextColor();
    }
}
