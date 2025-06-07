package Main.help.topbarPanel;

import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StaffPanel extends JPanel {

    private JLabel textLabel;

    public StaffPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(Const.ICON_PANEL_WIDTH, Const.ICON_PANEL_HEIGHT));
        setBackground(Const.COLOR_MAIN_DARK);
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        int iconSize = Const.ICON_PANEL_HEIGHT - 10; // e.g. 30px for padding

        // === Icon (Left)
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(10, 5, iconSize, iconSize); // slight padding

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Staff.png"));
        Image scaled = icon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));

        add(iconLabel);

        // === Label (Right)
        textLabel = new JLabel("業務專區");
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textLabel.setBounds(iconSize + 20, 0, Const.ICON_PANEL_WIDTH - iconSize - 10, Const.ICON_PANEL_HEIGHT);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textLabel.setForeground(Color.BLACK);
            }
        });

        add(textLabel);
    }

    public JLabel getLabel() {
        return textLabel;
    }
}
