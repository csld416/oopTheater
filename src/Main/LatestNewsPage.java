package Main;

import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;

public class LatestNewsPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel contentPanel;

    public LatestNewsPage() {
        setTitle("Latest News Page");
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

        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, y, Const.FRAME_WIDTH, height);
        contentPanel.setBackground(Const.COLOR_MAIN_LIGHT);

        try {
            JEditorPane htmlPane = new JEditorPane();
            htmlPane.setContentType("text/html");
            htmlPane.setEditable(false);
            htmlPane.setPage(getClass().getResource("/text/News.html")); // uses src/text/News.html

            JScrollPane scrollPane = new JScrollPane(htmlPane);
            scrollPane.setBounds(30, 20, Const.FRAME_WIDTH - 60, height - 80);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scrolling
            scrollPane.setBorder(null);
            contentPanel.add(scrollPane);

        } catch (Exception e) {
            JLabel fallback = new JLabel("無法載入最新消息內容", SwingConstants.CENTER);
            fallback.setFont(new Font("SansSerif", Font.PLAIN, 18));
            fallback.setBounds(0, 100, Const.FRAME_WIDTH, 40);
            contentPanel.add(fallback);
        }

        add(contentPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LatestNewsPage::new);
    }
}
