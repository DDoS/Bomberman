/*

Login and Create Account Scripts

Note: Included a simple command line menu for testing purposes

 */
package ecse321.fall2014.group3.bomberman.database;

import java.sql.*;

public class Login {
    public static boolean createAccount(String user, String password, Connection c) {

        //openDB();
        //Connection con = c;
        PreparedStatement stmt = null;

        if (user.isEmpty() || password.isEmpty()) {

            System.out.println("Empty Fields");
            return false;
        }

        try {

            //Class.forName("org.sqlite.JDBC");
            //  c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);


            String usr = null;

            usr = user;
            //Check if username already exists
            String check = "SELECT USERNAME FROM USERS WHERE USERNAME= ? ;";

            stmt = c.prepareStatement(check);
            stmt.setString(1, user);
           // ResultSet rs = stmt.executeQuery("SELECT USERNAME FROM USERS WHERE USERNAME='" + usr + "';");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                System.out.println("Username already exists");
                return false;
            }

            //System.out.println("Enter a Password");
            // String pass = scan.nextLine();

            String sql = "INSERT INTO USERS (USERNAME,PASSWORD) " +
                    " VALUES (?, ? )";

            stmt = c.prepareStatement(sql);

            stmt.setString(1, user);
            stmt.setString(2, password);

            stmt.executeUpdate();

            c.commit();
            stmt.close();
            c.setAutoCommit(true);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Create Account Success");
        return true;
    }

    public static boolean login(String user, String password, Connection c) {

        //openDB();
        //Connection c = null;
        PreparedStatement stmt = null;
        boolean good = false;

        try {

            //Class.forName("org.sqlite.JDBC");
            // c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            //  System.out.println("Opened DataBase Successfully");

            //stmt = c.createStatement();

            String que = "SELECT PASSWORD FROM USERS WHERE USERNAME= ? ;";
            String testPass = null;

            stmt = c.prepareStatement(que);
            stmt.setString(1, user);
            //GOOD QUERY
            //" SELECT * FROM USERS WHERE username='" + userName +"';"

            ResultSet rs = stmt.executeQuery();


            if (!rs.next()) {

                System.out.println("Username does not exist");
                good = false;
            } else {
                testPass = rs.getString("PASSWORD");

                if (testPass.equals(password)) {

                    System.out.println("Login Successful!");
                    good = true;
                } else {

                    System.out.println("Password did not match");
                    good = false;
                }
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        //System.out.println("Login Successful");
        return good;
    }

    public static Connection openDB() {

        Connection c = null;
        Statement stmt = null;

        try {

            //opening database test1
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
