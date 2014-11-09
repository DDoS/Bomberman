package ecse321.fall2014.group3.bomberman;

import org.spout.renderer.lwjgl.LWJGLUtil;

import ecse321.fall2014.group3.bomberman.database.Login;

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

        final JTextField passwordText = new JTextField(10);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        panel.add(loginButton);

        JButton createButton = new JButton("New");
        panel.add(createButton);

        final JLabel error = new JLabel();
        error.setForeground(Color.red);
        error.setVisible(false);
        panel.add(error);

        error.setText("wrong username or password");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Login.login(userText.getText(), passwordText.getText())) {
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
            public void actionPerformed(ActionEvent e) {
                if (Login.createAccount(userText.getText(), passwordText.getText())) {

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
