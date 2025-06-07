package admin;

import admin.topBar.AdminTopBarPanel;
import GlobalConst.Const;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RevenuePage extends JFrame {

    private JPanel dashboardPanel;

    public RevenuePage() {
        setTitle("Revenue Dashboard");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);  // absolute layout to position top bar and main manually

        initTop();
        initMain();

        setVisible(true);
    }

    private void initTop() {
        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        add(topBar);
    }

    private void initMain() {
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        dashboardPanel.setBackground(new Color(240, 240, 240));

        int topOffset = Const.TOP_BAR_HEIGHT;
        dashboardPanel.setBounds(0, topOffset, Const.FRAME_WIDTH, Const.FRAME_HEIGHT - topOffset);
        add(dashboardPanel);

        addDataPanel("Sales", "$500k");
        addDataPanel("Expenses", "$350k");
        addDataPanel("Profit", "$150k");
        addDataPanel("Customers", "$1,000");

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChart(g, getHeight());
            }
        };
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(740, 300));
        chartPanel.setBackground(Color.WHITE);
        dashboardPanel.add(chartPanel);

        JLabel chartTitleLabel = new JLabel("Orders");
        chartTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chartTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chartTitleLabel.setOpaque(true);
        chartTitleLabel.setBackground(new Color(150, 50, 50));
        chartTitleLabel.setForeground(Color.WHITE);
        chartPanel.add(chartTitleLabel, BorderLayout.NORTH);
    }

    private void addDataPanel(String title, String value) {
        JPanel dataPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawDataPanel(g, title, value, getWidth(), getHeight());
            }
        };
        dataPanel.setLayout(new GridLayout(2, 1));
        dataPanel.setPreferredSize(new Dimension(170, 100));
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setBorder(new LineBorder(new Color(255, 204, 0), 5));
        dashboardPanel.add(dataPanel);
    }

    private void drawDataPanel(Graphics g, String title, String value, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, width, height, 20, 20);

        g2d.setColor(new Color(150, 50, 50));
        g2d.fillRect(0, 0, width, 40);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString(title, 20, 30);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(value, 20, 75);
    }

    private void drawChart(Graphics g, int height) {
        Graphics2D g2d = (Graphics2D) g;

        int barWidth = 60;
        int barSpacing = 55;
        int startX = 50;
        int startY = height - 80;

        int[] data = {100, 200, 150, 300, 250, 350};
        int maxValue = 0;
        for (int value : data) {
            if (value > maxValue) maxValue = value;
        }

        Color barColor = new Color(76, 175, 80);
        Color labelColor = Color.BLACK;

        for (int i = 0; i < data.length; i++) {
            int barHeight = (int) ((double) data[i] / maxValue * (startY - 60));
            int x = startX + (barWidth + barSpacing) * i;
            int y = startY - barHeight;
            g2d.setColor(barColor);
            g2d.fillRect(x, y, barWidth, barHeight);

            g2d.setColor(labelColor);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(String.valueOf(data[i]), x + 10, y - 10);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("Product " + (i + 1), x + 5, startY + 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RevenuePage::new);
    }
}