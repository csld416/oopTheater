package MainPage;

import global.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MoviePanel extends JPanel {

    public MoviePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, UIConstants.MOVIE_CARD_HGAP, UIConstants.MOVIE_CARD_VGAP));
        setBackground(Color.WHITE);

        // Left spacer to create visual centering
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(5, 1));
        add(spacer);

        loadMoviesFromDatabase();
    }

    private void loadMoviesFromDatabase() {
        ArrayList<Movie> movieList = Movie.getAllMovies();

        // Clear all (keep spacer)
        removeAll();
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(5, 1));
        add(spacer);

        // Add real cards
        for (Movie movie : movieList) {
            add(new MovieCardPanel(movie));
        }

        revalidate();
        repaint();
    }
}
