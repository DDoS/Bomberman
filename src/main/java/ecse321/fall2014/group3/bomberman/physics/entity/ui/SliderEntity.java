package ecse321.fall2014.group3.bomberman.physics.entity.ui;

import com.flowpowered.math.vector.Vector2f;

/**
 *
 */
public class SliderEntity extends ButtonEntity {
    private final int min, range;
    private volatile int value;

    public SliderEntity(Vector2f position, Vector2f size, String text, String action, int minValue, int maxValue) {
        super(position, size, text, action);
        min = minValue;
        range = maxValue - min + 1;
        value = minValue;
    }

    public void add(int value) {
        this.value = ((this.value - min + value) % range + range) % range + min;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getText() {
        return String.format(super.getText(), value);
    }
}
