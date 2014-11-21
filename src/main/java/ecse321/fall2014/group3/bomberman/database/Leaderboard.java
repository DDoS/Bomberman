package ecse321.fall2014.group3.bomberman.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 * Created by Marco on 2014-11-21.
 */
public class Leaderboard {

    private final Database DB;
    private final Connection connection;

    public Leaderboard(Session session){

        this.DB= session.DB;
        this.connection = DB.connection;

    }

    public void updateScore(String username, int score){


        DB.setInt("USERS", username, "SCORE", score);

    }

    public void updateMap(String username, int level){


        DB.setInt("USERS", username, "LEVEL", level);

    }

    public int getScore(String username){

        return DB.getInt("USERS", username, "SCORE");
    }


    public String[] getTop(int num){

        String leaders [] = new String[num];

        PreparedStatement stmt = null;

        try {


            connection.setAutoCommit(false);

            //Check if username already exists
            String check = "SELECT * FROM USERS ORDER BY SCORE DESC;";

            stmt = connection.prepareStatement(check);

            ResultSet rs = stmt.executeQuery();


            int i = 0;

            while (rs.next() && i < leaders.length) {

                //For testing purposes
                System.out.println("Position " + (i + 1) + ": " + rs.getString("USERNAME"));
                leaders[i] = rs.getString("USERNAME");

                i++;
            }

           System.out.println(i);

        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return leaders;

    }

}
