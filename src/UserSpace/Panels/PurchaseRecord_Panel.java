package UserSpace.Panels;

import GlobalConst.Const;
import javax.swing.*;
import java.awt.*;

public class PurchaseRecord_Panel extends JPanel {

    public PurchaseRecord_Panel() {
        setLayout(null);
        setBackground(Const.COLOR_MAIN_LIGHT);

        JLabel messageLabel = new JLabel("這裡是消費紀錄頁面喔！");
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        messageLabel.setForeground(Color.GRAY);

        Dimension size = messageLabel.getPreferredSize();
        messageLabel.setBounds((Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH - size.width) / 2,
                200,
                size.width,
                size.height
        );

        add(messageLabel);
    }
}