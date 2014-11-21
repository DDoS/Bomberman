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

            verifyTable("USERS");

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



    private void verifyTable(String table){

        Statement stmt = null;

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
                //connection.commit();

                System.out.println("Checked USER Table Successfully");

            }
            //TODO: Implement Leaderboard Table

        } catch (Exception e){


            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void setString(String table, String username, String column, String value){

        PreparedStatement stmt = null;

        try {

            if (column.equals("USERNAME")) {
                connection.setAutoCommit(false);

                //that username does not exist

                //create the username
                String sql = "INSERT INTO " + table + " (USERNAME, PASSWORD, REALNAME, SCORE, LEVEL) " +
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

            }else {

                connection.setAutoCommit(false);

                String sql = "UPDATE " + table + " SET " + column  +
                        "=? WHERE USERNAME=?";

                stmt = connection.prepareStatement(sql);

                stmt.setString(1, value);
                stmt.setString(2, username);
                stmt.executeUpdate();

                connection.commit();
                stmt.close();
                connection.setAutoCommit(true);

                System.out.println("Created "+column);
                System.out.println(value);
            }
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }



    }

    public void setInt(String table, String username, String column, int value){


        PreparedStatement stmt = null;

        try {

                connection.setAutoCommit(false);

                String sql = "UPDATE " + table + " SET " + column  +
                        "=? WHERE USERNAME=?";

                stmt = connection.prepareStatement(sql);

                stmt.setInt(1, value);
                stmt.setString(2, username);
                stmt.executeUpdate();

                connection.commit();
                stmt.close();
                connection.setAutoCommit(true);

                System.out.println("Updated "+column);
                System.out.println(value);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String getString(String table, String username, String column){

        PreparedStatement stmt = null;

        try {


            connection.setAutoCommit(false);



            //Check if username already exists
            String check = "SELECT "+ column +" FROM "+ table +" WHERE USERNAME= ? ;";

            stmt = connection.prepareStatement(check);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()){

                System.out.println("No results found for "+column);
                return null;

            }

            return rs.getString(column);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;

    }

    public int getInt(String table, String username, String column){

        PreparedStatement stmt = null;

        try {


            connection.setAutoCommit(false);

            String check = "SELECT "+ column +" FROM "+ table +" WHERE USERNAME= ? ;";

            stmt = connection.prepareStatement(check);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()){

                System.out.println("No results found for "+column);
                return -1;

            }

            return rs.getInt(column);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return -1;

    }


}
