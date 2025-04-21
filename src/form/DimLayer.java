/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author csld
 */
public class DimLayer extends JPanel {
    public DimLayer() {
        setOpaque(false);  // Crucial for transparency
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


