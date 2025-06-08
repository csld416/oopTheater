package admin.showtimehelp;

import connection.DatabaseConnection;
import global.CapsuleButton;
import Data.Movie;
import Data.Showtime;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowtimeListPanel extends JPanel {

    private final Movie movie;
    private final JPanel listContainer;
    private static final int GAP = 15;

    private final Color NEW_COLOR = new Color(157, 175, 158);
    private final Color NEW_COLOR_HOVOR = new Color(137, 154, 138);

    private final int ADD_X = Const.RIGHT_PANEL_WIDTH - 180;
    private final int ADD_Y = 30;

    public ShowtimeListPanel(Movie movie) {
        this.movie = movie;
        setLayout(null);
        setBackground(Color.WHITE);

        int panelWidth = Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH;
        int panelHeight = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;

        JPanel container = new JPanel(null);
        container.setBounds(0, 0, panelWidth, panelHeight);
        container.setBackground(new Color(250, 245, 240));
        add(container);

        JLabel movieLabel = new JLabel("🎬 當前電影: " + movie.getTitle());
        movieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movieLabel.setBounds(30, 20, 400, 25);
        container.add(movieLabel);

        JLabel dateLabel = new JLabel("📅 上映: " + movie.getReleaseDate() + "　⛔ 下檔: " + movie.getRemovalDate());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setBounds(30, 45, 600, 20);
        container.add(dateLabel);

        CapsuleButton addButton = new CapsuleButton("新增場次",
                NEW_COLOR, NEW_COLOR_HOVOR, new Dimension(130, 40), 16);
        addButton.setBounds(ADD_X, ADD_Y, 130, 40);
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Container parent = ShowtimeListPanel.this.getParent();
                if (parent != null) {
                    parent.removeAll();
                    ShowtimeRegisterPanel register = new ShowtimeRegisterPanel(movie);
                    register.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                    parent.add(register);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
        container.add(addButton);

        listContainer = new JPanel(null);
        listContainer.setBackground(new Color(250, 245, 240));
        listContainer.setBounds(0, 90, panelWidth, panelHeight - 70);
        container.add(listContainer);

        loadShowtimes();
    }

    private void loadShowtimes() {
        listContainer.removeAll();

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT s.id, s.movies_id, s.theater_id, s.start_time, s.end_time, s.is_canceled, t.name "
                    + "FROM Showtimes s JOIN Theaters t ON s.theater_id = t.id "
                    + "WHERE s.movies_id = ? AND s.is_canceled = 0 ORDER BY s.start_time ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            ResultSet rs = stmt.executeQuery();

            int y = 0;
            while (rs.next()) {
                Showtime showtime = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movies_id"),
                        rs.getInt("theater_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getBoolean("is_canceled")
                );

                ShowtimeEntryPanel entry = new ShowtimeEntryPanel(showtime);
                entry.setBounds(30, y, Const.ENTRY_WIDTH, Const.ENTRY_HEIGHT);
                listContainer.add(entry);
                y += Const.ENTRY_HEIGHT + GAP;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // === Optional: inject dummyShowtime into the cache if needed ===
            Showtime s = Showtime.dummyShowtime;
            Showtime.getAllShowtimes().add(s); // this won't persist, but allows testing

            // === Create dummy JFrame ===
            JFrame frame = new JFrame("Showtime List - Dummy Test");
            frame.setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // === Use dummy movie for testing ===
            ShowtimeListPanel panel = new ShowtimeListPanel(Movie.dummyMovie);
            frame.add(panel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
