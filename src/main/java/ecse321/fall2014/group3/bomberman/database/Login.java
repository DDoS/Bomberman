/*

Login and Create Account Scripts

Note: Included a simple command line menu for testing purposes

 */

package ecse321.fall2014.group3.bomberman.database;

import java.sql.*;
import java.util.Scanner;

public class Login {

    public static void menu() {

        Connection c = null;
        Statement stmt = null;

        Scanner scan = new Scanner(System.in);

        try {

            //opening database test
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened Database Successfully");

            c.setAutoCommit(false);
            stmt = c.createStatement();

            String tbl = " CREATE TABLE IF NOT EXISTS USERS " +
                    "(USERNAME       TEXT   NOT NULL," +
                    " PASSWORD       TEXT   NOT NULL)";

            stmt.executeUpdate(tbl);
            c.commit();
            c.close();

            //Menu

            System.out.println("Enter an option: ");
            System.out.println("1. Create an Account. ");
            System.out.println("2. Login");

            int choice = scan.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    login();
                    break;

                default:
                    System.out.println("Invalid Choice");
                    System.exit(0);

            }

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Process Complete");
    }

    public static void createAccount() {

        Connection c = null;
        Statement stmt = null;
        Scanner scan = new Scanner(System.in);

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            boolean goodUser = false;
            String usr = null;

            while (true) {
                System.out.println("Enter a Username");
                usr = scan.nextLine();

                //Check if username already exists

                ResultSet rs = stmt.executeQuery(" SELECT username FROM USERS WHERE username='" + usr + "';");

                if (rs.next()) {

                    System.out.println("Username already exists :(");

                } else {

                    break;
                }

            }

            System.out.println("Enter a Password");
            String pass = scan.nextLine();

            String sql = "INSERT INTO USERS (USERNAME,PASSWORD) " +
                    " VALUES ('" + usr + "', '" + pass + "' );";

            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.setAutoCommit(true);
            c.close();

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }

        System.out.println("Create Account Success");

    }

    public static void login() {

        Connection c = null;
        Statement stmt = null;
        Scanner scan = new Scanner(System.in);

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened DataBase Successfully");

            //user attempts to login
            System.out.println("Enter Username: ");
            String userName = scan.nextLine();

            System.out.println("Enter Password: ");
            String userPass = scan.nextLine();

            stmt = c.createStatement();

            String que = "SELECT * FROM USERS WHERE EXISTS (SELECT * FROM USERS WHERE username='" + userName + "');";
            String testPass = null;

            //GOOD QUERY
            //" SELECT * FROM USERS WHERE username='" + userName +"';"

            ResultSet rs = stmt.executeQuery(que);
            if (!rs.next()) {

                System.out.println("Username does not exist");
                System.exit(0);
            }

            boolean good = false;

            while (rs.next()) {

                testPass = rs.getString("password");

                if (testPass.equals(userPass)) {

                    System.out.println("Login Successful");
                    good = true;

                    break;

                }

            }

            if (!good) {
                System.out.println("Login Unsuccessful");
            }
            rs.close();
            stmt.close();
            c.close();

            System.exit(0);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);

        }

    }

}
