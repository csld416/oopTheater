package UserSpace.Panels;

import GlobalConst.Const;
import global.CapsuleButton;

import javax.swing.*;
import java.awt.*;

public class ChangePassword_Panel extends JPanel {

    private JPasswordField changePasswordField;
    private JPasswordField confirmPasswordField;

    // ====== Y Positions ======
    private final int TITLE_Y = 70;
    private final int PWD_LABEL_Y = TITLE_Y + 80;
    private final int PWD_FIELD_Y = PWD_LABEL_Y + 30;
    private final int CONFIRM_LABEL_Y = PWD_FIELD_Y + 70;
    private final int CONFIRM_FIELD_Y = CONFIRM_LABEL_Y + 30;
    private final int BUTTON_Y = CONFIRM_FIELD_Y + 100;

    public ChangePassword_Panel() {
        setLayout(null);
        setBackground(Const.COLOR_MAIN_LIGHT);

        int availableWidth = Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH;

        int fieldWidth = 300;
        int fieldHeight = 40;
        int fieldX = (availableWidth - fieldWidth) / 2;

        // ====== Title Label ======
        JLabel titleLabel = new JLabel("更改密碼", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, TITLE_Y, availableWidth, 40); // Centered horizontally
        add(titleLabel);

        // ====== Change Password Label ======
        JLabel changeLabel = new JLabel("新密碼：");
        changeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        changeLabel.setBounds(fieldX, PWD_LABEL_Y, 100, 30);
        add(changeLabel);

        // ====== Change Password Field ======
        changePasswordField = new JPasswordField();
        changePasswordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        changePasswordField.setBounds(fieldX, PWD_FIELD_Y, fieldWidth, fieldHeight);
        add(changePasswordField);

        // ====== Confirm Password Label ======
        JLabel confirmLabel = new JLabel("確認密碼：");
        confirmLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        confirmLabel.setBounds(fieldX, CONFIRM_LABEL_Y, 120, 30);
        add(confirmLabel);

        // ====== Confirm Password Field ======
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        confirmPasswordField.setBounds(fieldX, CONFIRM_FIELD_Y, fieldWidth, fieldHeight);
        add(confirmPasswordField);

        // ====== Clear Button ======
        CapsuleButton clearButton = new CapsuleButton(
                "清除",
                new Color(194, 182, 174),    // default color
                new Color(179, 165, 157),    // hover color
                new Dimension(150, 50)
        );
        clearButton.setBounds(fieldX, BUTTON_Y, 150, 50);
        add(clearButton);

        // ====== Confirm Button ======
        CapsuleButton confirmButton = new CapsuleButton(
                "確認更改",
                new Color(122, 140, 153),    // default color
                new Color(90, 107, 122),     // hover color
                new Dimension(150, 50)
        );
        confirmButton.setBounds(fieldX + 170, BUTTON_Y, 150, 50);
        add(confirmButton);

        // ====== Clear Fields Action ======
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                changePasswordField.setText("");
                confirmPasswordField.setText("");
            }
        });
    }
}