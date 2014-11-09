package ecse321.fall2014.group3.bomberman;

import org.spout.renderer.lwjgl.LWJGLUtil;

import ecse321.fall2014.group3.bomberman.database.Login;
import sun.rmi.runtime.Log;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class App {
    public static void main(String[] args) {
        LWJGLUtil.deployNatives(null);

        createLoginScreen();

        //new Game().open();
    }

    private static void createLoginScreen() {

        final JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 4));
        frame.add(panel, BorderLayout.CENTER);

        JLabel userLabel = new JLabel("User");
        panel.add(userLabel);

        final JTextField userText = new JTextField(10);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        panel.add(passwordLabel);

        final JPasswordField passwordText = new JPasswordField(10);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        panel.add(loginButton);

        JButton createButton = new JButton("New");
        panel.add(createButton);

        final JLabel error = new JLabel("enter a new username and password");
        error.setForeground(Color.red);
        error.setVisible(false);
        panel.add(error);

        
        loginButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                if (Login.login(userText.getText(), passwordText.getText(), Login.openDB())) {
                    frame.dispose();
                    new Game().open();

                } else {
                    userText.setText("");
                    passwordText.setText("");
                    error.setText("wrong username or password");
                    error.setVisible(true);

                }

            }
        });

        createButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {
                if (Login.createAccount(userText.getText(), passwordText.getText(),Login.openDB())) {

                    frame.dispose();
                    new Game().open();

                } else {
                    userText.setText("");
                    passwordText.setText("");
                    error.setText("enter a new username and password");
                    error.setVisible(true);

                }

            }
        });

        frame.pack();
        frame.setVisible(true);

    }
}
