package Main.Movie;

import Main.help.HoverButtonPanel;
import MovieBooking.ShowtimeChoosePage;
import Data.Movie;
import Data.Order;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;

public class MovieCardPanel extends JPanel {

    private final JLabel posterLabel;
    private final JLabel titleLabel;
    private final JLabel dateLabel;
    private final Movie movie;

    public MovieCardPanel(Movie movie) {
        this.movie = movie;

        setPreferredSize(new Dimension(Const.MOVIE_CARD_WIDTH, Const.MOVIE_CARD_HEIGHT + 60));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // === Poster
        posterLabel = new JLabel();
        posterLabel.setAlignmentX(CENTER_ALIGNMENT);
        setPosterImage(movie);
        add(posterLabel);

        // === Title
        titleLabel = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        add(titleLabel);

        // === Release Date
        String dateText;
        if (movie.getReleaseDate() != null && movie.getReleaseDate().after(new java.util.Date())) {
            dateText = "上映日期: " + movie.getReleaseDate();
        } else {
            dateText = "下檔日期: " + movie.getRemovalDate();
        }
        dateLabel = new JLabel(dateText);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(120, 120, 120));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(dateLabel);

        // === Button Container
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        HoverButtonPanel introPanel = new HoverButtonPanel("電影介紹", new Color(157, 170, 179), new Color(135, 148, 158));
        HoverButtonPanel bookPanel = new HoverButtonPanel("線上訂票", new Color(180, 142, 135), new Color(159, 120, 112));

        introPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(introPanel);
                frame.dispose();
                new MovieInfoPage(movie);
            }
        });

        bookPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bookPanel);
                frame.dispose();
                Order order = new Order();
                order.setMovie(movie);
                new ShowtimeChoosePage(order);
            }
        });

        buttonPanel.add(introPanel);
        buttonPanel.add(bookPanel);
        add(buttonPanel);
    }

    private void setPosterImage(Movie movie) {
        int targetWidth = Const.MOVIE_CARD_WIDTH;
        int targetHeight = Const.MOVIE_CARD_WIDTH * 3 / 2;
        try {
            BufferedImage img = null;

            if (movie.getPosterBlob() != null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(movie.getPosterBlob());
                img = ImageIO.read(bais);
            } else if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
                File file = new File(movie.getPosterPath());
                if (file.exists()) {
                    img = ImageIO.read(file);
                }
            }

            if (img != null) {
                Image scaled = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                posterLabel.setIcon(new ImageIcon(scaled));
            } else {
                posterLabel.setText("No Image");
            }

            posterLabel.setPreferredSize(new Dimension(targetWidth, targetHeight));
        } catch (Exception e) {
            posterLabel.setText("No Image");
            e.printStackTrace();
        }
    }
}
