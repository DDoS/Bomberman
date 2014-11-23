package ecse321.fall2014.group3.bomberman.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marco
 */
public class Leaderboard {
    private final Database database;

    public Leaderboard(Database database) {
        this.database = database;
    }

    public void updateScore(String username, int score) {
        database.setInt(username, Database.SCORE_KEY, score);
    }

    public void updateLevel(String username, int level) {
        database.setInt(username, Database.LEVEL_KEY, level);
    }

    public int getScore(String username) {
        return database.getInt(username, Database.SCORE_KEY);
    }

    public Leader[] getTop(int num) {
        final Leader leaders[] = new Leader[num];
        final Connection connection = database.getConnection();
        final String sql = "SELECT USERNAME, SCORE FROM USERS ORDER BY SCORE DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            final ResultSet rs = stmt.executeQuery();
            connection.commit();
            int i = 0;
            while (rs.next() && i < leaders.length) {
                leaders[i] = new Leader(rs.getString(Database.USERNAME_KEY), rs.getInt(Database.SCORE_KEY));
                i++;
            }
        } catch (SQLException exception) {
            System.err.println("Couldn't get top scores");
            exception.printStackTrace();
        }
        return leaders;
    }

    public static class Leader {
        public final String username;
        public final int score;

        public Leader(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getFormatted() {
            return username + ": " + score;
        }
    }
}
