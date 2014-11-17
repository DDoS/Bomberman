package ecse321.fall2014.group3.bomberman.database;

/**
 * Created by marco on 16/11/14.
 */


import java.sql.*;

public class Session {

    private final Database DB;
    private String name;


    public Session(){

        DB = new Database();
        DB.connect();

    }

    public void create(String name){

            name = name;

    }

   public String getName(){

       String name = null;

       //TODO: Query to retrieve display name

       return name;
   }

    public String getDisplayName(){

        String username = null;

        //TODO: Query to get username

        return username;
    }

    public boolean setUserName(String username, String password){

           return DB.update(username, password);


    }

    public void setPassword(String password){

        //TODO: Query to change password


    }

    public String getPassword(String username){

       return DB.getPass(username);

    }

    public void disconnect() throws SQLException{

     DB.disconnect();

    }



}
