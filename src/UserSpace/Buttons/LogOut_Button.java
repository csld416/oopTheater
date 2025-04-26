package UserSpace.Buttons;

import UserSpace.UserUIConstants;
import javax.swing.*;
import java.awt.*;

public class LogOut_Button extends JPanel {

    private JLabel label;
    private JLabel iconLabel;

    public LogOut_Button() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Create the icon label
        iconLabel = new JLabel();
        iconLabel.setBounds(UserUIConstants.ICON_X + 5, UserUIConstants.ICON_Y, 30, 30); // Icon size 30x30
        iconLabel.setIcon(new ImageIcon(new ImageIcon("src/UserSpace/icons/logout.png")
                .getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
        add(iconLabel);

        // Create the text label
        label = new JLabel("登出會員");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setBounds(UserUIConstants.TEXT_X, UserUIConstants.ICON_Y, 200, 30); // Align with icon
        add(label);
    }
}
