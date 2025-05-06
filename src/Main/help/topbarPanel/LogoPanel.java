package Main.help.topbarPanel;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

import Main.StartingPage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LogoPanel extends JPanel {

    public LogoPanel() {
        setPreferredSize(new Dimension(UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT));
        setLayout(null); // no layout manager
        setOpaque(false); // transparent background

        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(0, 0, UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT);

        // Load and scale the logo image
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/Logo.png"));
        Image scaledImage = icon.getImage().getScaledInstance(
                UIConstants.LOGO_WIDTH,
                UIConstants.LOGO_HEIGHT,
                Image.SCALE_SMOOTH
        );
        logoLabel.setIcon(new ImageIcon(scaledImage));

        // === Mouse Listener ===
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoLabel.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Find and dispose the top-level JFrame
                Window window = SwingUtilities.getWindowAncestor(LogoPanel.this);
                if (window instanceof JFrame) {
                    window.dispose();
                }
                // Launch StartingPage
                SwingUtilities.invokeLater(StartingPage::new);
            }
        });

        add(logoLabel);
    }
}