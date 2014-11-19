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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import ecse321.fall2014.group3.bomberman.database.Login;
import ecse321.fall2014.group3.bomberman.database.Session;

import org.spout.renderer.lwjgl.LWJGLUtil;

public class App {
    private static final Semaphore loginWait = new Semaphore(0);

    public static void main(String[] args) {
        LWJGLUtil.deployNatives(null);

        final Session session = new Session();
        final JFrame frame = createLoginScreen(session);

        loginWait.acquireUninterruptibly();

        frame.dispose();
        try {
            session.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new Game().open();
    }

    private static JFrame createLoginScreen(final Session session) {

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

        final JLabel error = new JLabel("user already exists or missing password");
        error.setForeground(Color.RED);
        error.setVisible(false);
        panel.add(error);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (Login.login(userText.getText(), String.valueOf(passwordText.getPassword()), session)) {
                        loginWait.release();
                        session.create(userText.getText());

                    } else {
                        passwordText.setText("");
                        verifyText.setText("");
                        error.setText("wrong username or password");
                        error.setVisible(true);
                    }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Arrays.equals(passwordText.getPassword(), verifyText.getPassword())) {
                    passwordText.setText("");
                    verifyText.setText("");
                    error.setText("passwords dont match");
                    error.setVisible(true);
                } else {
                    //TODO: add real name to database
                    //if(Login.login(realNameText.getText(),newUserText.getText(), String.valueOf(newPasswordText.getPassword()), connection))
                    if (Login.createAccount(userText.getText(), String.valueOf(passwordText.getPassword()), session)) {
                        session.create(userText.getText());
                        loginWait.release();
                    } else {
                        realNameText.setText("");
                        userText.setText("");
                        passwordText.setText("");
                        verifyText.setText("");
                        error.setText("user already exist");
                        error.setVisible(true);
                    }
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
