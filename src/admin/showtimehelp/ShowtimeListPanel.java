package admin.showtimehelp;

import connection.DatabaseConnection;
import global.CapsuleButton;
import global.Movie;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeListPanel extends JPanel {

    private final Movie movie;
    private final JPanel listContainer;

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
        movieLabel.setBounds(30, 20, 400, 25);
        container.add(movieLabel);

        CapsuleButton addButton = new CapsuleButton("新增場次",
                new Color(55, 133, 128), new Color(70, 160, 155), new Dimension(130, 40));
        addButton.setBounds(panelWidth - 160, 15, 130, 40);
        container.add(addButton);

        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(new Color(250, 245, 240));

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBounds(30, 70, panelWidth - 60, panelHeight - 100);
        scrollPane.setBorder(null);
        container.add(scrollPane);

        loadShowtimes();
    }

    private void loadShowtimes() {
        listContainer.removeAll();

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT s.id, t.room_num, s.start_time FROM Showtimes s " +
                         "JOIN Theaters t ON s.theater_id = t.id WHERE s.movies_id = ? AND s.is_canceled = 0 ORDER BY s.start_time ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movie.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int showtimeId = rs.getInt("id");
                String room = rs.getString("room_num");
                Timestamp startTime = rs.getTimestamp("start_time");

                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                row.setBackground(new Color(250, 245, 240));

                JLabel info = new JLabel("[ " + room + " | " + startTime.toString().substring(0, 16) + " ]");
                row.add(info);

                JButton editButton = new JButton("\uD83D\uDD8A\uFE0F修改");
                editButton.setFocusPainted(false);
                row.add(editButton);

                JButton deleteButton = new JButton("\uD83D\uDDD1\uFE0F刪除");
                deleteButton.setFocusPainted(false);
                row.add(deleteButton);

                listContainer.add(row);
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
