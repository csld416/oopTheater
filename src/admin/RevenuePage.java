package admin;

import GlobalConst.Const;
import admin.topBar.AdminTopBarPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RevenuePage extends JFrame {

    public RevenuePage() {
        setTitle("營收統計");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initTopBar();
        initChart();

        setVisible(true);
    }

    private void initTopBar() {
        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        add(topBar);
    }

    private void initChart() {
        Map<String, Integer> revenueMap = fetchRevenueData();

        if (revenueMap.isEmpty()) {
            JLabel noData = new JLabel("無資料", SwingConstants.CENTER);
            noData.setFont(new Font("SansSerif", Font.PLAIN, 20));
            noData.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH, 100);
            add(noData);
            return;
        }

        JPanel pieChartPanel = new PieChartPanel(revenueMap);
        pieChartPanel.setBounds(0, Const.TOP_BAR_HEIGHT - 60, Const.FRAME_WIDTH, Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT - 40);
        add(pieChartPanel);
    }

    private Map<String, Integer> fetchRevenueData() {
        Map<String, Integer> map = new HashMap<>();
        String sql = """
            SELECT m.title, SUM(t.total_price) as revenue
            FROM Tickets t
            JOIN Movies m ON t.movie_id = m.id
            WHERE t.status >= 0
            GROUP BY t.movie_id
        """;

        try (Connection conn = new connection.DatabaseConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                int revenue = rs.getInt("revenue");
                map.put(title, revenue);
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to fetch revenue: " + e.getMessage());
        }

        return map;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RevenuePage::new);
    }
}