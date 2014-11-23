package ecse321.fall2014.group3.bomberman.database;

/**
 * @author Marco
 */
public class Session {
    private final Database database;
    private final String username;

    private Session(Database database, String username) {
        this.database = database;
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setPassword(String password) {
        database.setString(username, Database.PASSWORD_KEY, password);
    }

    public void setRealName(String realName) {
        database.setString(username, Database.REALNAME_KEY, realName);
    }

    public String getPassword() {
        return database.getString(username, Database.PASSWORD_KEY);
    }

    public int getLevel() {
        return database.getInt(username, Database.LEVEL_KEY);
    }

    public int getScore() {
        return database.getInt(username, Database.SCORE_KEY);
    }

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
