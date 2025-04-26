package UserSpace.Buttons;

import UserSpace.UserUIConstants;
import javax.swing.*;
import java.awt.*;

public class InfoChange_Button extends JPanel {

    private JLabel label;
    private JLabel iconLabel;

    public InfoChange_Button() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Create the icon label
        iconLabel = new JLabel();
        iconLabel.setBounds(UserUIConstants.ICON_X, UserUIConstants.ICON_Y, 30, 30); // JLabel size = 30x30
        iconLabel.setIcon(new ImageIcon(new ImageIcon("src/UserSpace/icons/setting.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))); // Image scaled to 30x30
        add(iconLabel);

        // Create the text label
        label = new JLabel("修改資料");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setBounds(UserUIConstants.TEXT_X, UserUIConstants.ICON_Y, 200, 30); // Leave space after icon
        add(label);
    }
}
