package ecse321.fall2014.group3.bomberman.database;

/**
 * Created by mangia_hockey on 2014-11-17.
 */

import java.sql.*;


public class Database {

    private Connection connection;

    public Database(){

        //Empty Constructor

    }

    public void connect(){

        Connection c = null;
        Statement stmt = null;

        try {

            //opening database test
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Check Database Successfully");

            c.setAutoCommit(false);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        connection = c;

        try {

            verifyTableUsers();

        }catch (Exception e){

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }

    public void disconnect() throws SQLException{


            if (connection == null){

                return;
            }

            try {
                connection.close();
            }catch (Exception e) {

                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }


    }

    private void verifyTableUsers(){

        Statement stmt = null;

        try {


            stmt = connection.createStatement();

            String tbl = " CREATE TABLE IF NOT EXISTS USERS " +
                    "(USERNAME       TEXT   NOT NULL," +
                    " PASSWORD       TEXT   NOT NULL)";

            stmt.executeUpdate(tbl);
            //connection.commit();



            System.out.println("Checked Table Successfully");



        } catch (Exception e){


            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public boolean get(String username){

        PreparedStatement stmt = null;

        try {


            connection.setAutoCommit(false);


            //Check if username already exists
            String check = "SELECT USERNAME FROM USERS WHERE USERNAME= ? ;";

            stmt = connection.prepareStatement(check);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                System.out.println("Username already exists");
                return false;
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return true;

    }
    public String getPass(String username){

        String pass = null;

        PreparedStatement stmt = null;

        try {


            connection.setAutoCommit(false);


            String que = "SELECT PASSWORD FROM USERS WHERE USERNAME= ? ;";

            stmt = connection.prepareStatement(que);
            stmt.setString(1, username);
            //GOOD QUERY
            //" SELECT * FROM USERS WHERE username='" + userName +"';"

            ResultSet rs = stmt.executeQuery();


            if (!rs.next()) {

                System.out.println("Username does not exist");
                return null;

            } else {

                pass = rs.getString("PASSWORD");


            }

            rs.close();
            stmt.close();
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return pass;
    }


    public boolean update(String username, String password){

        PreparedStatement stmt = null;

        try {

            if (!get(username)){

                return false;
            }

            String sql = "INSERT INTO USERS (USERNAME, PASSWORD) " +
                    " VALUES (?,?)";

            stmt = connection.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            connection.commit();
            stmt.close();
            connection.setAutoCommit(true);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return true;


    }


}
