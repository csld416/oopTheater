package Main;

import Data.Order;
import GlobalConst.Const;
import Main.TicketHelp.TicketPanel_1;
import Main.TicketHelp.TicketPanel_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyTicketSpacePage extends JFrame {

    private final int USED = 0;
    private final int FRESH = 1;
    private final int EXPIRED = -1;
    private final int REFUNDED = -2;

    private JPanel topBarSlot;
    private JPanel middlePanel;
    private JPanel contentPanel;

    private final Color DEFAULT_BG = Color.WHITE;
    private final Color HOVER_BG = new Color(230, 230, 230);
    private final Color SELECTED_BG = new Color(200, 230, 255);
    private final Font FONT = new Font("SansSerif", Font.BOLD, 16);

    private int tabSelect = 0; // 0 = 未使用, 1 = 已使用 (paging)

    private JPanel tabNotUsed;
    private JPanel tabUsed;

    public MyTicketSpacePage() {
        setTitle("My Ticket Page");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        Data.Order.markExpiredTicketsForCurrentUser();

        initTopBar();
        initMiddle();
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

    private void initMiddle() {
        middlePanel = new JPanel(null);
        middlePanel.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH, 60);
        middlePanel.setBackground(new Color(245, 245, 245));

        int tabWidth = 120;
        int tabHeight = 40;
        int spacing = 20;
        int y = 10;
        int x0 = (Const.FRAME_WIDTH - (tabWidth * 2 + spacing)) / 2;
        int x1 = x0 + tabWidth + spacing;

        tabNotUsed = createTabPanel("未使用", x0, y, tabWidth, tabHeight, 0);
        tabUsed = createTabPanel("已使用", x1, y, tabWidth, tabHeight, 1);

        middlePanel.add(tabNotUsed);
        middlePanel.add(tabUsed);

        add(middlePanel);
    }

    private JPanel createTabPanel(String text, int x, int y, int w, int h, int id) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(x, y, w, h);
        panel.setBackground(tabSelect == id ? SELECTED_BG : DEFAULT_BG);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(FONT);
        panel.add(label, BorderLayout.CENTER);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (tabSelect != id) {
                    panel.setBackground(HOVER_BG);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tabSelect != id) {
                    panel.setBackground(DEFAULT_BG);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (tabSelect != id) {
                    tabSelect = id;
                    refreshContent();
                    tabNotUsed.setBackground(tabSelect == 0 ? SELECTED_BG : DEFAULT_BG);
                    tabUsed.setBackground(tabSelect == 1 ? SELECTED_BG : DEFAULT_BG);
                }
            }
        });

        return panel;
    }

    private JScrollPane scrollPane;

    private void initContent() {
        int y = Const.TOP_BAR_HEIGHT + 60;
        int height = Const.FRAME_HEIGHT - y;

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // VERTICAL layout
        contentPanel.setBackground(Const.COLOR_MAIN_LIGHT);

        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        wrapperPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        wrapperPanel.add(contentPanel);

        scrollPane = new JScrollPane(wrapperPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, y, Const.FRAME_WIDTH, height);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
        loadContent();
    }

    private void refreshContent() {
        contentPanel.removeAll();
        loadContent();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void loadContent() {
        contentPanel.removeAll(); // Clear previous items

        ArrayList<Order> allOrders = Data.Order.getList();
        ArrayList<Order> filtered = new ArrayList<>();

        for (Order o : allOrders) {
            int status = o.getStatus();
            if ((tabSelect == 0 && status == 1) || (tabSelect == 1 && (status == 0 || status == -1 || status == -2))) {
                filtered.add(o);
            }
        }

        if (filtered.isEmpty()) {
            JLabel emptyLabel = new JLabel("目前此區域尚無票券資料");
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(emptyLabel);
        } else {
            for (Order o : filtered) {
                contentPanel.add(Box.createVerticalStrut(8));
                if (tabSelect == 0) {
                    contentPanel.add(new TicketPanel_1(o)); // 未使用
                } else {
                    contentPanel.add(new TicketPanel_2(o)); // 已使用 或 已取消
                }
            }
        }

        contentPanel.add(Box.createVerticalGlue()); // Pushes content upward
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void refreshContentExternally() {
        int userId = Data.User.getCurrentUser().getId();
        Data.Order.clearCache(userId); // 👈 force invalidate cache
        contentPanel.removeAll();
        loadContent(); // this will now refetch fresh orders
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyTicketSpacePage::new);
    }
}
