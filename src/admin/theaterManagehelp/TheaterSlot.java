package admin.theaterManagehelp;

import global.UIConstants;
import global.ToggleButtonPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TheaterSlot extends JPanel {

    private JLabel nameLabel;
    private ToggleButtonPanel toggleButton;
    private Runnable onClick = null;

    public TheaterSlot(String theaterName, boolean isActive) {
        setLayout(null);
        setPreferredSize(new Dimension(UIConstants.LEFT_PANEL_WIDTH, 60));
        setMaximumSize(new Dimension(UIConstants.LEFT_PANEL_WIDTH, 60));
        setBackground(new Color(242, 234, 230));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setOpaque(false);

        int height = 60;
        int labelHeight = 20;
        int toggleHeight = 30;
        int paddingX = 20;

        nameLabel = new JLabel(theaterName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setBounds(paddingX, (height - labelHeight) / 2, 160, labelHeight);
        add(nameLabel);

        toggleButton = new ToggleButtonPanel(isActive);
        toggleButton.setBounds(getPreferredSize().width - paddingX - 50, (height - toggleHeight) / 2, 50, toggleHeight);
        add(toggleButton);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (onClick != null) {
                    onClick.run();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        g2.dispose();
        super.paintComponent(g);
    }

    public void setTheaterName(String name) {
        nameLabel.setText(name);
    }

    public boolean isActive() {
        return toggleButton.isToggled();
    }

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }
}
