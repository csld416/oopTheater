/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginRegisterForm;

import Data.*;
import Main.StartingPage;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import javax.swing.JOptionPane;

/**
 *
 * @author csld
 */
public class LoginForm {

    private JFrame frame;
    private final JPanel titleBar;
    private final JLabel titleLabel;
    private final JPanel contentPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private JButton buttonRegister;

    //for session manager
    private String userEmail = null;

    //dragging the form
    private boolean isdragging = false;
    private Point mouseoffset;

    private JFrame substrateFrame;

    private static final int W = 420;
    private static final int H = 250;
    private boolean isRegistering = false;
    private boolean isLoggin = false;

    public LoginForm(JFrame SubstrateFrame) {
        this.substrateFrame = SubstrateFrame;
        //=== Frame
        frame = new JFrame();
        frame.setSize(W, H);
        frame.setLocationRelativeTo(substrateFrame);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        // Title Bar
        titleBar = new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(169, 183, 198));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));
        frame.add(titleBar, BorderLayout.NORTH);
        //=== Title Label
        titleLabel = new JLabel("Login Form");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 200, 30);
        titleBar.add(titleLabel);

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                    if (!isLoggin && !isRegistering && focusedWindow != frame) {
                        frame.dispose();
                        substrateFrame.getGlassPane().setVisible(false);
                        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
        //=== Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(238, 236, 233));
        contentPanel.setBorder(new LineBorder(new Color(216, 200, 196), 5));
        contentPanel.setBounds(10, 30, frame.getWidth() - 10, frame.getHeight() - 40);
        frame.add(contentPanel);
        //=== username Label
        JLabel usernameLabel = new JLabel("Email:");
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

        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonLogin.setBackground(new Color(255, 51, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonLogin.setBackground(new Color(255, 102, 0));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                loginAction();
            }

        });
        contentPanel.add(buttonLogin);
        frame.getRootPane().setDefaultButton(buttonLogin);
        buttonLogin.addActionListener(e -> {
            loginAction(); // reuse the same logic from mousePressed
        });
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

        buttonRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonRegister.setBackground(new Color(0, 51, 204));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonRegister.setBackground(new Color(0, 102, 255));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isRegistering = true;
                frame.dispose();
                new RegisterForm(substrateFrame);
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
        frame.setVisible(true);
    }

    private void loginAction() {
        isLoggin = true;
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (User.checkLogin(username, password)) {
            User user = User.fetchUserByEmail(username);
            User.setCurrentUser(user);

            // === Age restriction check ===
            if (substrateFrame instanceof MovieBooking.BookLargePage || substrateFrame instanceof MovieBooking.BookSmallPage) {
                Order order = (substrateFrame instanceof MovieBooking.BookLargePage)
                        ? ((MovieBooking.BookLargePage) substrateFrame).getOrder()
                        : ((MovieBooking.BookSmallPage) substrateFrame).getOrder();

                int userAge = user.getAge();          // Assume User class has getAge()
                int ageLimit = order.getMovie().getAgeLimit();   // Assume Movie class has getAgeLimit()

                if (userAge < ageLimit) {
                    JOptionPane.showMessageDialog(frame,
                            "您未滿 " + ageLimit + " 歲，無法訂購本電影票。",
                            "年齡限制", JOptionPane.WARNING_MESSAGE);
                    isLoggin = false;
                    return;
                }
            }

            frame.dispose();
            substrateFrame.dispose();

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
            isLoggin = false;
        }
    }

    public static void main(String[] args) {
        JFrame dummyFrame = new JFrame();
        dummyFrame.setSize(W, H);
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dummyFrame.setLocationRelativeTo(null);
        dummyFrame.setVisible(true);

        new LoginForm(dummyFrame);
    }

}
