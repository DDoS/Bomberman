package ecse321.fall2014.group3.bomberman.test.database;

import java.io.File;
import java.io.IOException;

import ecse321.fall2014.group3.bomberman.database.Database;
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
        final Session session = Session.create(testDB, username, password, realname);
        session.setScore(score);
        session.setLevel(level);

        // Retrieving values for test cases
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
    }


    @After
    public void closeDB() {
        testDB.disconnect();
    }
}
