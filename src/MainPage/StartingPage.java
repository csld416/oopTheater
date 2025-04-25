/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPage;

import PanelButton.TopBarPanel;
import connection.DatabaseConnection;
import global.Movie;
import global.UIConstants;
import java.awt.Dimension;

import javax.swing.*;
import java.util.ArrayList;
import java.sql.*;

public class StartingPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel movieSlot;
    private MoviePanel moviePanel;
    private GapPanel gapPanel;

    // In StartingPage.java
    public static ArrayList<Movie> allMovies = new ArrayList<>();
    public static int currentStartIndex = 0;
    public static final int MOVIES_PER_PAGE = 4;

    public StartingPage() {
        setTitle("Starting Page");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout for precise positioning

        initTopBarSlot();
        initGapSlot();
        initMovieSlot();

        fetchMoviesFromDB();
        refreshMovieCards();

        setVisible(true);
    }

    private void initTopBarSlot() {
        topBarSlot = new JPanel(null); // Allow absolute placement inside
        topBarSlot.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel(); // your custom panel
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initGapSlot() {
        gapPanel = new GapPanel();  // make it a field
        gapPanel.setBounds(0, UIConstants.TOP_BAR_HEIGHT, UIConstants.FRAME_WIDTH, UIConstants.GAP_BETWEEN);
        add(gapPanel);
        gapPanel.revalidate();  // 🔥 Force layout manager to layout its children
        gapPanel.repaint();     // 🔥 Force repaint so doLayout() has an effect
    }

    private void initMovieSlot() {
        movieSlot = new JPanel();
        int y = UIConstants.TOP_BAR_HEIGHT + UIConstants.GAP_BETWEEN;
        int height = UIConstants.FRAME_HEIGHT - y;

        movieSlot.setBounds(0, y, UIConstants.FRAME_WIDTH, height);
        movieSlot.setLayout(null);

        moviePanel = new MoviePanel(); // ✅ Initialize here
        moviePanel.setBounds(0, 0, UIConstants.FRAME_WIDTH, height);

        movieSlot.add(moviePanel);
        add(movieSlot);
    }

    private void fetchMoviesFromDB() {
        allMovies.clear();
        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT * FROM Movies ORDER BY release_date ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                allMovies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("description"),
                        rs.getString("rating"),
                        rs.getDate("release_date"),
                        rs.getDate("removal_date"),
                        rs.getString("poster_path")
                ));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshMovieCards() {
        if (moviePanel == null) {
            return;
        }

        moviePanel.removeAll();

        // Add spacer first
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(5, 1));
        moviePanel.add(spacer);

        // Add up to 4 cards from currentStartIndex
        for (int i = currentStartIndex; i < Math.min(currentStartIndex + MOVIES_PER_PAGE, allMovies.size()); i++) {
            Movie m = allMovies.get(i);
            MovieCardPanel card = new MovieCardPanel(m.getTitle(), m.getReleaseDate().toString(), m.getPosterPath());
            moviePanel.add(card);
        }

        moviePanel.revalidate();
        moviePanel.repaint();
        gapPanel.updateArrowStates();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartingPage::new);
    }
}
