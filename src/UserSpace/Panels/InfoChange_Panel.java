package UserSpace.Panels;

import connection.DatabaseConnection;
import global.CapsuleButton;
import Data.User;
import GlobalConst.Const;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// ... (same package & imports)
public class InfoChange_Panel extends JPanel {

    private final JTextField fullnameField;
    private final JTextField phoneField;
    private final JLabel emailDisplayLabel;
    private final JRadioButton maleRadioButton;
    private final JRadioButton femaleRadioButton;
    private final JLabel profilePictureLabel;
    private final CapsuleButton browseButton;
    private final CapsuleButton updateButton;
    private final ButtonGroup genderGroup;

    // Layout Constants
    private final int FIELD_WIDTH = 275;
    private final int FIELD_HEIGHT = 30;
    private final int LABEL_WIDTH = 100;
    private final int FIELD_SPACING_Y = 50;
    private final int TITLE_Y = 30;
    private final int FORM_START_Y = 100;
    private final int rightPanelWidth = Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH;
    private final int START_X = (rightPanelWidth - (LABEL_WIDTH + FIELD_WIDTH)) / 2;

    public InfoChange_Panel() {
        setLayout(null);
        setBackground(Const.COLOR_MAIN_LIGHT);

        // Title
        JLabel titleLabel = new JLabel("修改個人資訊", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBounds(0, TITLE_Y, rightPanelWidth, 40);
        add(titleLabel);

        // Email (non-editable)
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel.setBounds(START_X, FORM_START_Y, LABEL_WIDTH, FIELD_HEIGHT);
        add(emailLabel);

        emailDisplayLabel = new JLabel();
        emailDisplayLabel.setOpaque(true);
        emailDisplayLabel.setBackground(new Color(230, 230, 230));
        emailDisplayLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        emailDisplayLabel.setBorder(new LineBorder(Color.GRAY));
        emailDisplayLabel.setHorizontalAlignment(SwingConstants.LEFT);
        emailDisplayLabel.setBounds(START_X + LABEL_WIDTH, FORM_START_Y, FIELD_WIDTH, FIELD_HEIGHT);
        add(emailDisplayLabel);

        // Full Name
        JLabel fullnameLabel = new JLabel("姓名:");
        fullnameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        fullnameLabel.setBounds(START_X, FORM_START_Y + FIELD_SPACING_Y, LABEL_WIDTH, FIELD_HEIGHT);
        add(fullnameLabel);

        fullnameField = new JTextField();
        fullnameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        fullnameField.setBounds(START_X + LABEL_WIDTH, FORM_START_Y + FIELD_SPACING_Y, FIELD_WIDTH, FIELD_HEIGHT);
        add(fullnameField);

        // Phone
        JLabel phoneLabel = new JLabel("電話:");
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        phoneLabel.setBounds(START_X, FORM_START_Y + FIELD_SPACING_Y * 2, LABEL_WIDTH, FIELD_HEIGHT);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        phoneField.setBounds(START_X + LABEL_WIDTH, FORM_START_Y + FIELD_SPACING_Y * 2, FIELD_WIDTH, FIELD_HEIGHT);
        add(phoneField);

        // Gender
        JLabel genderLabel = new JLabel("性別:");
        genderLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        genderLabel.setBounds(START_X, FORM_START_Y + FIELD_SPACING_Y * 3, LABEL_WIDTH, FIELD_HEIGHT);
        add(genderLabel);

        maleRadioButton = new JRadioButton("男");
        maleRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        maleRadioButton.setBackground(Const.COLOR_MAIN_LIGHT);
        maleRadioButton.setBounds(START_X + LABEL_WIDTH, FORM_START_Y + FIELD_SPACING_Y * 3, 80, FIELD_HEIGHT);

        femaleRadioButton = new JRadioButton("女");
        femaleRadioButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        femaleRadioButton.setBackground(Const.COLOR_MAIN_LIGHT);
        femaleRadioButton.setBounds(START_X + LABEL_WIDTH + 60, FORM_START_Y + FIELD_SPACING_Y * 3, 80, FIELD_HEIGHT);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        add(maleRadioButton);
        add(femaleRadioButton);

        // Profile Pic
        JLabel profilePicLabel = new JLabel("頭像:");
        profilePicLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        profilePicLabel.setBounds(START_X, FORM_START_Y + FIELD_SPACING_Y * 4, LABEL_WIDTH, FIELD_HEIGHT);
        add(profilePicLabel);

        browseButton = new CapsuleButton(
                "選擇圖片",
                new Color(157, 161, 149),
                new Color(138, 141, 127),
                new Dimension(120, 40)
        );
        browseButton.setBounds(START_X + LABEL_WIDTH, FORM_START_Y + FIELD_SPACING_Y * 4, 120, 40);
        add(browseButton);

        profilePictureLabel = new JLabel();
        profilePictureLabel.setBorder(new LineBorder(Color.GRAY));
        profilePictureLabel.setBounds(START_X + LABEL_WIDTH + 140, FORM_START_Y + (int) (FIELD_SPACING_Y * 3.2), 130, 130);
        add(profilePictureLabel);

        // Update Button
        updateButton = new CapsuleButton("確認修改",
                new Color(122, 140, 153),
                new Color(90, 107, 122),
                new Dimension(200, 50)
        );
        int updateButtonX = (rightPanelWidth - 200) / 2;
        int updateButtonY = FORM_START_Y + FIELD_SPACING_Y * 6;
        updateButton.setBounds(updateButtonX + 60, updateButtonY + 20, 150, 50);
        add(updateButton);

        fetchUserData();

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateUserData();
            }
        });
    }

    private void fetchUserData() {
        User user = User.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "尚未登入或找不到使用者資料", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        fullnameField.setText(user.getName());
        phoneField.setText(user.getPhoneNumber());
        emailDisplayLabel.setText(user.getEmail());

        if ("Male".equalsIgnoreCase(user.getGender())) {
            maleRadioButton.setSelected(true);
        } else {
            femaleRadioButton.setSelected(true);
        }
        if (user.getProfileImage() != null) {
            Image scaled = user.getProfileImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(scaled));
        } else {
            // Optional fallback to default image if profileImage is null
            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/icons/profile-icon.jpg"));
            Image scaled = defaultIcon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            profilePictureLabel.setIcon(new ImageIcon(scaled));
        }
    }

    private boolean validateFields() {
        if (fullnameField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有欄位必須填寫！", "驗證錯誤", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateUserData() {
        if (!validateFields()) {
            return;
        }

        try (Connection conn = new DatabaseConnection().getConnection()) {
            String sql = "UPDATE users SET fullname = ?, phone = ?, gender = ? WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullnameField.getText().trim());
            stmt.setString(2, phoneField.getText().trim());
            stmt.setString(3, maleRadioButton.isSelected() ? "Male" : "Female");
            stmt.setString(4, User.currUser.getEmail());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "資料更新成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "更新失敗，請稍後重試。", "錯誤", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "更新資料錯誤: " + e.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        User.setCurrentUser(User.dummyUser);
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("個人資訊測試介面");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new InfoChange_Panel());
            frame.setVisible(true);
        });
    }
}
