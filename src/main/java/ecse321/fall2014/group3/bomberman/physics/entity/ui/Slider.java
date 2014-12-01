/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;

/**
 * The Class Slider.
 */
public class Slider extends Button {
    private final int min, range;
    private volatile int value;

    /**
     * Instantiates a new slider.
     *
     * @param position the position
     * @param size the size
     * @param text the text
     * @param action the action
     * @param minValue the min value
     * @param maxValue the max value
     */
    public Slider(Vector2f position, Vector2f size, String text, String action, int minValue, int maxValue) {
        super(position, size, text, action);
        min = minValue;
        range = maxValue - min + 1;
        value = minValue;
    }

    /**
     * Adds the.
     *
     * @param value the value
     */
    public void add(int value) {
        this.value = ((this.value - min + value) % range + range) % range + min;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String getText() {
        return String.format(super.getText(), value);
    }
}
