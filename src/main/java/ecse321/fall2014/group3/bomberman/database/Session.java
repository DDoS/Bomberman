package ecse321.fall2014.group3.bomberman.database;

/**
 * Created by marco on 16/11/14.
 */


import java.sql.*;

public class Session {

    protected final Database DB;
    private String name;


    public Session(){

        DB = new Database();
        DB.connect();

    }

    public void create(String name){

            name = name;
    }

   public String getName(String username){

       return DB.getString("USERS",username,"USERNAME");

   }

    public String getDisplayName(){

        String username = null;

        //TODO: Query to get username

        return username;
    }

    public void setUserName(String username){

           DB.setString("USERS",username,"USERNAME", username);

    }

    public void setPassword(String username, String password){

          DB.setString("USERS", username, "PASSWORD", password);
    }

    public void setRealName(String username, String realName){

        DB.setString("USERS", username, "REALNAME", realName);
    }

    public String getPassword(String username){

       return DB.getString("USERS",username, "PASSWORD");

    }

    public void disconnect() throws SQLException{

     DB.disconnect();

    }



}
