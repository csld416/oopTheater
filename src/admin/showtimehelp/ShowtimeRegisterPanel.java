package admin.showtimehelp;

import connection.DatabaseConnection;
import global.CapsuleButton;
import Data.Movie;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowtimeRegisterPanel extends JPanel {

    public ShowtimeRegisterPanel(Movie movie) {
        setLayout(null);
        setBackground(UIConstants.COLOR_MAIN_LIGHT);
        setBounds(0, 0, UIConstants.RIGHT_PANEL_WIDTH, UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT);

        int panelWidth = UIConstants.RIGHT_PANEL_WIDTH;
        int centerX = panelWidth / 2;
        int y = 80;

        int labelWidth = 80;
        int fieldWidth = 300;
        int xOffset = 20; // 👈 Adjust this if still feels off
        int leftX = centerX - fieldWidth / 2 + xOffset;

        // === Title ===
        JLabel titleLabel = new JLabel("新增場次", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(0, y, panelWidth, 30);
        add(titleLabel);

        // === 放映廳 ===
        y += 90;
        JLabel roomLabel = new JLabel("放映廳:");
        roomLabel.setBounds(leftX - labelWidth, y, labelWidth, 30);
        roomLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(roomLabel);

        // Ensure list is loaded
        if (Data.Theater.theaterList == null) {
            Data.Theater.fetchTheaterList();
        }

        // Extract names into array
        String[] theaterNames = Data.Theater.theaterList.stream()
                .map(Data.Theater::getName)
                .toArray(String[]::new);

        // Create combo box with real theater names
        JComboBox<String> roomSelect = new JComboBox<>(theaterNames);
        roomSelect.setBounds(leftX, y, fieldWidth, 30);
        add(roomSelect);

        // === 開始時間 ===
        y += 60;
        JLabel startLabel = new JLabel("開始時間:");
        startLabel.setBounds(leftX - labelWidth, y, labelWidth, 30);
        startLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(startLabel);

        JTextField startField = new JTextField("2025-04-30 14:30");
        startField.setBounds(leftX, y, fieldWidth, 30);
        add(startField);

        // === Buttons ===
        y += 100;
        CapsuleButton cancelButton = new CapsuleButton("取消", new Color(180, 180, 180), new Color(140, 140, 140), new Dimension(100, 40), 16);
        cancelButton.setBounds(centerX - 130, y, 100, 40);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Container parent = ShowtimeRegisterPanel.this.getParent();
                parent.remove(ShowtimeRegisterPanel.this);
                ShowtimeListPanel listPanel = new ShowtimeListPanel(movie);
                listPanel.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                parent.add(listPanel);
                parent.revalidate();
                parent.repaint();
            }
        });
        add(cancelButton);

        CapsuleButton confirmButton = new CapsuleButton("確認新增", new Color(76, 153, 133), new Color(60, 135, 110), new Dimension(130, 40), 16);
        confirmButton.setBounds(centerX + 30, y, 130, 40);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                String selectedRoom = roomSelect.getSelectedItem().toString();
                String startTimeText = startField.getText().trim();

                if (!isValid(startTimeText)) {
                    JOptionPane.showMessageDialog(ShowtimeRegisterPanel.this, "請輸入正確的時間格式：yyyy-MM-dd HH:mm", "格式錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = insertShowtime(movie, selectedRoom, startTimeText);
                if (!success) {
                    JOptionPane.showMessageDialog(ShowtimeRegisterPanel.this, "新增場次失敗，請重試或檢查資料庫", "錯誤", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // If successful, return to list
                Container parent = ShowtimeRegisterPanel.this.getParent();
                parent.remove(ShowtimeRegisterPanel.this);
                ShowtimeListPanel listPanel = new ShowtimeListPanel(movie);
                listPanel.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                parent.add(listPanel);
                parent.revalidate();
                parent.repaint();
            }
        });
        add(confirmButton);
    }

    private boolean isValid(String startTimeText) {
        if (startTimeText == null || !startTimeText.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "請輸入正確的時間格式 (yyyy-MM-dd HH:mm)", "錯誤", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean insertShowtime(Movie movie, String roomName, String startTimeText) {
        try {
            Connection conn = new DatabaseConnection().getConnection();

            // 1. Get theater_id from name
            String theaterSql = "SELECT id FROM Theaters WHERE name = ?";
            PreparedStatement theaterStmt = conn.prepareStatement(theaterSql);
            theaterStmt.setString(1, roomName);
            ResultSet rs = theaterStmt.executeQuery();

            int theaterId = -1;
            if (rs.next()) {
                theaterId = rs.getInt("id");
            } else {
                JOptionPane.showMessageDialog(this, "找不到對應的放映廳", "錯誤", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // 2. Compute endTime using movie duration
            java.sql.Timestamp startTime = java.sql.Timestamp.valueOf(startTimeText + ":00");
            long endMillis = startTime.getTime() + movie.getDuration() * 60 * 1000;
            java.sql.Timestamp endTime = new java.sql.Timestamp(endMillis);

            // 3. Insert showtime
            String insertSql = "INSERT INTO Showtimes (movies_id, theater_id, start_time, end_time, is_canceled) VALUES (?, ?, ?, ?, 0)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, movie.getId());
            insertStmt.setInt(2, theaterId);
            insertStmt.setTimestamp(3, startTime);
            insertStmt.setTimestamp(4, endTime);

            int affected = insertStmt.executeUpdate();
            if (affected > 0) {
                JOptionPane.showMessageDialog(this, "✅ 新增成功");
            } else {
                JOptionPane.showMessageDialog(this, "❌ 新增失敗", "錯誤", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            theaterStmt.close();
            insertStmt.close();
            conn.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ 發生錯誤: " + e.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}
