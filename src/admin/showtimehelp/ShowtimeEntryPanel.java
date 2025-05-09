package admin.showtimehelp;

import global.CapsuleButton;
import Data.Showtime;
import Data.Theater;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class ShowtimeEntryPanel extends JPanel {

    private final Showtime showtime;
    private final Color BORDER_COLOR = new Color(200, 200, 200);
    private final Color BG_COLOR = Color.WHITE;
    private final int ARC_RADIUS = 30;

    private final int WIDTH = Const.ENTRY_WIDTH;
    private final int HEIGHT = Const.ENTRY_HEIGHT;

    private final Dimension d = new Dimension(45, 20);

    private final Color EDITColor = new Color(166, 161, 178);
    private final Color EDITColor_hover = new Color(146, 140, 160);
    private final Color DELETEColor = new Color(198, 143, 124);
    private final Color DELETEColor_hover = new Color(178, 123, 104);

    private final int sz = 16;

    public ShowtimeEntryPanel(Showtime showtime) {
        this.showtime = showtime;
        setLayout(null);
        setBackground(BG_COLOR);
        setOpaque(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        int x = 70;
        int y = 15;

        // Room label
        String s = "";
        int theaterId = showtime.getTheaterId();
        for (Theater t : Theater.fetchTheaterList()) {
            if (t.getId() == theaterId) {
                s = t.getName();
            }
        }
        JLabel roomLabel = new JLabel("Room " + s);
        roomLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roomLabel.setBounds(x, y, 120, 30);
        add(roomLabel);

        x += 160;
        // Time label
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JLabel timeLabel = new JLabel(sdf.format(showtime.getStartTime()));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        timeLabel.setBounds(x, y, 200, 30);
        add(timeLabel);

        x += 200;
        // Edit button
        CapsuleButton editButton = new CapsuleButton("修改", EDITColor, EDITColor_hover, d, sz);
        editButton.setBounds(x, y, 80, 30);
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Edit Showtime: ID " + showtime.getId());
            }
        });
        add(editButton);

        x += 90;
        // Delete button
        CapsuleButton deleteButton = new CapsuleButton("刪除", DELETEColor, DELETEColor_hover, d, sz);
        deleteButton.setBounds(x, y, 80, 30);
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Delete Showtime: ID " + showtime.getId());
            }
        });
        add(deleteButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(BG_COLOR);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_RADIUS, ARC_RADIUS);

        g2.setColor(BORDER_COLOR);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC_RADIUS, ARC_RADIUS);

        g2.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Showtime Entry Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(650, 200);
            frame.setLayout(null);
            frame.getContentPane().setBackground(Const.COLOR_MAIN_LIGHT);

            Showtime s = new Showtime(
                    1,
                    2,
                    3,
                    java.sql.Timestamp.valueOf("2025-04-30 14:30:00"),
                    java.sql.Timestamp.valueOf("2025-04-30 16:30:00"),
                    false
            );

            ShowtimeEntryPanel entry = new ShowtimeEntryPanel(s);
            entry.setBounds(20, 30, entry.WIDTH, entry.HEIGHT);
            frame.add(entry);

            frame.setVisible(true);
        });
    }

}
