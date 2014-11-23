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

    public String[] getTop(int num) {
        final String leaders[] = new String[num];
        final Connection connection = database.getConnection();
        final String sql = "SELECT USERNAME FROM USERS ORDER BY SCORE DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            final ResultSet rs = stmt.executeQuery();
            connection.commit();
            int i = 0;
            while (rs.next() && i < leaders.length) {
                leaders[i] = rs.getString(Database.USERNAME_KEY);
                i++;
            }
        } catch (SQLException exception) {
            System.err.println("Couldn't get top scores");
            exception.printStackTrace();
        }
        return leaders;
    }
}
