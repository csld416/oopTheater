package admin.showtimehelp;

import connection.DatabaseConnection;
import global.CapsuleButton;
import global.DimLayer;
import global.Movie;
import global.Showtime;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class ShowtimeListPanel extends JPanel {

    private final Movie movie;
    private final JPanel listContainer;
    private static final int GAP = 20;

    private final Color NEW_COLOR = new Color(157, 175, 158);
    private final Color NEW_COLOR_HOVOR = new Color(137, 154, 138);

    private final int ADD_X = UIConstants.RIGHT_PANEL_WIDTH - 180;
    private final int ADD_Y = 30;

    public ShowtimeListPanel(Movie movie) {
        this.movie = movie;
        setLayout(null);
        setBackground(Color.WHITE);

        int panelWidth = UIConstants.FRAME_WIDTH - UIConstants.LEFT_PANEL_WIDTH;
        int panelHeight = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        JPanel container = new JPanel(null);
        container.setBounds(0, 0, panelWidth, panelHeight);
        container.setBackground(new Color(250, 245, 240));
        add(container);

        JLabel movieLabel = new JLabel("🎬 當前電影: " + movie.getTitle());
        movieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movieLabel.setBounds(30, 20, 400, 25);
        container.add(movieLabel);

        CapsuleButton addButton = new CapsuleButton("新增場次",
                NEW_COLOR, NEW_COLOR_HOVOR, new Dimension(130, 40), 16);
        addButton.setBounds(ADD_X, ADD_Y, 130, 40);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ShowtimeListPanel.this);
                Container parent = ShowtimeListPanel.this.getParent(); // usually the rightPanel

                if (parent != null) {
                    parent.removeAll();
                    ShowtimeRegister register = new ShowtimeRegister(movie);
                    register.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                    parent.add(register);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
        container.add(addButton);

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(new Color(250, 245, 240));

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBounds(30, 70, panelWidth - 60, panelHeight - 100);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        container.add(scrollPane);

        loadShowtimes();
    }

    private void loadShowtimes() {
        listContainer.removeAll();

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT s.id, s.movies_id, s.theater_id, s.start_time, s.end_time, s.is_canceled, t.room_num "
                    + "FROM Showtimes s JOIN Theaters t ON s.theater_id = t.id "
                    + "WHERE s.movies_id = ? AND s.is_canceled = 0 ORDER BY s.start_time ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Showtime showtime = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movies_id"),
                        rs.getInt("theater_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getBoolean("is_canceled")
                );

                JPanel slotPanel = new JPanel(null);
                slotPanel.setPreferredSize(new Dimension(UIConstants.ENTRY_WIDTH, UIConstants.ENTRY_HEIGHT));
                slotPanel.setBackground(new Color(250, 245, 240));

                ShowtimeEntryPanel entry = new ShowtimeEntryPanel(showtime);
                entry.setBounds(0, 0, UIConstants.ENTRY_WIDTH, UIConstants.ENTRY_HEIGHT);
                slotPanel.add(entry);

                listContainer.add(Box.createVerticalStrut(GAP));
                listContainer.add(slotPanel);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "讀取場次失敗: " + e.getMessage());
        }

        listContainer.revalidate();
        listContainer.repaint();
    }
}
