/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author csld
 */
public class DashBoardForm {

    private JFrame frame;
    private JPanel titleBar;
    private JLabel minimizeLabel;
    private JLabel titleLabel;
    private JLabel closeLabel;
    private JPanel dashboardPanel;

    //=== dragging
    private boolean isdragging = false;
    private Point mouseoffset;

    public DashBoardForm() {
        //=== Frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(255, 204, 0)), 
                new EmptyBorder(0, 0, 0, 0)
        ));
        // Title Bar
        titleBar = new JPanel();
        titleBar.setLayout(null);
        titleBar.setBackground(Color.DARK_GRAY);
        titleBar.setPreferredSize(new Dimension(frame.getWidth(), 30));
        frame.add(titleBar, BorderLayout.NORTH);
        //=== Title Label
        titleLabel = new JLabel("DashBoard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 200, 30);
        titleBar.add(titleLabel);
        //=== Close Label
        closeLabel = new JLabel("X");
        closeLabel.setForeground(Color.WHITE);
        closeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        closeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.setBounds(frame.getWidth() - 50, 0, 30, 30);

        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeLabel.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeLabel.setForeground(Color.WHITE);

            }
        });
        titleBar.add(closeLabel);
        //===
        minimizeLabel = new JLabel("-");
        minimizeLabel.setForeground(Color.WHITE);
        minimizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        minimizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        minimizeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeLabel.setBounds(frame.getWidth() - 90, 0, 30, 30);

        minimizeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minimizeLabel.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeLabel.setForeground(Color.WHITE);
            }
        });
        titleBar.add(minimizeLabel);
        //===
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        dashboardPanel.setBackground(new Color(240, 240, 240));
        //dashboardPanel.setBorder(new LineBorder(new Color(255, 204, 0), 1));
        frame.add(dashboardPanel, BorderLayout.CENTER);
        //=== add data
        addDataPanel("Sales", "$500k");
        addDataPanel("Expenses", "$350k");
        addDataPanel("Profit", "$150k");
        addDataPanel("Customers", "$1,000");

        //=== Draw Chart
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //draw chart
                drawChart(g, getHeight());
            }
        };
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(740, 300));
        chartPanel.setBackground(Color.WHITE);
        dashboardPanel.add(chartPanel);
        //=== Enable Chart Title
        JLabel chartTitleLabel = new JLabel("Orders");
        chartTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chartTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chartTitleLabel.setOpaque(true);
        chartTitleLabel.setBackground(new Color(150, 50, 50));
        chartTitleLabel.setForeground(Color.WHITE);
        chartPanel.add(chartTitleLabel, BorderLayout.NORTH);
        //=== Title Bar
        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isdragging = true;
                mouseoffset = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isdragging = false;
            }
        });
        titleBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isdragging) {
                    Point newLocation = e.getLocationOnScreen();
                    newLocation.translate(-mouseoffset.x, -mouseoffset.y);
                    frame.setLocation(newLocation);
                }
            }
        });

        //=== Init
        frame.setVisible(true);
    }

    //=== A method to add data to the panel
    private void addDataPanel(String title, String value) {
        JPanel dataPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawDataPanel(g, title, value, getWidth(), getHeight());
            }
        };
        dataPanel.setLayout(new GridLayout(2, 1));
        dataPanel.setPreferredSize(new Dimension(170, 100));
        dataPanel.setBackground(new Color(255, 255, 255));
        dataPanel.setBorder(new LineBorder(new Color(255, 204, 0), 5));
        dashboardPanel.add(dataPanel);
    }

    // Create a custom method to draw the panel
    private void drawDataPanel(Graphics g, String title, String value, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;

        //Customize the data panel appearance
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRoundRect(0, 0, width, height, 20, 20);

        //Stylish the background color for Data Panel Title
        g2d.setColor(new Color(150, 50, 50));
        g2d.fillRect(0, 0, width, 40);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString(title, 20, 30);

        // value
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(value, 20, 75);
    }

    // create a custom function to draw a chart
    private void drawChart(Graphics g, int height) {
        Graphics2D g2d = (Graphics2D) g;

        // the chart appearance
        int barWidth = 60;
        int barSpacing = 55;
        int startX = 50;
        int startY = height - 80;

        //Sample data values
        int[] data = {100, 200, 150, 300, 250, 350};

        //maximum data value for scaling
        int maxValue = 0;
        for (int value : data) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        //set colors for bars and labels
        Color barColor = new Color(76, 175, 80);
        Color labelColor = Color.BLACK;

        //Draw the bars and the labels
        for (int i = 0; i < data.length; i++) {
            int barHeight = (int)((double)data[i] / maxValue * (startY - 60));
            int x = startX + (barWidth + barSpacing) * i;
            int y = startY - barHeight;
            g2d.setColor(barColor);
            g2d.fillRect(x, y, barWidth, barHeight);

            g2d.setColor(labelColor);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(String.valueOf(data[i]), x + 10, y - 10);
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.drawString("Product " + (i + 1), x + 5, startY + 20);
        }
    }

    public static void main(String args[]) {
        new DashBoardForm();
    }
}

// Create custom class for rounded corners
class RoundedBorder implements Border{
    
    private int radius;
    private Color color;
    
    public RoundedBorder(int radius, Color color){
        this.color = color;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(color);
        g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);  
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
}