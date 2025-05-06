package MovieBooking.foodPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FoodEntry extends JPanel {

    private int quantity = 0;
    private final JButton minusButton;
    private final JLabel quantityLabel;
    private final JButton plusButton;

    public FoodEntry(ImageIcon icon, String name, String priceText) {
        setPreferredSize(new Dimension(400, 80));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setLayout(null);

        // === Image ===
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(10, 10, 60, 60);
        add(imageLabel);

        // === Name ===
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setBounds(80, 15, 200, 20);
        add(nameLabel);

        // === Price ===
        JLabel priceLabel = new JLabel(priceText);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setForeground(new Color(204, 102, 0)); // Orange
        priceLabel.setBounds(80, 40, 200, 20);
        add(priceLabel);

        // === Quantity Control ===
        int controlWidth = 100;
        int controlHeight = 30;
        int x = getPreferredSize().width - controlWidth - 10;
        int y = (getPreferredSize().height - controlHeight) / 2;

        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        controlPanel.setBounds(x, y, controlWidth, controlHeight);
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        minusButton = new JButton("-");
        quantityLabel = new JLabel(String.valueOf(quantity), SwingConstants.CENTER);
        plusButton = new JButton("+");

        setupButton(minusButton);
        setupButton(plusButton);
        minusButton.setEnabled(false); // Initial state

        minusButton.addActionListener(e -> {
            if (quantity > 0) {
                quantity--;
                updateQuantityDisplay();
            }
        });

        plusButton.addActionListener(e -> {
            quantity++;
            updateQuantityDisplay();
        });

        controlPanel.add(minusButton);
        controlPanel.add(quantityLabel);
        controlPanel.add(plusButton);
        add(controlPanel);
    }

    private void setupButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) button.setBackground(new Color(240, 240, 240));
                button.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
                button.repaint();
            }
        });
    }

    private void updateQuantityDisplay() {
        quantityLabel.setText(String.valueOf(quantity));
        minusButton.setEnabled(quantity > 0);
    }

    // === Main for Visualization ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Food Entry Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ImageIcon icon = new ImageIcon("src/icons/profile-icon.jpg");
            FoodEntry entry = new FoodEntry(icon, "(APP用戶)小杯碳酸飲料", "NT$65");

            frame.getContentPane().add(entry);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}