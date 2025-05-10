package Main.TicketHelp;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Ticket2LeftPanel extends JPanel {

    private final String year;
    private final String date;
    private final String time;
    private final String statusText;
    private final Color bgColor;
    private final int arc;

    public Ticket2LeftPanel(String year, String date, String time, String statusText, Color bgColor, int arc) {
        this.year = year;
        this.date = date;
        this.time = time;
        this.statusText = statusText;
        this.bgColor = bgColor;
        this.arc = arc;

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(100, 180));

        initContent();
    }

    private void initContent() {
        JLabel yearLabel = new JLabel(year, SwingConstants.CENTER);
        yearLabel.setAlignmentX(CENTER_ALIGNMENT);
        yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel dateLabel = new JLabel(date, SwingConstants.CENTER);
        dateLabel.setAlignmentX(CENTER_ALIGNMENT);
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel timeLabel = new JLabel(time, SwingConstants.CENTER);
        timeLabel.setAlignmentX(CENTER_ALIGNMENT);
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel statusLabel = new JLabel(statusText, SwingConstants.CENTER);
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        statusLabel.setForeground(Color.RED);

        add(Box.createVerticalGlue());
        add(yearLabel);
        add(dateLabel);
        add(timeLabel);
        add(Box.createVerticalStrut(10));
        add(statusLabel);
        add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // === Shape with cut-out corners
        Area area = new Area(new Rectangle2D.Double(0, 0, width, height));
        area.subtract(new Area(new Ellipse2D.Double(-arc, -arc, arc * 2, arc * 2)));
        area.subtract(new Area(new Ellipse2D.Double(-arc, height - arc, arc * 2, arc * 2)));

        // === Fill background
        g2.setColor(bgColor);
        g2.fill(area);

        // === Draw border
        g2.setColor(Color.GRAY); // or any color you want
        g2.setStroke(new BasicStroke(1.5f));

        // Draw rectangle edges manually (top, right, bottom, etc.)
        g2.drawLine(arc, 0, width - 1, 0);                         // Top
        //g2.drawLine(width - 1, 0, width - 1, height - 1);          // Right
        g2.drawLine(width - 1, height - 1, arc, height - 1);
        g2.draw(area);

        g2.setStroke(new BasicStroke(
                1.5f, // Line thickness
                BasicStroke.CAP_BUTT, // Cap style
                BasicStroke.JOIN_MITER, // Join style
                1f, // Miter limit
                new float[]{2f, 4f}, // Dash pattern: 2px line, 4px space
                0f // Dash phase
        ));
        g2.drawLine(width - 1, 0, width - 1, height - 1);

        g2.dispose();
    }

    // === For quick test ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ticket2 Left Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 250);
            frame.setLayout(new FlowLayout());
            frame.add(new Ticket2LeftPanel("2025", "05/03", "14:30", "已使用", new Color(245, 245, 245), 30));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
