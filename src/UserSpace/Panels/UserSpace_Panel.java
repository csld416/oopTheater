package UserSpace.Panels;

import global.CapsuleButton;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class UserSpace_Panel extends JPanel {

    // ====== Layout constants ======
    private final int ICON_SIZE = 100;
    private final int SPACE_BETWEEN_ICON_AND_LABEL = 30;
    private final int SPACE_BETWEEN_ROWS = 20;
    private final int BUTTON_Y_OFFSET = 220;

    private final int rightPanelWidth = UIConstants.FRAME_WIDTH - UIConstants.LEFT_PANEL_WIDTH;

    public UserSpace_Panel() {
        setLayout(null);
        setBackground(UIConstants.COLOR_MAIN_LIGHT);

        // ====== Load Icon ======
        JLabel userIcon = new JLabel();
        ImageIcon icon = new ImageIcon("src/UserSpace/icons/user.png");
        Image scaledImage = icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        userIcon.setIcon(new ImageIcon(scaledImage));
        userIcon.setSize(ICON_SIZE, ICON_SIZE);

        // ====== Labels and Values ======
        JLabel shoppingCreditLabel = new JLabel("\u0024購物金");
        shoppingCreditLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        shoppingCreditLabel.setSize(100, 30);

        JLabel amountLabel = new JLabel("0元");
        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        amountLabel.setForeground(new Color(198, 143, 124));
        amountLabel.setSize(60, 30);

        JLabel transactionFeeLabel = new JLabel("手續費");
        transactionFeeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        transactionFeeLabel.setForeground(Color.GRAY);
        transactionFeeLabel.setSize(100, 25);

        JLabel smallAmountLabel = new JLabel("0元");
        smallAmountLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        smallAmountLabel.setForeground(Color.GRAY);
        smallAmountLabel.setSize(60, 25);

        // ====== Compute group total width ======
        int textBlockWidth = Math.max(shoppingCreditLabel.getWidth() + amountLabel.getWidth(),
                                      transactionFeeLabel.getWidth() + smallAmountLabel.getWidth()) + SPACE_BETWEEN_ICON_AND_LABEL;

        int totalGroupWidth = ICON_SIZE + SPACE_BETWEEN_ICON_AND_LABEL + textBlockWidth;
        int startX = (rightPanelWidth - totalGroupWidth) / 2;

        int textStartX = startX + ICON_SIZE + SPACE_BETWEEN_ICON_AND_LABEL;

        // ====== Set positions ======

        // Icon roughly centered vertically relative to the two rows
        int iconStartY = 100;

        userIcon.setLocation(startX, iconStartY-5);

        // First row ($購物金 + amount)
        int firstRowY = iconStartY;
        shoppingCreditLabel.setLocation(textStartX, firstRowY);
        amountLabel.setLocation(textStartX + shoppingCreditLabel.getWidth() + 10, firstRowY);

        // Second row (手續費 + amount)
        int secondRowY = firstRowY + shoppingCreditLabel.getHeight() + SPACE_BETWEEN_ROWS;
        transactionFeeLabel.setLocation(textStartX, secondRowY);
        smallAmountLabel.setLocation(textStartX + transactionFeeLabel.getWidth() + 10, secondRowY);

        // ====== Add to Panel ======
        add(userIcon);
        add(shoppingCreditLabel);
        add(amountLabel);
        add(transactionFeeLabel);
        add(smallAmountLabel);

        // ====== Capsule Button ======
        CapsuleButton barcodeCapsule = new CapsuleButton(
                "會員條碼",
                new Color(157, 161, 149),
                new Color(138, 141, 127),
                new Dimension(200, 60)
        );

        Dimension capsuleSize = barcodeCapsule.getPreferredSize();
        int buttonX = (rightPanelWidth - capsuleSize.width) / 2;
        int buttonY = secondRowY + 120; // Proper distance from last row

        barcodeCapsule.setBounds(buttonX, buttonY, capsuleSize.width, capsuleSize.height);

        add(barcodeCapsule);
    }
}