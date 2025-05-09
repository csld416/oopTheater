package Main.help.topbarPanel;

import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;

public class ToggleListPanel extends JPanel {

    public ToggleListPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(Const.TOGGLE_ICON_WIDTH, Const.TOGGLE_ICON_WIDTH));
        setBackground(Const.COLOR_MAIN_DARK);
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int iconSize = Const.TOGGLE_ICON_WIDTH; // e.g. 30px for padding

        // === Icon (Left)
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize); // slight padding

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Burgers.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));

        add(iconLabel);
    }
}