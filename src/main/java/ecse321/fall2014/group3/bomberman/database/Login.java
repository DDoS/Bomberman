/*

Login and Create Account Scripts


 */
package ecse321.fall2014.group3.bomberman.database;

import java.sql.*;

public class Login {

    public static boolean createAccount(String user, String password, Session session) {


        PreparedStatement stmt = null;

        System.out.println(user);

        if (user.isEmpty() || password.isEmpty()) {

            System.out.println("Empty Fields");
            return false;
        }

        if (session.getName(user) == null) {


            session.setUserName(user);
            System.out.println("Username is okay");
            session.setPassword(user, password);

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
