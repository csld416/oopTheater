package Main.TicketHelp;

import Data.Order;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class TicketPanel_2 extends JPanel {

    private final int WIDTH = 600;
    private final int HEIGHT = 180;
    private final int ARC = 30;
    private final Order order;

    public TicketPanel_2(Order order) {
        this.order = order;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setOpaque(false);  // So the left/right segment's custom paint shows through

        initContent();
    }

    private void initContent() {
        // === Left Segment (Rounded corner left) ===
        LocalDateTime time = order.getShowtime().getStartTime().toLocalDateTime();
        String year = String.valueOf(time.getYear());
        String date = String.format("%02d/%02d", time.getMonthValue(), time.getDayOfMonth());
        String timeStr = String.format("%02d:%02d", time.getHour(), time.getMinute());
        String statusText = order.getStatus() == -1 ? "已取消" : "已使用";

        Ticket2LeftPanel left = new Ticket2LeftPanel(
                year,
                date,
                timeStr,
                statusText,
                new Color(245, 245, 245),
                ARC
        );
        left.setBounds(0, 0, 100, HEIGHT);
        add(left);

        // === Right Segment (Rounded corner right) ===
        Ticket2RightPanel right = new Ticket2RightPanel(order, WIDTH - 100, HEIGHT, ARC);
        right.setBounds(100, 0, WIDTH - 100, HEIGHT);
        add(right);
    }

    // No painting here — corners handled by left/right components
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("TicketPanel_2 Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 300);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));
            frame.add(new TicketPanel_2(Data.Order.dummyOrder));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
