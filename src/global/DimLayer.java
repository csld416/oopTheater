/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package global;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author csld
 */
public class DimLayer extends JPanel {

    public DimLayer(JFrame frame) {
        setOpaque(false);  // Crucial for transparency
        setLayout(null);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component clicked = SwingUtilities.getDeepestComponentAt(DimLayer.this, e.getX(), e.getY());

                if (clicked == DimLayer.this) {
                    // Remove the toggle panel
                    frame.repaint();

                    // Hide the dim layer
                    DimLayer.this.setVisible(false);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set a translucent gray overlay
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0, 0, 0, 100));  // RGBA, alpha=100 is light dim
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}
