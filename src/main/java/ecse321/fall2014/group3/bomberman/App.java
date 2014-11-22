package ecse321.fall2014.group3.bomberman;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import ecse321.fall2014.group3.bomberman.database.Leaderboard;
import ecse321.fall2014.group3.bomberman.database.Login;
import ecse321.fall2014.group3.bomberman.database.Session;

import org.spout.renderer.lwjgl.LWJGLUtil;

public class App {
    private static final Semaphore loginWait = new Semaphore(0);

    public static void main(String[] args) {
        LWJGLUtil.deployNatives(null);

        final Session session = new Session();
        final Leaderboard leaderboard = new Leaderboard(session);
        final JFrame loginFrame = createLoginScreen(session, leaderboard);

        loginWait.acquireUninterruptibly();

        loginFrame.dispose();

        final JFrame menuFrame = createMenu(session, leaderboard);
        loginWait.acquireUninterruptibly();
        menuFrame.dispose();

        try {
            session.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new Game().open();
    }

    private static JFrame createLoginScreen(final Session session, final Leaderboard leaderboard) {

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
                    leaderboard.updateScore(userText.getText(), (leaderboard.getScore(userText.getText()) + 10));

                    //For testing purposes
                    leaderboard.getTop(3);

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
                if (!Arrays.equals(newPasswordText.getPassword(), verifyText.getPassword())) {
                    newPasswordText.setText("");
                    verifyText.setText("");
                    error.setText("passwords dont match");
                    error.setVisible(true);
                } else {
                    //TODO: add real name to database
                    //if(Login.login(realNameText.getText(),newUserText.getText(), String.valueOf(newPasswordText.getPassword()), connection))
                    if (Login.createAccount(newUserText.getText(), String.valueOf(newPasswordText.getPassword()), String.valueOf(realNameText.getText()), session, leaderboard)) {
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

        frame.setSize(300, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    private static JFrame createMenu(final Session session, final Leaderboard leaderboard) {
        JFrame frame = new JFrame("Bomberman");
        frame.setSize(300, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel mainPanel = new JPanel();
        JPanel leaderboardPanel = new JPanel(new GridLayout(0, 2));
        JPanel mainMenu = new JPanel(new GridLayout(10, 2));

        final CardLayout card = new CardLayout();
        mainPanel.setLayout(card);
        mainPanel.add(mainMenu, "main");
        mainPanel.add(leaderboardPanel, "leaderboard");

        //create leaderboard panel

        leaderboardPanel.setBackground(Color.LIGHT_GRAY);

        JLabel userLabel = new JLabel("User");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardPanel.add(userLabel);

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardPanel.add(scoreLabel);

        JLabel userName[] = new JLabel[10];
        JLabel userScore[] = new JLabel[10];

        String names[] = leaderboard.getTop(10);
        int scores[] = new int[10];

        for (int i = 0; i < 10; i++) {
            userName[i] = new JLabel(names[i]);
            userName[i].setHorizontalAlignment(SwingConstants.CENTER);
            userName[i].setBorder(LineBorder.createBlackLineBorder());
            leaderboardPanel.add(userName[i]);

            if (names[i] != null) {
                userScore[i] = new JLabel(String.valueOf(leaderboard.getScore(names[i])));
            } else {
                userScore[i] = new JLabel("");
            }
            userScore[i].setHorizontalAlignment(SwingConstants.CENTER);
            userScore[i].setBorder(LineBorder.createBlackLineBorder());
            leaderboardPanel.add(userScore[i]);
        }

        JButton returnButton = new JButton("Return");
        leaderboardPanel.add(returnButton);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, "main");
            }
        });

        //create main menu panel
        mainMenu.setBackground(Color.LIGHT_GRAY);

        JLabel bomberman = new JLabel("Bomberman");
        bomberman.setHorizontalAlignment(SwingConstants.CENTER);
        bomberman.setForeground(Color.black);
        bomberman.setFont(new Font("Serif", Font.BOLD, 20));
        mainMenu.add(bomberman);
        mainMenu.add(new JLabel());

        JButton continueButton = new JButton("Continue");
        mainMenu.add(continueButton);
        mainMenu.add(new JLabel());

        JButton newGameButton = new JButton("New Game");
        mainMenu.add(newGameButton);

        //TODO: get max level
        int maxLevel = 100;
        SpinnerModel model = new SpinnerNumberModel(maxLevel, 0, maxLevel, 1);
        JSpinner levelSelect = new JSpinner(model);
        mainMenu.add(levelSelect);

        mainMenu.add(new JLabel());

        JButton leaderboardButton = new JButton("LeaderBoard");
        mainMenu.add(leaderboardButton);
        mainMenu.add(new JLabel());

        leaderboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, "leaderboard");
            }
        });

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: load previous session
                //TODO: keep same size for game and menu
                loginWait.release();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: load game at level
                //int value = (Integer) levelSelect.getValue();
                loginWait.release();
            }
        });

        card.show(mainPanel, "Main");
        frame.add(mainPanel);
        frame.setVisible(true);
        return frame;
    }
}
