/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import GlobalConst.Const;
import Main.Movie.MoviePanel;
import Main.help.GapPanel;
import Main.Movie.MovieCardPanel;
import Data.Movie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import java.util.ArrayList;

public class StartingPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel movieSlot;
    private MoviePanel moviePanel;
    private GapPanel gapPanel;

    // In StartingPage.java
    public static ArrayList<Movie> allMovies = new ArrayList<>();
    public static ArrayList<Movie> originalMovieOrder = new ArrayList<>();
    public static int currentStartIndex = 0;
    public static final int MOVIES_PER_PAGE = 4;

    public StartingPage() {
        setTitle("Starting Page");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout for precise positioning
        setResizable(false);

        initTopBarSlot();
        initGapSlot();
        initMovieSlot();

        allMovies = Movie.getAllMovies();
        originalMovieOrder = new ArrayList<>(allMovies);
        refreshMovieCards();

        setVisible(true);
    }

    private void initTopBarSlot() {
        topBarSlot = new JPanel(null); // Allow absolute placement inside
        topBarSlot.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel(); // your custom panel
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initGapSlot() {
        gapPanel = new GapPanel();  // make it a field
        gapPanel.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH, Const.GAP_BETWEEN);
        add(gapPanel);
        gapPanel.revalidate();
        gapPanel.repaint();
    }

    private void initMovieSlot() {
        movieSlot = new JPanel();
        int y = Const.TOP_BAR_HEIGHT + Const.GAP_BETWEEN;
        int height = Const.FRAME_HEIGHT - y;

        movieSlot.setBounds(0, y, Const.FRAME_WIDTH, height);
        movieSlot.setLayout(null);

        moviePanel = new MoviePanel(); // ✅ Initialize here
        moviePanel.setBounds(0, 0, Const.FRAME_WIDTH, height);

        movieSlot.add(moviePanel);
        add(movieSlot);
    }

    public static void sortBy(String option) {
        switch (option) {
            case "上映日期 ⬆" ->
                allMovies.sort((a, b) -> a.getReleaseDate().compareTo(b.getReleaseDate()));
            case "上映日期 ⬇" ->
                allMovies.sort((a, b) -> b.getReleaseDate().compareTo(a.getReleaseDate()));
            case "片長 ⬆" ->
                allMovies.sort((a, b) -> Integer.compare(a.getDuration(), b.getDuration()));
            case "片長 ⬇" ->
                allMovies.sort((a, b) -> Integer.compare(b.getDuration(), a.getDuration()));
            case "下檔日期 ⬆" ->
                allMovies.sort((a, b) -> {
                    if (a.getRemovalDate() == null) {
                        return 1;
                    }
                    if (b.getRemovalDate() == null) {
                        return -1;
                    }
                    return a.getRemovalDate().compareTo(b.getRemovalDate());
                });
            case "下檔日期 ⬇" ->
                allMovies.sort((a, b) -> {
                    if (a.getRemovalDate() == null) {
                        return 1;
                    }
                    if (b.getRemovalDate() == null) {
                        return -1;
                    }
                    return b.getRemovalDate().compareTo(a.getRemovalDate());
                });
            default ->
                allMovies = new ArrayList<>(originalMovieOrder); // restore original
        }
        currentStartIndex = 0;
    }

    public void refreshMovieCards() {
        if (moviePanel == null) {
            return;
        }

        moviePanel.removeAll();

        // Filter displayable movies only
        ArrayList<Movie> displayingMovies = new ArrayList<>();
        java.util.Date now = new java.util.Date();

        for (Movie m : allMovies) {
            // 1. Skip if movie is past removal date
            if (m.getRemovalDate() != null && m.getRemovalDate().before(now)) {
                continue;
            }

            // 2. Skip if no uncanceled future showtimes
            boolean hasValidShowtime = Data.Showtime.getAllShowtimes().stream()
                    .anyMatch(s -> s.getMovieId() == m.getId() && !s.isCanceled() && s.getStartTime().after(now));

            if (!hasValidShowtime) {
                continue;
            }

            // 3. Valid movie
            displayingMovies.add(m);
        }

        if (displayingMovies.isEmpty()) {
            JLabel noMovieLabel = new JLabel("目前沒有可訂票之電影！", SwingConstants.CENTER);
            noMovieLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
            noMovieLabel.setForeground(Color.BLACK);
            noMovieLabel.setBounds(0, 200, Const.FRAME_WIDTH, 40); // vertically offset for aesthetics
            moviePanel.setLayout(null);
            moviePanel.add(noMovieLabel);
        } else {
            // Add spacer first
            JPanel spacer = new JPanel();
            spacer.setOpaque(false);
            spacer.setPreferredSize(new Dimension(5, 1));
            moviePanel.add(spacer);

            // Display at most MOVIES_PER_PAGE cards from currentStartIndex
            int shownCount = 0;
            int index = currentStartIndex;
            while (index < displayingMovies.size() && shownCount < MOVIES_PER_PAGE) {
                Movie m = displayingMovies.get(index);
                MovieCardPanel card = new MovieCardPanel(m);
                moviePanel.add(card);
                shownCount++;
                index++;
            }
        }

        moviePanel.revalidate();
        moviePanel.repaint();
        gapPanel.updateArrowStates();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartingPage::new);
    }
}
