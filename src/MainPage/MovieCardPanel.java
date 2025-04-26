package MainPage;

import Pages.MovieBookingPage;
import MovieOperation.MovieInfoPage;
import global.Movie;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MovieCardPanel extends JPanel {

    private final JLabel posterLabel;
    private final JLabel titleLabel;
    private final JLabel dateLabel;
    private final Movie movie;  // store the movie object

    public MovieCardPanel(Movie movie) {
        this.movie = movie;

        setPreferredSize(new Dimension(UIConstants.MOVIE_CARD_WIDTH, UIConstants.MOVIE_CARD_HEIGHT + 60));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // === Poster
        posterLabel = new JLabel();
        posterLabel.setAlignmentX(CENTER_ALIGNMENT);
        setPosterImage(movie.getPosterPath());
        add(posterLabel);

        // === Title
        titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        add(titleLabel);

        // === Release Date
        dateLabel = new JLabel("Release Date: " + movie.getReleaseDate());
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(120, 120, 120));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(dateLabel);

        // === Button Container using custom Hover panels
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        HoverButtonPanel introPanel = new HoverButtonPanel("電影介紹", new Color(157, 170, 179), new Color(135, 148, 158));
        HoverButtonPanel bookPanel = new HoverButtonPanel("線上訂票", new Color(180, 142, 135), new Color(159, 120, 112));

        // === Info Button (電影介紹)
        introPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(introPanel);
                frame.dispose();
                new MovieInfoPage(movie); // pass this movie to MovieInfoPage
            }
        });

        // === Book Button (線上訂票)
        bookPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bookPanel);
                frame.dispose();
                new MovieBookingPage(movie); // pass this movie to MovieBookingPage
            }
        });

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