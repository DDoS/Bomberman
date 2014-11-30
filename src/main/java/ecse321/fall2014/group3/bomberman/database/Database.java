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
    public static final String LIVES_KEY = "LIVES";
    public static final String POWERUPS_KEY = "POWERUPS";
    private final Connection connection;

    public Database(String file) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException("Couldn't open database connection", exception);
        }
        verifyTable();
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.err.println("Couldn't close database connection");
            exception.printStackTrace();
        }
    }

    private void verifyTable() {
        try (Statement statement = connection.createStatement()) {
            final String sql = "CREATE TABLE IF NOT EXISTS USERS " +
                    "(USERNAME  TEXT  NOT NULL  PRIMARY KEY," +
                    " PASSWORD  TEXT  NOT NULL," +
                    " REALNAME  TEXT  NOT NULL," +
                    " SCORE     INT   NOT NULL," +
                    " LEVEL     INT   NOT NULL," +
                    " LIVES     INT   NOT NULL," +
                    " POWERUPS  INT   NOT NULL)";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException exception) {
            System.err.println("Couldn't verify user table");
            exception.printStackTrace();
        }
    }

    public void setString(String username, String column, String value) {
        if (column.equals(USERNAME_KEY)) {
            final String sql = "INSERT INTO USERS (USERNAME, PASSWORD, REALNAME, SCORE, LEVEL, LIVES, POWERUPS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, "");
                statement.setString(3, username);
                statement.setInt(4, 0);
                statement.setInt(5, 1);
                statement.setInt(6, 3);
                statement.setInt(7, 0);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException exception) {
                System.err.println("Couldn't create user row");
                exception.printStackTrace();
            }
        } else {
            final String sql = "UPDATE USERS SET " + column + " = ? WHERE USERNAME = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, value);
                statement.setString(2, username);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException exception) {
                System.err.println("Couldn't update user row");
                exception.printStackTrace();
            }
        }
    }

    public void setInt(String username, String column, int value) {
        final String sql = "UPDATE USERS SET " + column + " = ? WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, value);
            statement.setString(2, username);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException exception) {
            System.err.println("Couldn't update user row");
            exception.printStackTrace();
        }
    }

    public String getString(String username, String column) {
        final String sql = "SELECT " + column + " FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();
            connection.commit();
            if (!rs.next()) {
                return null;
            }
            return rs.getString(column);
        } catch (SQLException exception) {
            System.err.println("Couldn't get user column");
            exception.printStackTrace();
        }
        return null;
    }

    public int getInt(String username, String column) {
        final String sql = "SELECT " + column + " FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            final ResultSet rs = statement.executeQuery();
            connection.commit();
            if (!rs.next()) {
                return -1;
            }
            return rs.getInt(column);
        } catch (SQLException exception) {
            System.err.println("Couldn't get user column");
            exception.printStackTrace();
        }
        return -1;
    }
}
