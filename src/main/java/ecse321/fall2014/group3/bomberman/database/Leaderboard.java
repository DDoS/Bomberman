package ecse321.fall2014.group3.bomberman.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class manages all the methods and actions related to the game leaderboard. More precisely, the class contains methods for managing user scores, levels and general
 * leaderboard generation
 *
 * The class uses methods in @link{ecse321.fall2014.group3.bomberman.database.Database} to store values pertaining to the leaderboard in the database. The methods that
 * return values also use methods from @link{ecse321.fall2014.group3.bomberman.database.Database}
 *
 * @author Marco
 */
public class Leaderboard {
    private final Database database;

    /**
     * Constructs a Leaderboard object
     *
     * @param database The Database
     */
    public Leaderboard(Database database) {
        this.database = database;
    }

    /**
     * Updates the user's level to the current level obtained in gameplay
     *
     * @param username The Player Username
     * @param score The Player Score
     */
    public void updateScore(String username, int score) {
        database.setInt(username, Database.SCORE_KEY, score);
    }
    /**
     * Updates the user's score to the current score obtained in gameplay
     *
     * @param username The Player Username
     * @param level The Player Level
     */
    public void updateLevel(String username, int level) {
        database.setInt(username, Database.LEVEL_KEY, level);
    }

    /**
     * Returns the most up-to-date score value for the specified user
     * @param username
     * @return The User's Score value
     */
    public int getScore(String username) {
        return database.getInt(username, Database.SCORE_KEY);
    }

    /**
     * Returns an array of Leaders in order to generate the leaderboard. Each element in the array corresponds to one Username in the Database
     * @param num Number of Players in the Leaderboard
     * @return Array of Leaders
     */
    public Leader[] getTop(int num) {
        final Leader leaders[] = new Leader[num];
        final Connection connection = database.getConnection();
        final String sql = "SELECT USERNAME, REALNAME, SCORE FROM USERS ORDER BY SCORE DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            final ResultSet rs = stmt.executeQuery();
            connection.commit();
            int i = 0;
            while (rs.next() && i < leaders.length) {
                leaders[i] = new Leader(rs.getString(Database.USERNAME_KEY), rs.getString(Database.REALNAME_KEY), rs.getInt(Database.SCORE_KEY));
                i++;
            }
        } catch (SQLException exception) {
            System.err.println("Couldn't get top scores");
            exception.printStackTrace();
        }
        return leaders;
    }

    /**
     * This static class is responsible for defining a leader with regards to the game users. A leader is simply a player stored in the database, and its
     * associated value fields
     */
    public static class Leader {
        public final String username, realname;
        public final int score;

        /**
         * Constructs a Leader object based on the player information
         *
         * @param username The Player Username
         * @param realname The Player Real Name
         * @param score The Player Score
         */
        public Leader(String username, String realname, int score) {
            this.username = username;
            this.realname = realname;
            this.score = score;
        }

        /**
         * Returns  A String with the leader information. For use in the leaderboard display
         * @return Formatted String
         */
        public String getFormatted() {
            return realname + "   \"" + username + "\"  :   " + score;
        }
    }
}
