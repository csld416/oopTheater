package Pages;

import Main.help.TopBarPanel;
import global.*;

import javax.swing.*;
import java.awt.*;

public class LatestNewsPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel contentPanel;

    public LatestNewsPage() {
        setTitle("Latest News Page");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        initTopBar();
        initContent();

        setVisible(true);
    }

    private void initTopBar() {
        topBarSlot = new JPanel(null);
        topBarSlot.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initContent() {
        int y = UIConstants.TOP_BAR_HEIGHT;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        contentPanel = new JPanel();
        contentPanel.setBounds(0, y, UIConstants.FRAME_WIDTH, height);
        contentPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        contentPanel.setLayout(new GridBagLayout()); // to center the label

        JLabel label = new JLabel("LatestNews");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        contentPanel.add(label);

        add(contentPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LatestNewsPage::new);
    }
}