package MovieBooking.foodPanels;

import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;

public class DrinkPanel extends JPanel {

    public DrinkPanel() {
        setPreferredSize(new Dimension(Const.FOOD_PANEL_WIDTH, Const.FOOD_PANEL_HEIGHT));
        setLayout(new GridBagLayout()); // center alignment
        JLabel label = new JLabel("這是飲料類面板");
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(label);
    }
}