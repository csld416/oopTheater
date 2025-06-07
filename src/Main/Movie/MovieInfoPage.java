package Main.Movie;

import GlobalConst.Const;
import Data.Movie;
import Main.TopBarPanel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class MovieInfoPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel contentPanel;
    private Movie movie;

    private final int POSTER_WIDTH = 300;
    private final int POSTER_HEIGHT = 450;

    public MovieInfoPage(Movie movie) {
        this.movie = movie;

        setTitle("Movie Info Page");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initTopBar();
        initContent();

        setVisible(true);
    }

    private void initTopBar() {
        topBarSlot = new JPanel(null);
        topBarSlot.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initContent() {
        int y = Const.TOP_BAR_HEIGHT;
        int height = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;

        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, y, Const.FRAME_WIDTH, height);
        contentPanel.setBackground(Const.COLOR_MAIN_LIGHT);

        // === Poster ===
        int posterX = 60;
        int posterY = (height - POSTER_HEIGHT) / 2;

        JLabel posterLabel = new JLabel();
        posterLabel.setBounds(posterX, posterY, POSTER_WIDTH, POSTER_HEIGHT);
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (movie.getPosterPath() != null) {
            ImageIcon posterIcon = new ImageIcon(movie.getPosterPath());
            Image scaledPoster = posterIcon.getImage().getScaledInstance(POSTER_WIDTH, POSTER_HEIGHT, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaledPoster));
        } else {
            posterLabel.setText("No Image");
        }
        contentPanel.add(posterLabel);

        // === Info ===
        int gapX = 80;
        int infoX = posterX + POSTER_WIDTH + gapX;
        int infoWidth = Const.FRAME_WIDTH - infoX - 60;

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        infoPanel.setBounds(infoX, posterY, infoWidth, POSTER_HEIGHT);

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel durationLabel = new JLabel("Duration: " + movie.getDuration() + " min");
        JLabel ratingLabel = new JLabel("Rating: " + movie.getRating());
        JLabel releaseDateLabel = new JLabel("Release Date: " + movie.getReleaseDate());
        JLabel removalDateLabel = new JLabel("Removal Date: " + movie.getRemovalDate());

        for (JLabel label : new JLabel[]{durationLabel, ratingLabel, releaseDateLabel, removalDateLabel}) {
            label.setFont(new Font("SansSerif", Font.PLAIN, 16));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        JTextPane descriptionPane = new JTextPane();
        descriptionPane.setText(movie.getDescription());
        descriptionPane.setEditable(false);
        descriptionPane.setBackground(Const.COLOR_MAIN_LIGHT);
        descriptionPane.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descriptionPane.setForeground(Color.BLACK);
        descriptionPane.setBorder(null);

        StyledDocument doc = descriptionPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_JUSTIFIED);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        JScrollPane scrollPane = new JScrollPane(descriptionPane);
        scrollPane.setPreferredSize(new Dimension(infoWidth, 150));
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(durationLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(releaseDateLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(removalDateLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(scrollPane);

        contentPanel.add(infoPanel);
        add(contentPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieInfoPage(Movie.dummyMovie));
    }
}