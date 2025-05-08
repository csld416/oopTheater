package admin.FoodRegisterHelp;

import Data.Food;
import global.CapsuleButton;
import global.ToggleButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FoodEntry extends JPanel {

    private int x = 0; // used for dynamic layout

    public FoodEntry(Food food) {
        setPreferredSize(new Dimension(500, 80));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setLayout(null);

        // === Image ===
        x = 10;
        ImageIcon rawIcon = new ImageIcon(food.getImagePath());
        Image scaledImg = rawIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
        imageLabel.setBounds(x, 10, 60, 60);
        add(imageLabel);

        // === Name ===
        x += 70;
        JLabel nameLabel = new JLabel(food.getName());
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setBounds(x, 15, 200, 20);
        add(nameLabel);

        // === Price ===
        JLabel priceLabel = new JLabel("NT$" + food.getPrice());
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setForeground(new Color(204, 102, 0)); // Orange
        priceLabel.setBounds(x, 40, 200, 20);
        add(priceLabel);

        // === Right Section ===
        x = 290; // reset x for right control group

        // Toggle Button
        ToggleButtonPanel toggle = new ToggleButtonPanel(food.isValid());
        toggle.setBounds(x, 25, 50, 30);
        add(toggle);

        // 修改 Button
        x += 60;
        CapsuleButton editButton = new CapsuleButton("修改",
                new Color(70, 130, 180), new Color(100, 149, 237),
                new Dimension(60, 30), 14);
        editButton.setBounds(x, 25, 60, 30);
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "修改功能尚未實作");
            }
        });
        add(editButton);

        // 刪除 Button
        x += 70;
        CapsuleButton deleteButton = new CapsuleButton("刪除",
                new Color(220, 20, 60), new Color(255, 69, 0),
                new Dimension(60, 30), 14);
        deleteButton.setBounds(x, 25, 60, 30);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "刪除功能尚未實作");
            }
        });
        add(deleteButton);
    }

    // === Main for Demo ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Food Entry Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Food demoFood = new Food("src/icons/profile-icon.jpg", 65, "(APP用戶)小杯碳酸飲料", true, 0);
            FoodEntry entry = new FoodEntry(demoFood);

            frame.getContentPane().add(entry);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}