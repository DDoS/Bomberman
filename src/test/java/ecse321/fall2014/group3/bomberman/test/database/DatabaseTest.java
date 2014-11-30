package ecse321.fall2014.group3.bomberman.test.database;

import java.io.File;
import java.io.IOException;

import ecse321.fall2014.group3.bomberman.database.Database;
import ecse321.fall2014.group3.bomberman.database.Leaderboard;
import ecse321.fall2014.group3.bomberman.database.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Marco
 */
public class DatabaseTest {
    @Rule
    public final TemporaryFolder testFolder = new TemporaryFolder();
    private Database testDB;

    @Before
    public void openDB() throws IOException {
        final File tempFile = testFolder.newFile("test.db");
        testDB = new Database(tempFile.getAbsolutePath());
    }

    @Test
    public void testQueries() {
        // Inserting some accounts for test cases
        String username = "test";
        String realname = "test real name";
        String password = "pass";
        int score = 10;
        int level = 2;

        //Create Account Unit Test
        final Session session = Session.create(testDB, username, password, realname);
        session.setScore(score);
        session.setLevel(level);

        //Database Unit Tests
        String dbUsername = session.getUserName();
        String dbRealname = session.getRealName();
        String dbPassword = session.getPassword();
        int dbScore = session.getScore();
        int dbLevel = session.getLevel();

        // Test when it should equal true
        Assert.assertEquals(username, dbUsername);
        Assert.assertEquals(realname, dbRealname);
        Assert.assertEquals(password, dbPassword);
        Assert.assertEquals(score, dbScore);
        Assert.assertEquals(level, dbLevel);

        //Unit Tests for Statistics if values change
        score = 15000;
        level = 15;
        session.setLevel(level);
        session.setScore(score);
        dbScore = session.getScore();
        dbLevel = session.getLevel();

        Assert.assertEquals(score, dbScore);
        Assert.assertEquals(level, dbLevel);


    }

    //Leaderboard test
    @Test
    public void testLeaderboard(){

        Leaderboard leaderboard = new Leaderboard(testDB);
        String username = "test2";
        String realname = "test2realname";
        String password = "pass";

        //Creating a new session
        final Session session = Session.create(testDB, username, password, realname);

        //Testing Leaderboard Statistics
        int score = 15001;
        int level = 16;
        String dbUsername = session.getUserName();
        String dbRealname = session.getRealName();


        leaderboard.updateScore(dbUsername, score);
        leaderboard.updateLevel(dbUsername,level);

        int newScore = leaderboard.getScore(dbUsername);
        int newLevel = session.getLevel();

        Assert.assertEquals(score, newScore);
        Assert.assertEquals(level, newLevel);

        //Getting leader values
        Leaderboard.Leader[] leaderList = leaderboard.getTop(1);
        String leaderUsername = leaderList[0].username;
        String leaderRealname = leaderList[0].realname;
        int leaderScore = leaderList[0].score;

        //Checking for correctness

        Assert.assertEquals(dbUsername, leaderUsername);
        Assert.assertEquals(dbRealname, leaderRealname);
        Assert.assertEquals(newScore, leaderScore);
    }


    @After
    public void closeDB() {
        testDB.disconnect();
    }
}
