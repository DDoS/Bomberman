/**
 * @author Phil Douyon
 */
package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;

import com.flowpowered.math.vector.Vector4f;

import org.spout.renderer.api.util.CausticUtil;

/**
 * The Class TextBox.
 */
public class TextBox extends UIBox {
    private volatile Vector4f textColor = CausticUtil.WHITE;
    private volatile String text;

    /**
     * Instantiates a new text box.
     *
     * @param position the position
     * @param size the size
     * @param text the text
     */
    public TextBox(Vector2f position, Vector2f size, String text) {
        super(position, size);
        this.text = text;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.TextTextured#getText()
     */
    @Override
    public String getText() {
        return text;
    }

    /* (non-Javadoc)
     * @see ecse321.fall2014.group3.bomberman.nterface.TextTextured#getTextColor()
     */
    @Override
    public Vector4f getTextColor() {
        return textColor;
    }

    /**
     * Sets the text color.
     *
     * @param textColor the new text color
     */
    public void setTextColor(Vector4f textColor) {
        this.textColor = textColor;
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }
}
