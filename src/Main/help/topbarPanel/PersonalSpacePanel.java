package Main.help.topbarPanel;

import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PersonalSpacePanel extends JPanel {

    private JLabel textLabel;

    public PersonalSpacePanel() {
        setLayout(null);
        setPreferredSize(new Dimension(Const.ICON_PANEL_WIDTH, Const.ICON_PANEL_HEIGHT));
        setBackground(new Color(169, 183, 198));

        int iconSize = Const.ICON_PANEL_HEIGHT - 10;

        // === Icon ===
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/profile.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));
        add(iconLabel);

        // === Text Label ===
        textLabel = new JLabel("個人專區");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textLabel.setForeground(Color.BLACK);
        textLabel.setBounds(iconSize + 20, 0, Const.ICON_PANEL_WIDTH - iconSize - 10, Const.ICON_PANEL_HEIGHT);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(textLabel);

        // === Hover Behavior ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textLabel.setForeground(Color.WHITE);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textLabel.setForeground(Color.BLACK);
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public JLabel getLabel() {
        return textLabel;
    }
}