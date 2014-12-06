/*
 * This file is part of Bomberman, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Group 3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * @author Group 3
 */
package ecse321.fall2014.group3.bomberman;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ecse321.fall2014.group3.bomberman.database.Database;
import ecse321.fall2014.group3.bomberman.database.Leaderboard;
import ecse321.fall2014.group3.bomberman.database.Session;

import org.spout.renderer.lwjgl.LWJGLUtil;

/**
 * The Class App.
 */
public class App {
    private static final Semaphore loginWait = new Semaphore(0);
    private static volatile JFrame launcherScreen;
    private static volatile Database database;
    private static volatile Session session;

    //1 Digit, 1 Upper, 1 Lower, 1 Special, Minimum length of 8
    private static final Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$&%!^*()-_=+]).{8,})");

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        LWJGLUtil.deployNatives(null);
        database = new Database("accounts.db");

        createLoginScreen();
        loginWait.acquireUninterruptibly();
        launcherScreen.dispose();
        new Game(session, new Leaderboard(database)).open();

        database.disconnect();
    }

    //login screen
    private static void createLoginScreen() {

        final JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(20, 4));
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
        panel.getRootPane().setDefaultButton(loginButton);

        panel.add(new JLabel());

        JLabel newLabel = new JLabel("NEW USER");
        panel.add(newLabel);

        panel.add(new JLabel());

        JLabel realNameLabel = new JLabel("Real Name");
        panel.add(realNameLabel);

        final JTextField realNameText = new JTextField(10);
        panel.add(realNameText);

        JLabel newUserLabel = new JLabel("User");
        panel.add(newUserLabel);

        final JTextField newUserText = new JTextField(10);
        panel.add(newUserText);

        JLabel newPasswordLabel = new JLabel("Password");
        panel.add(newPasswordLabel);

        final JPasswordField newPasswordText = new JPasswordField(10);
        panel.add(newPasswordText);

        JLabel verifyLabel = new JLabel("Verify Password");
        panel.add(verifyLabel);

        final JPasswordField verifyText = new JPasswordField(10);
        panel.add(verifyText);

        JButton createButton = new JButton("New");
        panel.add(createButton);

        final JLabel error = new JLabel("User Already Exists");
        error.setForeground(Color.RED);
        error.setVisible(false);
        panel.add(error);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((session = Session.open(database, userText.getText(), String.valueOf(passwordText.getPassword()))) != null) {
                    loginWait.release();
                } else {
                    passwordText.setText("");
                    verifyText.setText("");
                    error.setText("Wrong Username or Password");
                    error.setVisible(true);
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validatePassword(String.valueOf(newPasswordText.getPassword()))) {
                    newPasswordText.setText("");
                    verifyText.setText("");
                    error.setText("Invalid Password!");
                    error.setVisible(true);
                } else if (!Arrays.equals(newPasswordText.getPassword(), verifyText.getPassword())) {
                    newPasswordText.setText("");
                    verifyText.setText("");
                    error.setText("Passwords Don't Match!");
                    error.setVisible(true);
                } else {
                    if ((session = Session.create(database, newUserText.getText(), String.valueOf(newPasswordText.getPassword()), realNameText.getText())) != null) {
                        loginWait.release();
                    } else {
                        userText.setText("");
                        passwordText.setText("");
                        verifyText.setText("");
                        error.setText("User Already Exists");
                        error.setVisible(true);
                    }
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        launcherScreen = frame;
    }

    /**
     * Validate password. The password must follow the following structure:
     * Minimum length: 8 Must contain at least: 1 upper case 1 lower case 1
     * number 1 special character
     *
     * @param pass the pass
     * @return true, if successful
     */
    public static boolean validatePassword(String pass) {
        Matcher match = pattern.matcher(pass);
        return match.matches();
    }
}
