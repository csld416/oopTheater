package Main.TicketHelp;

import Data.Order;
import Data.Seat;
import global.CapsuleButton;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.stream.Collectors;

public class TicketPanel_1 extends JPanel {

    private final int ARC = 20;
    private final int WIDTH = 600;
    private final int HEIGHT = 180;

    public TicketPanel_1(Order order) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(false);
        setLayout(null);

        // === Poster ===
        ImageIcon posterIcon = new ImageIcon(order.getMovie().getPosterPath());
        Image scaled = posterIcon.getImage().getScaledInstance(85, 135, Image.SCALE_SMOOTH);
        JLabel posterLabel = new JLabel(new ImageIcon(scaled));
        posterLabel.setBounds(18, 20, 90, 135);
        add(posterLabel);

        int textX = 120;
        int y = 15;

        JLabel theaterLabel = new JLabel(order.getShowtime().getTheaterName());
        theaterLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        theaterLabel.setBounds(textX, y, 200, 20);
        add(theaterLabel);
        y += 20;

        JLabel titleLabel = new JLabel(order.getMovie().getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBounds(textX, y, 200, 25);
        add(titleLabel);
        y += 30;

        JLabel info1 = new JLabel("影廳：" + order.getShowtime().getTheaterName());
        info1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info1.setBounds(textX, y, 200, 20);
        add(info1);
        y += 20;

        JLabel info2 = new JLabel("場次：" + order.getShowtime().getStartTime().toString());
        info2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info2.setBounds(textX, y, 250, 20);
        add(info2);
        y += 20;

        JLabel info3 = new JLabel("票券：全票 x " + order.getSeatList().size());
        info3.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info3.setBounds(textX, y, 250, 20);
        add(info3);
        y += 20;

        JLabel info4 = new JLabel("座位：" + order.getSeatList().stream()
                .map(Seat::getLabel)
                .collect(Collectors.joining(" ")));
        info4.setFont(new Font("SansSerif", Font.PLAIN, 12));
        info4.setBounds(textX, y, 250, 20);
        add(info4);

        // === Price ===
        JLabel priceLabel = new JLabel("NT$" + order.getTotalCost());
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setForeground(Color.DARK_GRAY);
        priceLabel.setBounds(WIDTH - 80, 10, 80, 20);
        add(priceLabel);

        // === Refund Button ===
        CapsuleButton refundBtn = new CapsuleButton("退票", new Color(255, 128, 84), new Color(255, 102, 51), new Dimension(60, 30));
        refundBtn.setBounds(WIDTH - 80, HEIGHT - 50, 60, 30);
        add(refundBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape round = new RoundRectangle2D.Float(0, 0, WIDTH, HEIGHT, ARC, ARC);
        g2d.fill(round);
        g2d.setColor(new Color(230, 230, 230));
        g2d.draw(round);
        g2d.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ticket Card Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 250);
            frame.setLayout(new FlowLayout());
            frame.add(new TicketPanel_1(Data.Order.dummyOrder));  // Use dummy order
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}