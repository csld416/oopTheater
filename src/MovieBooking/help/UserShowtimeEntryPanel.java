package MovieBooking.help;

import Data.Showtime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
        setOpaque(false); // allow transparent corners
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize(new Dimension(WIDTH, HEIGHT));

        // === Mouse Hover Effect ===
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentBgColor = hoverColor;
                setCursor(new Cursor(Cursor.HAND_CURSOR)); // 👈 hand cursor
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentBgColor = defaultColor;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 👈 default cursor
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickListener != null) {
                    clickListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });

        // Format elements
        SimpleDateFormat dateFmt = new SimpleDateFormat("M月d日 EEEE", Locale.TAIWAN);
        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm");

        String dateText = dateFmt.format(showtime.getStartTime());
        String startTime = timeFmt.format(showtime.getStartTime());
        String endTime = timeFmt.format(showtime.getEndTime());

        String theaterText = switch (showtime.getTheaterId()) {
            case 1 ->
                "A廳";
            case 2 ->
                "B廳";
            case 3 ->
                "C廳";
            default ->
                "未知廳";
        };
        String seatText = "剩98座位";

        int x = 30;
        int y = 15;

        JLabel dateLabel = new JLabel(dateText);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        dateLabel.setBounds(x, y, 200, 25);
        add(dateLabel);

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

        JLabel roomLabel = new JLabel(theaterText);
        roomLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        roomLabel.setBounds(x + 370, y + 3, 60, 30);
        add(roomLabel);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Showtime dummy = new Showtime(
                    1,
                    2,
                    3,
                    java.sql.Timestamp.valueOf("2025-05-16 21:30:00"),
                    java.sql.Timestamp.valueOf("2025-05-16 23:21:00"),
                    false
            );

            JFrame frame = new JFrame("User Showtime Entry Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(850, 200);
            frame.setLayout(new FlowLayout());
            frame.getContentPane().setBackground(new Color(245, 245, 245));

            UserShowtimeEntryPanel panel = new UserShowtimeEntryPanel(dummy);
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
