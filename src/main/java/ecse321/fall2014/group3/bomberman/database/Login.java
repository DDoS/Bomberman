/*

Login and Create Account Scripts


 */
package ecse321.fall2014.group3.bomberman.database;

import java.sql.*;

public class Login {
    public static boolean createAccount(String user, String password, Session session) {


        PreparedStatement stmt = null;

        if (user.isEmpty() || password.isEmpty()) {

            System.out.println("Empty Fields");
            return false;
        }

        if (session.setUserName(user, password)) {

            System.out.println("Username is okay");
            //session.setPassword(password);
            System.out.println("Create Account Success");
            return true;

        } else {

            System.out.println("Account creation FAILED");
            return false;
        }
    }

    public static boolean login(String user, String password, Session session) {


        PreparedStatement stmt = null;
        boolean good = false;

        if (session.getPassword(user) ==null){
            return false;
        }

        if (session.getPassword(user).equals(password)){

            return true;
        }

        else {
            return false;
        }


    }

    public static Connection openDB() {

        Connection c = null;
        Statement stmt = null;

        try {

            //opening database test
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Check Database Successfully");

            c.setAutoCommit(false);
            stmt = c.createStatement();

            String tbl = " CREATE TABLE IF NOT EXISTS USERS " +
                    "(USERNAME       TEXT   NOT NULL," +
                    " PASSWORD       TEXT   NOT NULL)";

            stmt.executeUpdate(tbl);
            c.commit();
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return c;
    }
}
