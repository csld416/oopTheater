package admin.showtimehelp;

import connection.DatabaseConnection;
import global.CapsuleButton;
import global.Movie;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ShowtimeFormPanel extends JPanel {

    private JComboBox<String> theaterSelect;
    private JTextField startTimeField;
    private CapsuleButton submitButton;
    private Movie movie;

    public ShowtimeFormPanel(Movie movie) {
        this.movie = movie;
        setLayout(null);
        setBackground(Color.WHITE);

        int panelWidth = UIConstants.FRAME_WIDTH - UIConstants.LEFT_PANEL_WIDTH;
        int panelHeight = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        JPanel form = new JPanel(null);
        form.setBounds(0, 0, panelWidth, panelHeight);
        form.setBackground(new Color(250, 245, 240));
        add(form);

        int fieldWidth = 200;
        int labelWidth = 80;
        int fieldHeight = 25;
        int totalWidth = labelWidth + 10 + fieldWidth;

        int centerX = (panelWidth - totalWidth) / 2;
        int labelX = centerX;
        int fieldX = centerX + labelWidth + 10;

        int y = 40;
        int gap = 50;

        JLabel movieLabel = new JLabel("🎬 當前電影: " + movie.getTitle());
        movieLabel.setBounds(centerX, y, 300, fieldHeight);
        form.add(movieLabel);

        y += gap;
        JLabel theaterLabel = new JLabel("放映廳:");
        theaterLabel.setBounds(labelX, y, labelWidth, fieldHeight);
        form.add(theaterLabel);

        theaterSelect = new JComboBox<>();
        theaterSelect.setBounds(fieldX, y, fieldWidth, fieldHeight);
        theaterSelect.addItem("Room A");
        theaterSelect.addItem("Room B");
        theaterSelect.addItem("Room C");
        form.add(theaterSelect);

        y += gap;
        JLabel startLabel = new JLabel("開始時間:");
        startLabel.setBounds(labelX, y, labelWidth, fieldHeight);
        form.add(startLabel);

        startTimeField = new JTextField("YYYY-MM-DD HH:MM");
        startTimeField.setBounds(fieldX, y, fieldWidth, fieldHeight);
        form.add(startTimeField);

        submitButton = new CapsuleButton("儲存場次",
                new Color(55, 133, 128),
                new Color(70, 160, 155),
                new Dimension(130, 40));
        submitButton.setBounds((panelWidth - 130) / 2, y + 60, 130, 40);
        form.add(submitButton);

        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                appendShowtime();
            }
        });
    }

    private void appendShowtime() {
        String room = (String) theaterSelect.getSelectedItem();
        String startStr = startTimeField.getText().trim();

        LocalDateTime startTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            startTime = LocalDateTime.parse(startStr, formatter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "請輸入正確的時間格式：yyyy-MM-dd HH:mm");
            return;
        }

        Movie selectedMovie = movie;
        int duration = selectedMovie.getDuration();
        LocalDateTime endTime = startTime.plusMinutes(duration);

        try {
            Connection conn = new DatabaseConnection().getConnection();

            // === Get theater_id from room name ===
            String roomQuery = "SELECT id FROM Theaters WHERE room_num = ?";
            PreparedStatement roomStmt = conn.prepareStatement(roomQuery);
            roomStmt.setString(1, room);
            ResultSet roomRs = roomStmt.executeQuery();
            if (!roomRs.next()) {
                JOptionPane.showMessageDialog(this, "找不到對應放映廳: " + room);
                return;
            }
            int theaterId = roomRs.getInt("id");
            roomRs.close();
            roomStmt.close();

            // === Check conflict ===
            String sql = "SELECT start_time, end_time FROM Showtimes WHERE theater_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, theaterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalDateTime existingStart = rs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime existingEnd = rs.getTimestamp("end_time").toLocalDateTime();

                boolean conflict = (startTime.isBefore(existingEnd) && endTime.isAfter(existingStart));
                if (conflict) {
                    JOptionPane.showMessageDialog(this,
                            "時間衝突：選擇的時段與其他電影重疊。請選擇其他時間。");
                    rs.close();
                    stmt.close();
                    conn.close();
                    return;
                }
            }

            rs.close();
            stmt.close();

            // === Insert ===
            String insertSql = """
            INSERT INTO Showtimes (movies_id, theater_id, start_time, end_time, is_canceled)
            VALUES (?, ?, ?, ?, false)
            """;
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, selectedMovie.getId());
            insertStmt.setInt(2, theaterId);
            insertStmt.setTimestamp(3, Timestamp.valueOf(startTime));
            insertStmt.setTimestamp(4, Timestamp.valueOf(endTime));
            insertStmt.executeUpdate();

            insertStmt.close();
            conn.close();
            JOptionPane.showMessageDialog(this, "場次新增成功！");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "資料庫錯誤：" + ex.getMessage());
        }
    }
}
