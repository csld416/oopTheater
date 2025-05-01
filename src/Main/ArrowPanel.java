package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ArrowPanel extends JPanel {

    private final String symbol;
    private final Color baseColor = new Color(180, 180, 180);
    private final Color hoverColor = new Color(150, 150, 150);
    private boolean hovered = false;

    private boolean enabled = true;
    private final Color disabledColor = new Color(200, 200, 200);

    public ArrowPanel(String symbol) {
        this.symbol = symbol;
        int diameter = 50;
        setPreferredSize(new Dimension(diameter, diameter));
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    hovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    hovered = false;
                    repaint();
                }
            }
        });

    }

    public void setArrowEnabled(boolean flag) {
        this.enabled = flag;
        setCursor(flag ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enable smooth drawing
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw circular background
        g2.setColor(!enabled ? disabledColor : hovered ? hoverColor : baseColor);
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Draw arrow symbol
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(symbol)) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(symbol, x, y);

        g2.dispose();
    }
}
