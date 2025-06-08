package admin;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PieChartPanel extends JPanel {

    private final Map<String, Integer> data;

    public PieChartPanel(Map<String, Integer> data) {
        this.data = data;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        int total = data.values().stream().mapToInt(Integer::intValue).sum();
        int x = getWidth() / 2 - 150;
        int y = getHeight() / 2 - 150;
        int diameter = 300;

        int startAngle = 0;
        int i = 0;
        Color[] palette = {new Color(100,149,237), new Color(255,182,193), new Color(144,238,144),
                           new Color(255,160,122), new Color(221,160,221), new Color(255,215,0)};

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int angle = (int) Math.round(360.0 * entry.getValue() / total);
            g2.setColor(palette[i % palette.length]);
            g2.fillArc(x, y, diameter, diameter, startAngle, angle);
            startAngle += angle;
            i++;
        }

        // Draw labels
        int labelY = y + diameter + 30;
        i = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            g2.setColor(palette[i % palette.length]);
            g2.fillRect(x + 10, labelY + i * 25, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey() + " - $" + entry.getValue(), x + 30, labelY + 12 + i * 25);
            i++;
        }

        g2.dispose();
    }
}