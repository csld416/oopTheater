package MovieBooking.help;

import Data.Showtime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Locale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import connection.DatabaseConnection;

public class UserShowtimeEntryPanel extends JPanel {

    private final Showtime showtime;

    private final int WIDTH = 700;
    private final int HEIGHT = 70;
    private final int ARC = 20;

    private final Color defaultColor = Color.WHITE;
    private final Color hoverColor = new Color(237, 236, 235);
    private Color currentBgColor = defaultColor;

    private ActionListener clickListener;

    public UserShowtimeEntryPanel(Showtime showtime) {
        this.showtime = showtime;
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        // === Mouse Hover Effect ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentBgColor = hoverColor;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentBgColor = defaultColor;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                repaint();
            }
        });

        // Format times
        SimpleDateFormat dateFmt = new SimpleDateFormat("M月d日 EEEE", Locale.TAIWAN);
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");

        String dateText = dateFmt.format(showtime.getStartTime());
        String startTime = timeFmt.format(showtime.getStartTime());
        String endTime = timeFmt.format(showtime.getEndTime());

        // === Info Strings ===
        String theaterText = showtime.getTheaterName() != null ? showtime.getTheaterName() : "未知廳";
        String seatText = "剩" + getRemainingSeats(showtime) + "座位";

        int x = 30;
        int y = 15;

        // === Date Label ===
        JLabel dateLabel = new JLabel(dateText);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        dateLabel.setBounds(x, y, 200, 25);
        add(dateLabel);

        // === Time Panel ===
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setBackground(currentBgColor);
        timePanel.setOpaque(false);
        timePanel.setBounds(x + 200, y - 2, 90, 50);

        JLabel startLabel = new JLabel(startTime);
        startLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel endLabel = new JLabel(endTime);
        endLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        endLabel.setForeground(Color.GRAY);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timePanel.add(startLabel);
        timePanel.add(endLabel);
        add(timePanel);

        // === Room Label ===
        JLabel roomLabel = new JLabel(theaterText);
        roomLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        roomLabel.setBounds(x + 370, y + 3, 60, 30);
        add(roomLabel);

        // === Seat Label ===
        JLabel seatLabel = new JLabel(seatText);
        seatLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        seatLabel.setBounds(x + 450, y + 3, 100, 30);
        add(seatLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC);
        g2.setClip(rounded);
        g2.setColor(currentBgColor);
        g2.fill(rounded);

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        Shape rounded = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC);
        return rounded.contains(x, y);
    }

    public void setClickListener(ActionListener listener) {
        this.clickListener = listener;
    }

    // === Dummy seat fetch ===
    private int getRemainingSeats(Showtime s) {
        int totalSeats = s.getTheaterTypeIsBig() ? 407 : 144;
        int booked = 0;

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = """
                SELECT COUNT(*)
                FROM BookedSeat bs
                JOIN Tickets t ON bs.ticket_id = t.id
                WHERE bs.showtime_id = ?
                  AND t.status = 1
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, s.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                booked = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // show error state
        }

        return Math.max(0, totalSeats - booked);
    }
}
