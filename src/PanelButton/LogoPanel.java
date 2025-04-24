package PanelButton;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class LogoPanel extends JPanel {

    public LogoPanel() {
        setPreferredSize(new Dimension(UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT));
        setLayout(null); // no layout manager
        setOpaque(false); // transparent background

        JLabel logoLabel = new JLabel();
        logoLabel.setBounds(0, 0, UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT);

        // Load and scale the logo image
        ImageIcon icon = new ImageIcon(getClass().getResource("/pngs/Logo.png"));
        Image scaledImage = icon.getImage().getScaledInstance(
                UIConstants.LOGO_WIDTH,
                UIConstants.LOGO_HEIGHT,
                Image.SCALE_SMOOTH
        );
        logoLabel.setIcon(new ImageIcon(scaledImage));

        add(logoLabel);
    }
}