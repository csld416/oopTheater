package form;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MovieCardPanel extends JPanel {

    private JLabel posterLabel;
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JButton introButton;
    private JButton bookButton;

    public MovieCardPanel(String title, String releaseDate, String imagePath) {
        setPreferredSize(new Dimension(200, 340));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // === Poster
        posterLabel = new JLabel();
        posterLabel.setAlignmentX(CENTER_ALIGNMENT);
        setPosterImage(imagePath);
        add(posterLabel);

        // === Title
        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Keep this for BoxLayout
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align within the label
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        add(titleLabel);

        // === Date
        dateLabel = new JLabel("Release Date: " + releaseDate);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(120, 120, 120));
        dateLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(dateLabel);

        // === Button container
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        introButton = createStyledButton("電影介紹", new Color(255, 153, 51));
        bookButton = createStyledButton("線上訂票", new Color(0, 120, 215));

        buttonPanel.add(introButton);
        buttonPanel.add(bookButton);
        add(buttonPanel);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        return btn;
    }

    private void setPosterImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            int targetWidth = 180;
            int targetHeight = 240;
            Image scaled = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaled));
            posterLabel.setPreferredSize(new Dimension(targetWidth, targetHeight));
        } catch (Exception e) {
            posterLabel.setText("No Image");
        }
    }
}
