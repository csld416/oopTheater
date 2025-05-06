package MovieBooking.foodPanels;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class DrinkPanel extends JPanel {

    public DrinkPanel() {
        setPreferredSize(new Dimension(UIConstants.FOOD_PANEL_WIDTH, UIConstants.FOOD_PANEL_HEIGHT));
        setLayout(new GridBagLayout()); // center alignment
        JLabel label = new JLabel("這是飲料類面板");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(label);
    }
}