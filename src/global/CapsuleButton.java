package global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CapsuleButton extends JPanel {

    private String text;
    private Color defaultColor;
    private Color hoverColor;
    private boolean isHovered = false;

    public CapsuleButton(String text, Color defaultColor, Color hoverColor, Dimension preferredSize) {
        this.text = text;
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;

        setOpaque(false);
        setPreferredSize(preferredSize);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int arc = height;

        // Background Color (change if hover)
        g2.setColor(isHovered ? hoverColor : defaultColor);
        g2.fillRoundRect(0, 0, width, height, arc, arc);

        // Draw Text
        g2.setColor(Color.WHITE); // Text color (fixed as white here, can make adjustable later)
        Font font = new Font("SansSerif", Font.BOLD, 18);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = (width - textWidth) / 2;
        int textY = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(text, textX, textY);

        g2.dispose();
    }
}
