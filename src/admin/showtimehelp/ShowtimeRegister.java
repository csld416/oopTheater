package admin.showtimehelp;

import global.CapsuleButton;
import global.Movie;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class ShowtimeRegister extends JPanel {

    public ShowtimeRegister(Movie movie) {
        setLayout(null);
        setBackground(UIConstants.COLOR_MAIN_LIGHT);
        setBounds(0, 0, UIConstants.RIGHT_PANEL_WIDTH, UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT);

        int centerX = UIConstants.RIGHT_PANEL_WIDTH / 2;
        
        int y = 80;

        // === Title ===
        JLabel titleLabel = new JLabel("新增場次", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(0, y, UIConstants.RIGHT_PANEL_WIDTH, 30);
        add(titleLabel);

        // === Placeholder Fields ===
        int labelWidth = 80;
        int fieldWidth = 300;
        int leftX = centerX - fieldWidth / 2;

        y += 90;
        JLabel roomLabel = new JLabel("放映廳:");
        roomLabel.setBounds(leftX - labelWidth, y, labelWidth, 30);
        roomLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(roomLabel);

        JComboBox<String> roomSelect = new JComboBox<>(new String[]{"Room 1", "Room 2", "Room 3"});
        roomSelect.setBounds(leftX, y, fieldWidth, 30);
        add(roomSelect);

        y += 60;
        JLabel startLabel = new JLabel("開始時間:");
        startLabel.setBounds(leftX - labelWidth, y, labelWidth, 30);
        startLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(startLabel);

        JTextField startField = new JTextField("2025-04-30 14:30");
        startField.setBounds(leftX, y, fieldWidth, 30);
        add(startField);

        y += 100;
        // === Buttons ===
        CapsuleButton cancelButton = new CapsuleButton("取消", new Color(180, 180, 180), new Color(140, 140, 140), new Dimension(100, 40), 16);
        cancelButton.setBounds(centerX - 130, y, 100, 40);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                Container parent = ShowtimeRegister.this.getParent();
                parent.remove(ShowtimeRegister.this);
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
                // TODO: Validate and insert showtime
                System.out.println("Creating new showtime...");
                Container parent = ShowtimeRegister.this.getParent();
                parent.remove(ShowtimeRegister.this);
                ShowtimeListPanel listPanel = new ShowtimeListPanel(movie);
                listPanel.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                parent.add(listPanel);
                parent.revalidate();
                parent.repaint();
            }
        });
        add(confirmButton);
    }
}