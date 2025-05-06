/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginRegisterForm;

import global.SessionManager;
import connection.DatabaseConnection;
import Main.StartingPage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author csld
 */
public class LoginFormOP {

    private JFrame frame;
    private JPanel titleBar;
    private JLabel minimizeLabel;
    private JLabel titleLabel;
    private JLabel closeLabel;
    private JPanel contentPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private JButton buttonRegister;

    //for session manager
    private String userEmail = null;

    //dragging the form
    private boolean isdragging = false;
    private Point mouseoffset;

    // db connection
    private DatabaseConnection dbConnection;
    

    public LoginFormOP() {
        //=== Frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 250);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        // Title Bar
        titleBar = new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(59, 76, 88));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));
        frame.add(titleBar, BorderLayout.NORTH);
        //=== Title Label
        titleLabel = new JLabel("Admin Login Form");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 200, 30);
        titleBar.add(titleLabel);
        //=== Close Label
        closeLabel = new JLabel("X");
        closeLabel.setForeground(Color.WHITE);
        closeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.setBounds(frame.getWidth() - 30, 0, 30, 30);

        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SessionManager.returnAfterLogin != null) {
                    SessionManager.returnAfterLogin.setVisible(true);
                    SessionManager.returnAfterLogin = null; // reset after use
                } else {
                    new StartingPage().setVisible(true); // fallback
                }
                frame.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(Color.BLACK);

            }
        });
        titleBar.add(closeLabel);
        //===
        minimizeLabel = new JLabel("-");
        minimizeLabel.setForeground(Color.WHITE);
        minimizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimizeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeLabel.setBounds(frame.getWidth() - 60, 0, 30, 30);

        minimizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minimizeLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeLabel.setForeground(Color.WHITE);
            }
        });
        titleBar.add(minimizeLabel);
        //=== Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(245, 243, 241));
        contentPanel.setBorder(new LineBorder(new Color(166, 127, 104), 0));
        contentPanel.setBounds(10, 30, frame.getWidth() - 10, frame.getHeight() - 40);
        frame.add(contentPanel);
        //=== username Label
        JLabel usernameLabel = new JLabel("Phone:");
        usernameLabel.setBounds(30, 40, 80, 25);
        contentPanel.add(usernameLabel);
        //=== UsernameField
        usernameField = new JTextField();
        usernameField.setBounds(120, 40, 200, 25);
        contentPanel.add(usernameField);
        //=== password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 80, 80, 25);
        contentPanel.add(passwordLabel);
        //=== UsernameField
        passwordField = new JPasswordField();
        passwordField.setBounds(120, 80, 200, 25);
        contentPanel.add(passwordField);
        //=== Button Login
        buttonLogin = new JButton("Login");
        buttonLogin.setBounds(100, 120, 100, 35);
        buttonLogin.setFont(new Font("Arial", Font.BOLD, 14));
        buttonLogin.setBackground(new Color(255, 102, 0));
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.setFocusPainted(false);
        buttonLogin.setBorderPainted(false);
        buttonLogin.setOpaque(true);
        buttonLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonLogin.addActionListener((e) -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if (checkLogin(username, password)) {
                frame.dispose();
                SessionManager.currentUserPhone = userEmail;
                if (SessionManager.redirectTargetPage != null) {
                    SessionManager.redirectTargetPage.run();
                    SessionManager.redirectTargetPage = null;
                } else if (SessionManager.returnAfterLogin != null) {
                    SessionManager.returnAfterLogin.setVisible(true);
                    SessionManager.returnAfterLogin = null;
                } else {
                    new StartingPage().setVisible(true); // fallback
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password", "Invalid Data", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonLogin.setBackground(new Color(255, 51, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonLogin.setBackground(new Color(255, 102, 0));
            }

        });
        contentPanel.add(buttonLogin);
        //=== Button Register
        buttonRegister = new JButton("Register");
        buttonRegister.setBounds(220, 120, 110, 35);
        buttonRegister.setFont(new Font("Arial", Font.BOLD, 14));
        buttonRegister.setBackground(new Color(0, 102, 255));
        buttonRegister.setForeground(Color.WHITE);
        buttonRegister.setFocusPainted(false);
        buttonRegister.setBorderPainted(false);
        buttonRegister.setOpaque(true);
        buttonRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonRegister.addActionListener((e) -> {
            frame.dispose();
            new RegisterFormOP();
        });

        buttonRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonRegister.setBackground(new Color(0, 51, 204));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonRegister.setBackground(new Color(0, 102, 255));
            }

        });
        contentPanel.add(buttonRegister);
        //=== Title Bar
        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isdragging = true;
                mouseoffset = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isdragging = false;
            }
        });
        titleBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isdragging) {
                    Point newLocation = e.getLocationOnScreen();
                    newLocation.translate(-mouseoffset.x, -mouseoffset.y);
                    frame.setLocation(newLocation);
                }
            }
        });

        //=== Init
        dbConnection = new DatabaseConnection();
        frame.setVisible(true);
    }

    //check username and passowrd
    private boolean checkLogin(String Phone, String password) {
        Connection connection = dbConnection.getConnection();
        if (connection != null) {
            try {
                String query = "SELECT * FROM `Operator` WHERE `phone` = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, Phone);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    userEmail = rs.getString("email");
                    return password.equals(storedPassword);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new LoginFormOP();
    }

}
