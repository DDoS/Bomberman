package ecse321.fall2014.group3.bomberman.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Marco
 */
public class Database {
    public static final String USERNAME_KEY = "USERNAME";
    public static final String PASSWORD_KEY = "PASSWORD";
    public static final String REALNAME_KEY = "REALNAME";
    public static final String SCORE_KEY = "SCORE";
    public static final String LEVEL_KEY = "LEVEL";
    private final Connection connection;

    public Database() {

        Connection c = null;

        try {

            //opening database test
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:accounts.db");
            System.out.println("Check Database Successfully");

            c.setAutoCommit(false);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        connection = c;

        try {

            verifyTable("USERS");
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() throws SQLException {

        try {
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void verifyTable(String table) {

        Statement stmt;

        try {

            if (table.equals("USERS")) {

                stmt = connection.createStatement();

                String tbl = " CREATE TABLE IF NOT EXISTS USERS " +
                        "(USERNAME       TEXT   NOT NULL," +
                        " PASSWORD       TEXT   NOT NULL," +
                        " REALNAME       TEXT   NOT NULL," +
                        " SCORE          INT    NOT NULL," +
                        " LEVEL          INT    NOT NULL)";

                stmt.executeUpdate(tbl);

                System.out.println("Checked USER Table Successfully");
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void setString(String username, String column, String value) {

        PreparedStatement stmt;

        try {

            if (column.equals("USERNAME")) {
                connection.setAutoCommit(false);

                //that username does not exist

                //create the username
                String sql = "INSERT INTO USERS (USERNAME, PASSWORD, REALNAME, SCORE, LEVEL) " +
                        " VALUES (?,?,?,?,?)";

                stmt = connection.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, "");
                stmt.setString(3, username);
                stmt.setInt(4, 0);
                stmt.setInt(5, 1);

                stmt.executeUpdate();

                connection.commit();
                stmt.close();
                connection.setAutoCommit(true);

                System.out.println("Created Username");
                System.out.println(username);
            } else {

                connection.setAutoCommit(false);

                String sql = "UPDATE USERS SET " + column +
                        "=? WHERE USERNAME=?";

                stmt = connection.prepareStatement(sql);

                stmt.setString(1, value);
                stmt.setString(2, username);
                stmt.executeUpdate();

                connection.commit();
                stmt.close();
                connection.setAutoCommit(true);

                System.out.println("Created " + column);
                System.out.println(value);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void setInt(String username, String column, int value) {

        PreparedStatement stmt;

        try {

            connection.setAutoCommit(false);

            String sql = "UPDATE USERS SET " + column +
                    "=? WHERE USERNAME=?";

            stmt = connection.prepareStatement(sql);

            stmt.setInt(1, value);
            stmt.setString(2, username);
            stmt.executeUpdate();

            connection.commit();
            stmt.close();
            connection.setAutoCommit(true);

            System.out.println("Updated " + column);
            System.out.println(value);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public String getString(String username, String column) {

        PreparedStatement stmt;

        try {

            connection.setAutoCommit(false);

            //Check if username already exists
            String check = "SELECT " + column + " FROM USERS WHERE USERNAME= ? ;";

            stmt = connection.prepareStatement(check);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {

                System.out.println("No results found for " + column);
                return null;
            }

            return rs.getString(column);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return null;
    }

    public int getInt(String username, String column) {

        PreparedStatement stmt;

        try {

            connection.setAutoCommit(false);

            String check = "SELECT " + column + " FROM USERS WHERE USERNAME= ? ;";

            stmt = connection.prepareStatement(check);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {

                System.out.println("No results found for " + column);
                return -1;
            }

            return rs.getInt(column);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return -1;
    }
}
