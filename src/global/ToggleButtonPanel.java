/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author csld
 */
public class ToggleButtonPanel extends JPanel {

    private boolean isOn = false;
    private final Color ON_COLOR = new Color(90, 184, 95);    // Green
    private final Color OFF_COLOR = new Color( 212, 107, 93);   // Red
    private final int WIDTH = 50;
    private final int HEIGHT = 30;
    private final int CIRCLE_DIAMETER = 24;

    public ToggleButtonPanel(boolean defaultState) {
        this.isOn = defaultState;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(false); // Transparent for custom painting
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isOn = !isOn;
                repaint();
            }
        });
    }

    public ToggleButtonPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(false); // Transparent for custom painting
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isOn = !isOn;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background color
        g2.setColor(isOn ? ON_COLOR : OFF_COLOR);
        g2.fillRoundRect(0, 0, WIDTH, HEIGHT, HEIGHT, HEIGHT);

        // Circle position
        int circleX = isOn ? WIDTH - CIRCLE_DIAMETER - 3 : 3;
        int circleY = (HEIGHT - CIRCLE_DIAMETER) / 2;

        // Circle
        g2.setColor(Color.WHITE);
        g2.fillOval(circleX, circleY, CIRCLE_DIAMETER, CIRCLE_DIAMETER);

        g2.dispose();
    }

    public boolean isToggled() {
        return isOn;
    }

    // Demo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ToggleButtonPanel Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 100);
            frame.setLayout(new FlowLayout());
            frame.add(new ToggleButtonPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
