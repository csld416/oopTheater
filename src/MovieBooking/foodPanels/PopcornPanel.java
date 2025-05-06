package MovieBooking.foodPanels;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class PopcornPanel extends JPanel {

    public PopcornPanel() {
        setPreferredSize(new Dimension(UIConstants.FOOD_PANEL_WIDTH, UIConstants.FOOD_PANEL_HEIGHT));
        setLayout(new GridBagLayout()); // center alignment
        JLabel label = new JLabel("這是爆米花類面板");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(label);
    }
}