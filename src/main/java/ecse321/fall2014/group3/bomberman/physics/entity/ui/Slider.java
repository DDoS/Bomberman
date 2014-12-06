/*
 * This file is part of Bomberman, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Group 3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
