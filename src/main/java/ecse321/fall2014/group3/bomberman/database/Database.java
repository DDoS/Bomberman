/*
 * This file is part of Bomberman, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Group 3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ecse321.fall2014.group3.bomberman.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The main class for the game database. The methods in the class manage the table creation and also the updating of table fields.
 * More precisely, this class creates the table containing all information needed for account management and gameplay. It also manages the connection information needed
 * to update the table fields.
 * All methods for retrieving and updating values in the table are contained in this class
 *
 * @author Marco Manglaviti
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

    /**
     * Constructs a Database Object
     *
     * @param file The local database file
     */
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

    /**
     * Returns the connection information for use with the database
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.err.println("Couldn't close database connection");
            exception.printStackTrace();
        }
    }

    //Check if the table exists, and creates a new table if it does not exist
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

    /**
     * Sets a String value in the Users table in the target column.
     *
     * @param username Player Username
     * @param column Target Column
     * @param value Value To Be Updated
     */
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

    /**
     * Sets an integer value in the Users table.
     *
     * @param username Player Username
     * @param column Target Column
     * @param value Value To Be Updated
     */
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

    /**
     * Returns a String value from the Users table that was requested
     *
     * @param username Player Username
     * @param column Target Column
     * @return  Requested String
     */
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
    /**
     * Returns an Integer value from the Users table that was requested
     *
     * @param username Player Username
     * @param column Target Column
     * @return  Requested Integer
     */
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
