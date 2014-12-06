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

import java.util.Map;

import ecse321.fall2014.group3.bomberman.world.tile.powerup.PowerUP;

/**
 * This class is responsible for creating a session object for the player currently playing the game. The session is created at time of login and is unique to the
 * current player that has logged in. The class uses methods in {@link ecse321.fall2014.group3.bomberman.database.Database} to retrieve table field values needed for
 * the session creation
 */
public class Session {
    private final Database database;
    private final String username;

    /**
     * Constructs a Session Object unique to the current user
     * @param database The Database
     * @param username The Player Username
     */
    private Session(Database database, String username) {
        this.database = database;
        this.username = username;
    }

    /**
     * Returns a string representing the username of the current player logged in
     * @return Player Username
     */
    public String getUserName() {
        return username;
    }
    /**
     * Returns a string representing the real name of the current player logged in
     * @return Player Real Name
     */
    public String getRealName() {
        return database.getString(username, Database.REALNAME_KEY);
    }
    /**
     * Returns a string representing the password of the current player logged in
     * @return Player Password
     */
    public String getPassword() {
        return database.getString(username, Database.PASSWORD_KEY);
    }

    /**
     * Sets the password for the current user in the database
     * @param password User Password
     */
    public void setPassword(String password) {
        database.setString(username, Database.PASSWORD_KEY, password);
    }
    /**
     * Sets the real name for the current user in the database
     * @param realName User Real Name
     */
    public void setRealName(String realName) {
        database.setString(username, Database.REALNAME_KEY, realName);
    }

    /**
     * Returns the level unlocked by the user that is currently logged in
     * @return Level
     */
    public int getLevel() {
        return database.getInt(username, Database.LEVEL_KEY);
    }

    /**
     * Sets the level for the current user in the database
     * @param level
     */
    public void setLevel(int level) {
        database.setInt(username, Database.LEVEL_KEY, level);
    }

    /**
     * Returns the user's score for the user that is currently logged in
     * @return Score
     */
    public int getScore() {
        return database.getInt(username, Database.SCORE_KEY);
    }
    /**
     * Sets the score for the current user in the database
     * @param score
     */
    public void setScore(int score) {
        database.setInt(username, Database.SCORE_KEY, score);
    }
    /**
     * Returns the user's lives remaining for the user that is currently logged in
     * @return Number of Lives
     */
    public int getLives() {
        return database.getInt(username, Database.LIVES_KEY);
    }
    /**
     * Stores the score for the current user in the database
     * @param lives Number of User Lives
     */
    public void setLives(int lives) {
        database.setInt(username, Database.LIVES_KEY, lives);
    }

    /**
     * Returns the Powerups currently available for the user that is logged in.
     * @param powerUPs Powerups
     */
    public void getPowerUPs(Map<Class<? extends PowerUP>, Integer> powerUPs) {
        final int serialized = database.getInt(username, Database.POWERUPS_KEY);
        PowerUP.deserialize(serialized, powerUPs);
    }

    /**
     * Sets the powerups that are available to the current user
     * @param powerUPs Powerups
     */
    public void setPowerUPs(Map<Class<? extends PowerUP>, Integer> powerUPs) {
        final int serialized = PowerUP.serialize(powerUPs);
        database.setInt(username, Database.POWERUPS_KEY, serialized);
    }

    /**
     * Open a new session based on the username and password.
     * This method returns a new session
     * @param database The Database
     * @param username The Player Username
     * @param password The Player Password
     * @return New Session
     */
    public static Session open(Database database, String username, String password) {
        if (database.getString(username, Database.USERNAME_KEY) == null) {
            return null;
        }
        final String pass = database.getString(username, Database.PASSWORD_KEY);
        if (!password.equals(pass)) {
            return null;
        }
        return new Session(database, username);
    }

    /**
     * Creates a new row in the user table if the username is null
     *
     * @param database The Database
     * @param username The Player Username
     * @param password The Player Password
     * @param realname The Player Real Name
     * @return New Session
     */
    public static Session create(Database database, String username, String password, String realname) {
        if (database.getString(username, Database.USERNAME_KEY) != null) {
            return null;
        }
        database.setString(username, Database.USERNAME_KEY, username);
        database.setString(username, Database.PASSWORD_KEY, password);
        database.setString(username, Database.REALNAME_KEY, realname);
        database.setInt(username, Database.SCORE_KEY, 0);
        database.setInt(username, Database.LEVEL_KEY, 1);
        return new Session(database, username);
    }
}
