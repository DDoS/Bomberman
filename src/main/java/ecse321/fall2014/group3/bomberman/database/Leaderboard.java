package ecse321.fall2014.group3.bomberman.database;

/**
 * Created by mangia_hockey on 2014-11-21.
 */
public class Leaderboard {

    private final Database DB;

    public Leaderboard(Session session){

        this.DB= session.DB;

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


    public void getTop(int num){

           //TODO: working on this now


    }
}
