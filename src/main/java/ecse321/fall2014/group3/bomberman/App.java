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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

import ecse321.fall2014.group3.bomberman.database.Login;

import org.spout.renderer.lwjgl.LWJGLUtil;

public class App {
    private static final Semaphore loginWait = new Semaphore(0);

    public static void main(String[] args) {
        LWJGLUtil.deployNatives(null);

        final Connection connection = Login.openDB();
        final JFrame frame = createLoginScreen(connection);

        loginWait.acquireUninterruptibly();

        frame.dispose();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new Game().open();
    }

    private static JFrame createLoginScreen(final Connection connection) {

        final JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        final JLabel error = new JLabel("user already exists or missing password");
        error.setForeground(Color.RED);
        error.setVisible(false);
        panel.add(error);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Login.login(userText.getText(), String.valueOf(passwordText.getPassword()), connection)) {
                    loginWait.release();
                } else {
                    passwordText.setText("");
                    error.setText("wrong username or password");
                    error.setVisible(true);
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Login.createAccount(userText.getText(), String.valueOf(passwordText.getPassword()), connection)) {
                    loginWait.release();
                } else {
                    userText.setText("");
                    passwordText.setText("");
                    error.setText("user already exists or missing password");
                    error.setVisible(true);
                }
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
