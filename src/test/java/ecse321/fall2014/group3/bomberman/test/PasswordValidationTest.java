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
package ecse321.fall2014.group3.bomberman.test;

import ecse321.fall2014.group3.bomberman.App;
import org.junit.Assert;
import org.junit.Test;

public class PasswordValidationTest {

    @Test
    public void test() {

        //Tests that should be successful
        Assert.assertTrue(App.validatePassword("abc123A@"));
        Assert.assertTrue(App.validatePassword("aaaaa1A@"));
        Assert.assertTrue(App.validatePassword("aA#12345"));
        Assert.assertTrue(App.validatePassword("a1@ABCDC"));
        Assert.assertTrue(App.validatePassword("a1A@#$%^&*("));
        Assert.assertTrue(App.validatePassword("a1A^^^()(^^"));

        //Tests that should fail
        Assert.assertFalse(App.validatePassword("abc123"));
        Assert.assertFalse(App.validatePassword(""));
        Assert.assertFalse(App.validatePassword("aA2@"));
        Assert.assertFalse(App.validatePassword("aaaaaaaa"));
        Assert.assertFalse(App.validatePassword("123456789"));
        Assert.assertFalse(App.validatePassword("ABCDCEDF"));
        Assert.assertFalse(App.validatePassword("!@#$%^&*()_"));

    }
}


