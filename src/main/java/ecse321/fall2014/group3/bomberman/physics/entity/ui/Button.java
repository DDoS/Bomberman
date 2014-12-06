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

    @Override
    public Vector4f getTextColor() {
        if (selected) {
            return selectedColor;
        }
        return super.getTextColor();
    }
}
