package ecse321.fall2014.group3.bomberman.test.database;

import ecse321.fall2014.group3.bomberman.database.Database;
import ecse321.fall2014.group3.bomberman.database.Session;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco
 */
public class DatabaseTest {
    Database testDB = new Database();

    @Test
    public void testQueries(){


        //Inserting some accounts for test cases
        Database testDB = new Database();
        String username = "test";
        String real = "test real name";
        String pass = "pass";
        Session session = Session.create(testDB, username, pass, real);

        //Retrieving values for test cases
        String user = testDB.getString("test", testDB.USERNAME_KEY);
        real = testDB.getString("test", testDB.REALNAME_KEY);
        pass = testDB.getString("test", testDB.PASSWORD_KEY);
        int score = testDB.getInt("test", testDB.SCORE_KEY);
        int level = testDB.getInt("test", testDB.LEVEL_KEY);


        //test when it should equal true
        Assert.assertEquals("test", user);
        Assert.assertEquals("test real name", real);
        Assert.assertEquals("test password", pass);
        Assert.assertEquals(score, 10);
        Assert.assertEquals(level, 2);


    }

    //Invalid SQL Queries
    @Test
    public void testSQL(){

        Assert.assertEquals(0,testDB.getInt("test", testDB.USERNAME_KEY));

    }
}
