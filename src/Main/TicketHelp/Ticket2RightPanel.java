package Main.TicketHelp;

import Data.Order;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.stream.Collectors;

public class Ticket2RightPanel extends JPanel {

    private final int width;
    private final int height;
    private final int arc;
    private final Order order;

    public Ticket2RightPanel(Order order, int width, int height, int arc) {
        this.order = order;
        this.width = width;
        this.height = height;
        this.arc = arc;

        setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);

        initContent();
    }

    private void initContent() {
        // === Poster ===
        ImageIcon rawPoster = new ImageIcon(order.getMovie().getPosterPath());
        Image scaledPoster = rawPoster.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        JLabel poster = new JLabel(new ImageIcon(scaledPoster));
        poster.setBounds(20, 30, 80, 120);
        add(poster);

        // === Text Info ===
        int xText = 120;
        int yText = 30;

        JLabel title = new JLabel("🎬 " + order.getMovie().getTitle());
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setBounds(xText, yText, 400, 20);
        add(title);
        yText += 30;

        JLabel theater = new JLabel("影廳：" + order.getShowtime().getTheaterName());
        theater.setFont(new Font("SansSerif", Font.PLAIN, 14));
        theater.setBounds(xText, yText, 300, 20);
        add(theater);
        yText += 25;

        JLabel rating = new JLabel("分級：" + order.getMovie().getRating());
        rating.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rating.setBounds(xText, yText, 200, 20);
        add(rating);
        yText += 25;

        JLabel ticket = new JLabel("票券：全票 x " + order.getSeatList().size());
        ticket.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ticket.setBounds(xText, yText, 200, 20);
        add(ticket);
        yText += 25;

        String seatStr = order.getSeatList().stream()
                .map(seat -> seat.getLabel())
                .collect(Collectors.joining(" "));
        JLabel seats = new JLabel("座位：" + seatStr);
        seats.setFont(new Font("SansSerif", Font.PLAIN, 14));
        seats.setBounds(xText, yText, width - xText - 20, 20);
        add(seats);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Area area = new Area(new Rectangle2D.Double(0, 0, width, height));

        // Subtract top-right and bottom-right
        area.subtract(new Area(new Ellipse2D.Double(width - arc, -arc, arc * 2, arc * 2)));
        area.subtract(new Area(new Ellipse2D.Double(width - arc, height - arc, arc * 2, arc * 2)));

        g2.setColor(Color.WHITE);
        g2.fill(area);

        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1.5f));
        // Top
        g2.drawLine(0, 0, width - arc, 0);
        // Left
        //g2.drawLine(0, 0, 0, height);
        // Bottom
        g2.drawLine(0, height - 1, width - arc, height - 1);
        // Right vertical (between curves)
        g2.drawLine(width - 1, arc, width - 1, height - arc);
        //Top-right curve
        g2.draw(new Arc2D.Double(width - arc, -arc, arc * 2, arc * 2, 180, 90, Arc2D.OPEN));
        //Bottom-right curve
        g2.draw(new Arc2D.Double(width - arc, height - arc, arc * 2, arc * 2, 180, -90, Arc2D.OPEN));
        g2.dispose();
    }

    // === Demo ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Ticket Right Preview");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(700, 300);
            f.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 40));

            Ticket2RightPanel right = new Ticket2RightPanel(Data.Order.dummyOrder, 500, 180, 30);
            f.add(right);

            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
