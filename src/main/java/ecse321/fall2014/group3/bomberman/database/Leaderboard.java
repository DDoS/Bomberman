package ecse321.fall2014.group3.bomberman.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

        PreparedStatement stmt;

        final Connection connection = database.getConnection();

        try {

            connection.setAutoCommit(false);

            //Check if username already exists
            String check = "SELECT * FROM USERS ORDER BY SCORE DESC;";

            stmt = connection.prepareStatement(check);

            ResultSet rs = stmt.executeQuery();

            int i = 0;

            while (rs.next() && i < leaders.length) {
                leaders[i] = rs.getString(Database.USERNAME_KEY);
                i++;
            }

            System.out.println(i);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return leaders;
    }
}
