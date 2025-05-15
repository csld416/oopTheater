package UserSpace.Panels;

import connection.DatabaseConnection;
import global.CapsuleButton;
import Data.SessionManager;
import Data.User;
import GlobalConst.Const;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoChange_Panel extends JPanel {

    // === Fields ===
    private final JTextField fullnameField;
    private final JTextField emailField;
    private final JLabel phoneDisplayLabel;
    private final JRadioButton maleRadioButton;
    private final JRadioButton femaleRadioButton;
    private final JLabel profilePictureLabel;
    private final CapsuleButton browseButton;
    private final CapsuleButton updateButton;
    private final ButtonGroup genderGroup;

    private JLabel phoneLabel;

    // === Layout Constants ===
    private final int FIELD_WIDTH = 250;
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

        // ====== Title ======
        JLabel titleLabel = new JLabel("修改個人資訊", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBounds(0, TITLE_Y, rightPanelWidth, 40);
        add(titleLabel);

        // ====== Phone (Non-editable) ======
        phoneLabel = new JLabel("電話:");
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        phoneLabel.setBounds(START_X, FORM_START_Y, LABEL_WIDTH, FIELD_HEIGHT);
        add(phoneLabel);

        phoneDisplayLabel = new JLabel();
        phoneDisplayLabel.setOpaque(true);
        phoneDisplayLabel.setBackground(new Color(230, 230, 230));
        phoneDisplayLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        phoneDisplayLabel.setBorder(new LineBorder(Color.GRAY));
        phoneDisplayLabel.setHorizontalAlignment(SwingConstants.LEFT);
        phoneDisplayLabel.setBounds(START_X + LABEL_WIDTH, FORM_START_Y, FIELD_WIDTH, FIELD_HEIGHT);
        add(phoneDisplayLabel);

        // ====== Full Name ======
        JLabel fullnameLabel = new JLabel("姓名:");
        fullnameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        fullnameLabel.setBounds(START_X, FORM_START_Y + FIELD_SPACING_Y, LABEL_WIDTH, FIELD_HEIGHT);
        add(fullnameLabel);

        fullnameField = new JTextField();
        fullnameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        fullnameField.setBounds(START_X + LABEL_WIDTH, FORM_START_Y + FIELD_SPACING_Y, FIELD_WIDTH, FIELD_HEIGHT);
        add(fullnameField);

        // ====== Email ======
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel.setBounds(START_X, FORM_START_Y + FIELD_SPACING_Y * 2, LABEL_WIDTH, FIELD_HEIGHT);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        emailField.setBounds(START_X + LABEL_WIDTH, FORM_START_Y + FIELD_SPACING_Y * 2, FIELD_WIDTH, FIELD_HEIGHT);
        add(emailField);

        // ====== Gender ======
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
        femaleRadioButton.setBounds(START_X + LABEL_WIDTH + 100, FORM_START_Y + FIELD_SPACING_Y * 3, 80, FIELD_HEIGHT);

        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        add(maleRadioButton);
        add(femaleRadioButton);

        // ====== Profile Picture ======
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
        profilePictureLabel.setBounds(START_X + LABEL_WIDTH + 140, FORM_START_Y + FIELD_SPACING_Y * 4, 80, 80);
        add(profilePictureLabel);

        // ====== Update Button ======
        updateButton = new CapsuleButton(
                "確認修改",
                new Color(122, 140, 153),
                new Color(90, 107, 122),
                new Dimension(200, 50)
        );
        int updateButtonX = (rightPanelWidth - 200) / 2;
        int updateButtonY = FORM_START_Y + FIELD_SPACING_Y * 6;
        updateButton.setBounds(updateButtonX, updateButtonY, 200, 50);
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
        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT fullname, email, phone, gender FROM users WHERE phone = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, User.currUser.getPhoneNumber()); // fetch by phone
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("[DEBUG] Fetched data:");
                System.out.println("Fullname: " + rs.getString("fullname"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Gender: " + rs.getString("gender"));

                fullnameField.setText(rs.getString("fullname"));
                emailField.setText(rs.getString("email"));
                phoneDisplayLabel.setText(rs.getString("phone"));  // <<<< FIXED LINE
                String gender = rs.getString("gender");
                if ("Male".equalsIgnoreCase(gender)) {
                    maleRadioButton.setSelected(true);
                } else {
                    femaleRadioButton.setSelected(true);
                }
            } else {
                System.out.println("[DEBUG] No user found with phone: " + User.currUser.getPhoneNumber());
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "讀取使用者資料錯誤: " + e.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (fullnameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty()) {
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
            String sql = "UPDATE users SET fullname = ?, email = ?, gender = ? WHERE phone = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullnameField.getText().trim());
            stmt.setString(2, emailField.getText().trim());
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            stmt.setString(3, gender);
            stmt.setString(4, User.currUser.getPhoneNumber());

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
}
