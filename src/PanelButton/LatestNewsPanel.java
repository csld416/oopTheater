package PanelButton;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class LatestNewsPanel extends JPanel {

    private JLabel textLabel;

    public LatestNewsPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(UIConstants.ICON_PANEL_WIDTH, UIConstants.ICON_PANEL_HEIGHT));
        setBackground(new Color(169, 183, 198));
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int iconSize = UIConstants.ICON_PANEL_HEIGHT - 10;

        // Icon
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize);
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/newspaper.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));
        add(iconLabel);

        // Text
        textLabel = new JLabel("最新消息");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textLabel.setBounds(iconSize + 20, 0, UIConstants.ICON_PANEL_WIDTH - iconSize - 10, UIConstants.ICON_PANEL_HEIGHT);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(textLabel);
    }

    public JLabel getLabel() {
        return textLabel;
    }
}