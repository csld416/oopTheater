package MainPage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import global.*;

public class MovieCardPanel extends JPanel {

    private JLabel posterLabel;
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JButton introButton;
    private JButton bookButton;

    public MovieCardPanel(String title, String releaseDate, String imagePath) {
        setPreferredSize(new Dimension(200, 400));
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
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        add(titleLabel);

        // === Date
        dateLabel = new JLabel("Release Date: " + releaseDate);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(120, 120, 120));
        dateLabel.setAlignmentX(CENTER_ALIGNMENT);
        add(dateLabel);

        // === Button container using custom Hover panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        HoverButtonPanel introPanel = new HoverButtonPanel("電影介紹", new Color(157, 170, 179), new Color(135, 148, 158));
        HoverButtonPanel bookPanel = new HoverButtonPanel("線上訂票", new Color(180, 142, 135), new Color(159, 120, 112));

        buttonPanel.add(introPanel);
        buttonPanel.add(bookPanel);
        add(buttonPanel);
    }

    private void setPosterImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            int targetWidth = UIConstants.MOVIE_CARD_WIDTH;
            int targetHeight = UIConstants.MOVIE_CARD_WIDTH * 3 / 2;
            Image scaled = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaled));
            posterLabel.setPreferredSize(new Dimension(targetWidth, targetHeight));
        } catch (Exception e) {
            posterLabel.setText("No Image");
        }
    }
}
