/*

Login and Create Account Scripts


 */
package ecse321.fall2014.group3.bomberman.database;

import java.sql.*;

public class Login {

    public static boolean createAccount(String user, String password, String realName, Session session, Leaderboard leaderboard) {


        PreparedStatement stmt = null;

        System.out.println(realName);

        if (user.isEmpty() || password.isEmpty()) {

            System.out.println("Empty Fields");
            return false;
        }

        if (session.getName(user) == null) {


            session.setUserName(user);
            session.setPassword(user, password);
            session.setRealName(user, realName);
            leaderboard.updateScore(user, 0);
            leaderboard.updateMap(user, 1);


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

        if (session.getPassword(user) == null){
            return false;
        }

        if (session.getPassword(user).equals(password)){

            return true;
        }

        else {
            return false;
        }


    }

}
