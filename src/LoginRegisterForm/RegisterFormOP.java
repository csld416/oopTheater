/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginRegisterForm;

import connection.DatabaseConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author csld
 */
public class RegisterFormOP {

    private JFrame frame;
    private JPanel titleBar;
    private JLabel minimizeLabel;
    private JLabel titleLabel;
    private JLabel closeLabel;
    private JPanel contentPanel;
    private JTextField fullnameField;
    private JTextField usernameField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private ButtonGroup genderGroup;
    private JLabel profilepictureImage;
    private JButton browseButton;
    private JButton buttonRegister;
    private JButton buttonLogin;

    private String selectedImage;
    private BufferedImage profileImage;
    
    private JLabel licenseLabel;
    private JTextField licenseField;

    private boolean isdragging = false;
    private Point mouseoffset;

    // the database variable
    private DatabaseConnection dbConnection;

    public RegisterFormOP() {
        //=== Frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 500);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        // Title Bar
        titleBar = new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(59, 76, 88));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));
        frame.add(titleBar, BorderLayout.NORTH);
        //=== Title Label
        titleLabel = new JLabel("Admin Register Form");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 200, 30);
        titleBar.add(titleLabel);
        //=== Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(245, 243, 241));
        contentPanel.setBorder(new LineBorder(new Color(255, 204, 0), 0));
        contentPanel.setBounds(10, 30, frame.getWidth() - 10, frame.getHeight() - 40);
        frame.add(contentPanel);
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
                System.exit(0);
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
                minimizeLabel.setForeground(Color.BLACK);
            }
        });
        titleBar.add(minimizeLabel);
        //=== Fullname (Label & Field)
        JLabel fullnameLabel = new JLabel("Full name:");
        fullnameLabel.setBounds(30, 20, 120, 25);
        contentPanel.add(fullnameLabel);
        fullnameField = new JTextField();
        fullnameField.setBounds(150, 20, 250, 25);
        contentPanel.add(fullnameField);
        //=== Username (Label & Field)
        JLabel usernameLabel = new JLabel("Email:");
        usernameLabel.setBounds(30, 50, 80, 25);
        contentPanel.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 250, 25);
        contentPanel.add(usernameField);
        //=== Password (Label & Field)
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 80, 80, 25);
        contentPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 80, 250, 25);
        contentPanel.add(passwordField);
        //=== confirmPassword (Label & Field)
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(30, 110, 120, 25);
        contentPanel.add(confirmPasswordLabel);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 110, 250, 25);
        contentPanel.add(confirmPasswordField);
        //=== Username (Label & Field)
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 140, 80, 25);
        contentPanel.add(phoneLabel);
        phoneField = new JTextField();
        phoneField.setBounds(150, 140, 250, 25);
        contentPanel.add(phoneField);
        //=== gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 170, 80, 25);
        contentPanel.add(genderLabel);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBounds(150, 170, 100, 25);
        maleRadioButton.setSelected(true);
        maleRadioButton.setFocusPainted(false);
        maleRadioButton.setBorderPainted(false);
        maleRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPanel.add(maleRadioButton);

        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBounds(260, 170, 100, 25);
        femaleRadioButton.setFocusPainted(false);
        femaleRadioButton.setBorderPainted(false);
        femaleRadioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contentPanel.add(femaleRadioButton);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        //=== Profile picture(add and Browse)
        JLabel profilePictureLabel = new JLabel("Profile Picture");
        profilePictureLabel.setBounds(30, 200, 120, 25);
        contentPanel.add(profilePictureLabel);
        //=== Browse button
        browseButton = new JButton("Browse");
        browseButton.setBounds(150, 200, 100, 25);
        browseButton.setFont(new Font("Arial", Font.PLAIN, 12));
        browseButton.setBackground(new Color(210, 193, 174));
        browseButton.setForeground(Color.WHITE);
        browseButton.setFocusPainted(false);
        browseButton.setBorderPainted(false);
        browseButton.setOpaque(true);
        browseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                browseButton.setBackground(new Color(189, 175, 154));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                browseButton.setBackground(new Color(210, 193, 174));
            }

        });

        // browse and display image
        browseButton.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(fileFilter);

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImage = selectedFile.getAbsolutePath();
                try {
                    //read the image file
                    profileImage = ImageIO.read(selectedFile);

                    int originalWidth = profileImage.getWidth();
                    int originalHeight = profileImage.getHeight();

                    int targetWidth = profilepictureImage.getWidth();
                    int targetHeight = profilepictureImage.getHeight();

                    double widthRatio = (double) targetWidth / originalWidth;
                    double heightRatio = (double) targetHeight / originalHeight;
                    double scaleFactor = Math.min(widthRatio, heightRatio); // Ensures the image fits inside

                    int scaledWidth = (int) (originalWidth * scaleFactor);
                    int scaledHeight = (int) (originalHeight * scaleFactor);

                    Image scaledImage = profileImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    profilepictureImage.setIcon(new ImageIcon(scaledImage));
                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                    profilepictureImage.setIcon(imageIcon);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        contentPanel.add(browseButton);

        profilepictureImage = new JLabel();
        profilepictureImage.setBounds(270, 200, 130, 130);
        profilepictureImage.setBorder(new LineBorder(Color.GRAY, 1));
        contentPanel.add(profilepictureImage);
        //=== License Label & Field
        licenseLabel = new JLabel("License:");
        licenseLabel.setBounds(30, 340, 120, 25);  // Adjust position as needed
        contentPanel.add(licenseLabel);

        licenseField = new JTextField();
        licenseField.setBounds(150, 340, 150, 25);  // Shorter field
        contentPanel.add(licenseField);

        //=== Button Register
        buttonRegister = new JButton("Register");
        buttonRegister.setBounds(225, 380, 170, 35);
        buttonRegister.setFont(new Font("Arial", Font.BOLD, 14));
        buttonRegister.setBackground(new Color(200, 182, 161));
        buttonRegister.setForeground(Color.WHITE);
        buttonRegister.setFocusPainted(false);
        buttonRegister.setBorderPainted(false);
        buttonRegister.setOpaque(true);
        buttonRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonRegister.addActionListener((e) -> {
            System.out.println("Register button clicked!");
            registerUser();
        });

        buttonRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonRegister.setBackground(new Color(185, 167, 142));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonRegister.setBackground(new Color(200, 182, 161));
            }

        });
        contentPanel.add(buttonRegister);
        //=== Button Login
        buttonLogin = new JButton("Back to Login");
        buttonLogin.setBounds(40, 380, 170, 35);
        buttonLogin.setFont(new Font("Arial", Font.BOLD, 14));
        buttonLogin.setBackground(new Color(160, 123, 94));
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.setFocusPainted(false);
        buttonLogin.setBorderPainted(false);
        buttonLogin.setOpaque(true);
        buttonLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonLogin.addActionListener((e) -> {
            frame.dispose();
            new LoginFormOP();
        });

        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonLogin.setBackground(new Color(140, 107, 82));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonLogin.setBackground(new Color(160, 123, 94));
            }

        });
        contentPanel.add(buttonLogin);
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
        // Default image icon
        ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icons/profile-icon.jpg"));
        int width = 130, height = 130;
        Image scaledImage = defaultIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        profilepictureImage = new JLabel(new ImageIcon(scaledImage));
        profilepictureImage.setBounds(270, 200, width, height);
        profilepictureImage.setBorder(new LineBorder(Color.GRAY, 1));
        profilepictureImage.setHorizontalAlignment(SwingConstants.CENTER);
        profilepictureImage.setVerticalAlignment(SwingConstants.CENTER);
        contentPanel.add(profilepictureImage);
        //=== Init
        frame.setVisible(true);
        dbConnection = new DatabaseConnection();
    }

    //show error msg
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "registration error", JOptionPane.ERROR_MESSAGE);
    }

    //show sucess msg
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "registration Success", JOptionPane.INFORMATION_MESSAGE);
    }

    //close the registration form
    private void closeRegisterForm() {
        frame.dispose();
    }

    //oprn login form
    private void openLoginForm() {
        new LoginFormOP();
    }

    //check exists
    private boolean existed(String username) {
        Connection connection = dbConnection.getConnection();
        String query = "SELECT * FROM `Operator` WHERE `email` = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //register user
    private void registerUser() {
        String fullname = fullnameField.getText();
        String email = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String phone = phoneField.getText().trim();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        String license = licenseField.getText().trim();
        String validLicense = "ADMIN-NTU-CSIE-0420";

        if (license.trim().isEmpty() || !license.equals(validLicense)) {
            showErrorMessage("Invalid license code. Please contact your supervisor.");
            return;
        }

        if (fullname.trim().isEmpty() || email.trim().isEmpty()
                || password.trim().isEmpty() || confirmPassword.trim().isEmpty()
                || phone.trim().isEmpty() || gender.trim().isEmpty()) {
            showErrorMessage("All fields must be filled");
            return;
        }

        if (existed(email)) {
            showErrorMessage("This Email already registers");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showErrorMessage("Passwords do not match");
            return;
        }

        try {
            Connection connection = dbConnection.getConnection();
            String query = "INSERT INTO `Operator`(`fullname`, `email`, `password`, `phone`, `gender`, `picture`) VALUES (?,?,?,?,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            prepareStatement.setString(1, fullname);
            prepareStatement.setString(2, email);
            prepareStatement.setString(3, password);
            prepareStatement.setString(4, phone);
            prepareStatement.setString(5, gender);

            File profilePictureFile;
            if (selectedImage != null) {
                profilePictureFile = new File(selectedImage);
            } else {
                profilePictureFile = new File("src/form/resources/profile-icon.jpg"); // Default profile picture
            }

            if (!profilePictureFile.exists()) {
                showErrorMessage("Error: Profile picture file not found!");
                return;
            }

            FileInputStream fileStream = new FileInputStream(profilePictureFile);
            prepareStatement.setBinaryStream(6, fileStream, profilePictureFile.length());

            int rowsAffected = prepareStatement.executeUpdate();

            if (rowsAffected > 0) {
                showSuccessMessage("Registration Successful!");
                closeRegisterForm();
                new LoginFormOP();
            } else {
                showErrorMessage("Registration Failed!");
            }

        } catch (SQLException ex) {
            showErrorMessage("SQL Error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (FileNotFoundException eex) {
            showErrorMessage("File Error: " + eex.getMessage());
            eex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new RegisterFormOP();
    }
}
