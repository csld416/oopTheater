package Main;

import GlobalConst.Const;
import Main.TopBarPanel;
import global.*;

import javax.swing.*;
import java.awt.*;

public class MyTicketSpacePage extends JFrame {

    private JPanel topBarSlot;
    private JPanel contentPanel;

    public MyTicketSpacePage() {
        setTitle("My Ticket Page");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        initTopBar();
        initContent();

        setVisible(true);
    }

    private void initTopBar() {
        topBarSlot = new JPanel(null);
        topBarSlot.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initContent() {
        int y = Const.TOP_BAR_HEIGHT;
        int height = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;

        contentPanel = new JPanel();
        contentPanel.setBounds(0, y, Const.FRAME_WIDTH, height);
        contentPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        contentPanel.setLayout(new GridBagLayout()); // to center the label

        JLabel label = new JLabel("My ticket Page");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        contentPanel.add(label);

        add(contentPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyTicketSpacePage::new);
    }
}