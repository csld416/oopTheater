package MovieBooking.foodPanels;

import Data.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FoodEntry extends JPanel {

    private final Food food;

    public interface QuantityChangeListener {

        void onQuantityChange(Food food, int newQuantity);
    }

    private QuantityChangeListener quantityChangeListener;

    private int quantity = 0;
    private final JLabel minusLabel;
    private final JLabel quantityLabel;
    private final JLabel plusLabel;
    private final Color color = new Color(228, 220, 216);
    private final int arcRadius = 20;

    private final JPanel controlPanel = new JPanel(null) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Rounded background
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            // Border
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

            // Vertical dividers
            g2.drawLine(30, 0, 30, getHeight());
            g2.drawLine(70, 0, 70, getHeight());

            g2.dispose();
        }
    };

    public FoodEntry(Food food) {
        this.food = food;
        setPreferredSize(new Dimension(400, 80));
        setMinimumSize(new Dimension(400, 80));
        setMaximumSize(new Dimension(400, 80));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(color);
        setOpaque(false);
        setLayout(null);

        // === Image ===
        // === Image ===
        String path = food.getImagePath();
        ImageIcon imageIcon;

        if (new java.io.File(path).exists()) {
            Image rawImage = new ImageIcon(path).getImage();
            Image scaled = rawImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaled);
        } else {
            System.out.println("[Warning] Image not found at: " + path);
            imageIcon = new ImageIcon(); // fallback to empty icon
        }

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20, 10, 60, 60);
        add(imageLabel);

        // === Name ===
        JLabel nameLabel = new JLabel(food.getName());
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setBounds(100, 15, 200, 20);
        add(nameLabel);

        // === Price ===
        JLabel priceLabel = new JLabel("NT$" + food.getPrice());
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setForeground(new Color(204, 102, 0)); // Orange
        priceLabel.setBounds(100, 40, 200, 20);
        add(priceLabel);

        // === Quantity Control ===
        int controlWidth = 100;
        int controlHeight = 30;
        int x = getPreferredSize().width - controlWidth - 20;
        int y = (getPreferredSize().height - controlHeight) / 2;

        controlPanel.setBounds(x, y, controlWidth, controlHeight);
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setOpaque(false);
        controlPanel.setLayout(null);

        // Minus Label
        minusLabel = createButtonLabel("-");
        minusLabel.setBounds(0, 0, 30, controlHeight);
        minusLabel.setOpaque(false);
        controlPanel.add(minusLabel);

        // Quantity Label
        quantityLabel = new JLabel(String.valueOf(quantity), SwingConstants.CENTER);
        quantityLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        quantityLabel.setBounds(30, 0, 40, controlHeight);
        quantityLabel.setOpaque(false);
        controlPanel.add(quantityLabel);

        // Plus Label
        plusLabel = createButtonLabel("+");
        plusLabel.setBounds(70, 0, 30, controlHeight);
        plusLabel.setOpaque(false);
        controlPanel.add(plusLabel);

        add(controlPanel);
        setupActions();
    }

    private JLabel createButtonLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(null);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }

    private void setupActions() {
        // Minus logic
        minusLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (quantity > 0) {
                    minusLabel.setBackground(new Color(240, 240, 240));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minusLabel.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (quantity > 0) {
                    quantity--;
                    updateQuantityDisplay();
                }
            }
        });

        // Plus logic
        plusLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                plusLabel.setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plusLabel.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                quantity++;
                updateQuantityDisplay();
            }
        });
    }

    public void setQuantityChangeListener(QuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    private void updateQuantityDisplay() {
        quantityLabel.setText(String.valueOf(quantity));
        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChange(food, quantity);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint children first

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(color);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcRadius, arcRadius);

        // Border
        g2.setColor(Color.LIGHT_GRAY);  // or any custom border color
        g2.setStroke(new BasicStroke(1));  // thickness of the border
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcRadius, arcRadius);

        g2.dispose();
    }

    // === Main for demo ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Food demoFood = new Food("src/icons/Foods/cold-coco.jpg", 90, "冰可可", true, 0);

            JFrame frame = new JFrame("Food Entry Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(450, 160);
            frame.setLayout(new FlowLayout());
            frame.add(new FoodEntry(demoFood));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
