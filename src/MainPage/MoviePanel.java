package MainPage;

import global.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
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
        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            Connection conn = new connection.DatabaseConnection().getConnection();
            String sql = "SELECT * FROM Movies ORDER BY release_date ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie m = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("description"),
                        rs.getString("rating"),
                        rs.getDate("release_date"),
                        rs.getDate("removal_date"),
                        rs.getString("poster_path")
                );
                movieList.add(m);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Clear all (keep spacer)
        removeAll();
        JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(5, 1));
        add(spacer);

        // Add real cards
        for (Movie movie : movieList) {
            add(new MovieCardPanel(movie.getTitle(), movie.getReleaseDate().toString(), movie.getPosterPath()));
        }

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getComponentCount() <= 1) return;

        Component leftCard = getComponent(1); // skip spacer
        Component rightCard = getComponent(getComponentCount() - 1);

        int leftGap = leftCard.getX();
        int rightGap = getWidth() - (rightCard.getX() + rightCard.getWidth());

        System.out.println("Left gap: " + leftGap + "px");
        System.out.println("Right gap: " + rightGap + "px");
    }
}