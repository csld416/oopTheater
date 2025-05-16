package LoginRegisterForm;

import Data.User;
import global.CapsuleButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;

public class RegisterForm {

    private JFrame frame;
    private final JPanel titleBar;
    private final JLabel titleLabel;
    private final JPanel contentPanel;
    private final JTextField fullnameField;
    private final JTextField usernameField;
    private final JTextField phoneField;
    private final JTextField dobField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JRadioButton maleRadioButton;
    private final JRadioButton femaleRadioButton;
    private final ButtonGroup genderGroup;
    private JLabel profilepictureImage;
    private CapsuleButton browseButton;
    private final CapsuleButton buttonRegister;
    private final CapsuleButton buttonLogin;

    private String selectedImage;
    private BufferedImage profileImage;

    private boolean isdragging = false;
    private Point mouseoffset;

    private final JFrame substrateFrame;
    private static final int W = 450;
    private static final int H = 550;
    private boolean isLoggin = false;
    private boolean isRegistering = false;

    private File profilePictureFile;

    public RegisterForm(JFrame SubstrateFrame) {
        this.substrateFrame = SubstrateFrame;
        frame = new JFrame();
        frame.setSize(W, H);
        frame.setLocationRelativeTo(substrateFrame);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());

        // === Title Bar ===
        titleBar = new JPanel(null);
        titleBar.setBackground(new Color(169, 183, 198));
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));

        titleLabel = new JLabel("Register Form");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 200, 30);
        titleBar.add(titleLabel);

        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                isdragging = true;
                mouseoffset = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                isdragging = false;
            }
        });
        titleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isdragging) {
                    Point newLoc = e.getLocationOnScreen();
                    newLoc.translate(-mouseoffset.x, -mouseoffset.y);
                    frame.setLocation(newLoc);
                }
            }
        });

        frame.add(titleBar, BorderLayout.NORTH);

        // === Content Panel ===
        contentPanel = new JPanel(null);
        contentPanel.setBackground(new Color(238, 236, 233));
        contentPanel.setBorder(new LineBorder(new Color(216, 200, 196), 0));
        contentPanel.setPreferredSize(new Dimension(W, H - 30));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Fields
        fullnameField = createLabeledField("Full name:", 20);
        usernameField = createLabeledField("Email:", 50);
        phoneField = createLabeledField("Phone:", 80);
        dobField = createDateField("Date of Birth:", 110);
        passwordField = createPasswordField("Password:", 140);
        confirmPasswordField = createPasswordField("Confirm Password:", 170);

        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 200, 80, 25);
        contentPanel.add(genderLabel);

        maleRadioButton = new JRadioButton("Male");
        maleRadioButton.setBounds(150, 200, 80, 25);
        femaleRadioButton = new JRadioButton("Female");
        femaleRadioButton.setBounds(240, 200, 80, 25);
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        maleRadioButton.setSelected(true);
        contentPanel.add(maleRadioButton);
        contentPanel.add(femaleRadioButton);

        // Profile Picture
        JLabel picLabel = new JLabel("Profile Picture:");
        picLabel.setBounds(30, 230, 100, 25);
        contentPanel.add(picLabel);

        // === Browse Button ===
        browseButton = new CapsuleButton("Browse",
                new Color(182, 193, 201),
                new Color(158, 171, 184),
                new Dimension(100, 30), 14);
        browseButton.setBounds(150, 230, 100, 30);
        browseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        browseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                chooseImage();
            }
        });
        contentPanel.add(browseButton);

        profilepictureImage = new JLabel();
        profilepictureImage.setBounds(270, 230, 130, 130);
        profilepictureImage.setBorder(new LineBorder(Color.GRAY, 1));
        setDefaultProfileImage();
        contentPanel.add(profilepictureImage);

        // === Browse Button ===
        browseButton = new CapsuleButton("Browse",
                new Color(182, 193, 201),
                new Color(158, 171, 184),
                new Dimension(100, 30), 14);
        browseButton.setBounds(150, 230, 100, 30);
        browseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        browseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedImage = selectedFile.getAbsolutePath();
                    profilePictureFile = selectedFile;
                    try {
                        profileImage = ImageIO.read(selectedFile);
                        Image scaled = profileImage.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                        profilepictureImage.setIcon(new ImageIcon(scaled));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        contentPanel.add(browseButton);

        // === Back to Login Button ===
        buttonLogin = new CapsuleButton("Back to Login",
                new Color(107, 123, 140),
                new Color(90, 107, 122),
                new Dimension(160, 40), 16);
        buttonLogin.setBounds(50, 400, 160, 40);
        buttonLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isLoggin = true;
                frame.dispose();
                new LoginForm(substrateFrame);
            }
        });
        contentPanel.add(buttonLogin);

        // === Register Button ===
        buttonRegister = new CapsuleButton("Register",
                new Color(189, 170, 165),
                new Color(68, 149, 145),
                new Dimension(160, 40), 16);
        buttonRegister.setBounds(245, 400, 160, 40);
        buttonRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isRegistering = true;
                if (isValidInput()) {
                    String fullname = fullnameField.getText().trim();
                    String email = usernameField.getText().trim();
                    String password = User.hashPassword(String.valueOf(passwordField.getPassword()));
                    String phone = phoneField.getText().trim();
                    String dobText = dobField.getText().trim();
                    String gender = maleRadioButton.isSelected() ? "Male" : "Female";

                    LocalDate dob = LocalDate.parse(dobText);
                    User.registerUser(fullname, email, password, phone, dob, gender, profilePictureFile);
                    new LoginForm(substrateFrame);
                    frame.dispose();
                }
                isRegistering = false;
            }
        });
        contentPanel.add(buttonRegister);
        frame.setVisible(true);
    }

    private JFormattedTextField createDateField(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(30, y, 120, 25);
        contentPanel.add(l);

        JFormattedTextField f = new JFormattedTextField(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        f.setBounds(150, y, 250, 25);
        f.setToolTipText("Format: yyyy-MM-dd");
        f.setText(LocalDate.now().toString());
        contentPanel.add(f);
        return f;
    }

    private JTextField createLabeledField(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(30, y, 100, 25);
        contentPanel.add(l);
        JTextField f = new JTextField();
        f.setBounds(150, y, 250, 25);
        contentPanel.add(f);
        return f;
    }

    private JPasswordField createPasswordField(String label, int y) {
        JLabel l = new JLabel(label);
        l.setBounds(30, y, 120, 25);
        contentPanel.add(l);
        JPasswordField f = new JPasswordField();
        f.setBounds(150, y, 250, 25);
        contentPanel.add(f);
        return f;
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            selectedImage = f.getAbsolutePath();
            try {
                profileImage = ImageIO.read(f);
                Image scaled = profileImage.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
                profilepictureImage.setIcon(new ImageIcon(scaled));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setDefaultProfileImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/profile-icon.jpg"));
        Image scaled = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        profilepictureImage.setIcon(new ImageIcon(scaled));
    }

    private boolean isValidInput() {
        String fullname = fullnameField.getText().trim();
        String email = usernameField.getText().trim();
        String phone = phoneField.getText().trim();
        String dobText = dobField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dobText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled.");
            return false;
        }

        if (!dobText.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            JOptionPane.showMessageDialog(frame, "Invalid date format. Use yyyy-MM-dd.");
            return false;
        }

        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.");
            return false;
        }

        try {
            java.time.LocalDate birthDate = java.time.LocalDate.parse(dobText);
            java.time.LocalDate now = java.time.LocalDate.now();
            int age = java.time.Period.between(birthDate, now).getYears();

            if (age <= 0) {
                JOptionPane.showMessageDialog(frame, "Date of Birth must be in the past.");
                return false;
            }
        } catch (java.time.format.DateTimeParseException e) {
            JOptionPane.showMessageDialog(frame, "Invalid date. Please use format yyyy-MM-dd and enter a valid date.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match.");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        JFrame dummyFrame = new JFrame();
        dummyFrame.setSize(W, H);
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dummyFrame.setLocationRelativeTo(null);
        dummyFrame.setVisible(true);
        new RegisterForm(dummyFrame);
    }
}
