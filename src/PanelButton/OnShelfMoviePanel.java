package PanelButton;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class OnShelfMoviePanel extends JPanel {

    private JLabel textLabel;

    public OnShelfMoviePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(UIConstants.ICON_PANEL_WIDTH, UIConstants.ICON_PANEL_HEIGHT));
        setBackground(UIConstants.COLOR_MAIN_DARK);
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int iconSize = UIConstants.ICON_PANEL_HEIGHT - 10; // e.g. 30px for padding

        // === Icon (Left)
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize); // slight padding

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Video.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));
        add(iconLabel);

        // === Label (Right)
        textLabel = new JLabel("上映電影");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textLabel.setBounds(iconSize + 20, 0, UIConstants.ICON_PANEL_WIDTH - iconSize - 10, UIConstants.ICON_PANEL_HEIGHT);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(textLabel);
    }

    public JLabel getLabel() {
        return textLabel;
    }
}