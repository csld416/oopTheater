package UserSpace.Buttons;

import UserSpace.UserUIConstants;
import javax.swing.*;
import java.awt.*;

public class UserSpace_Button extends JPanel {

    private JLabel label;
    private JLabel iconLabel;

    public UserSpace_Button() {
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // Create the icon label
        iconLabel = new JLabel();
        iconLabel.setBounds(UserUIConstants.ICON_X, UserUIConstants.ICON_Y, 30, 30); // Icon size = 30x30
        iconLabel.setIcon(new ImageIcon(new ImageIcon("src/UserSpace/icons/user.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        add(iconLabel);

        // Create the text label
        label = new JLabel("個人專區");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setBounds(UserUIConstants.TEXT_X, UserUIConstants.ICON_Y, 200, 30); // Align with icon's Y
        add(label);
    }
}