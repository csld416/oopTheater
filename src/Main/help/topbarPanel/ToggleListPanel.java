package Main.help.topbarPanel;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class ToggleListPanel extends JPanel {

    public ToggleListPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(UIConstants.TOGGLE_ICON_WIDTH, UIConstants.TOGGLE_ICON_WIDTH));
        setBackground(UIConstants.COLOR_MAIN_DARK);
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int iconSize = UIConstants.TOGGLE_ICON_WIDTH; // e.g. 30px for padding

        // === Icon (Left)
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize); // slight padding

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Burgers.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));

        add(iconLabel);
    }
}