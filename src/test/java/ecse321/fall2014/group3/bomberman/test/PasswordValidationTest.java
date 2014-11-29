package ecse321.fall2014.group3.bomberman.test;

import ecse321.fall2014.group3.bomberman.App;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco
 */
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


