package Main.help.topbarPanel;

import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;

public class PersonalSpacePanel extends JPanel {

    private JLabel textLabel;

    public PersonalSpacePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(Const.ICON_PANEL_WIDTH, Const.ICON_PANEL_HEIGHT));
        setBackground(new Color(169, 183, 198));
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int iconSize = Const.ICON_PANEL_HEIGHT - 10; // e.g. 30px for padding

        // === Icon (Left)
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize); // slight padding

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/profile.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));

        add(iconLabel);

        // === Label (Right)
        textLabel = new JLabel("個人專區");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textLabel.setBounds(iconSize + 20, 0, Const.ICON_PANEL_WIDTH - iconSize - 10, Const.ICON_PANEL_HEIGHT);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);

        add(textLabel);
    }

    public JLabel getLabel() {
        return textLabel;
    }
}
