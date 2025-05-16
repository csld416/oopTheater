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
import java.awt.Dimension;

import javax.swing.*;
import java.util.ArrayList;

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
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout for precise positioning
        setResizable(false);

        initTopBarSlot();
        initGapSlot();
        initMovieSlot();

        allMovies = Movie.getAllMovies();
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
            MovieCardPanel card = new MovieCardPanel(m);
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
