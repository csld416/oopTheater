package admin.movieRegisterhelp;

import admin.MovieRegisterPage;
import global.Movie;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovieSlotPanel extends JPanel {

    private JLabel titleLabel;
    private Movie movie; // ⬅ Store the movie object

    public MovieSlotPanel(Movie movie) {
        this.movie = movie;

        int panelWidth = UIConstants.LEFT_PANEL_WIDTH; // or subtract padding if needed
        setPreferredSize(new Dimension(panelWidth, 50));
        setMaximumSize(new Dimension(panelWidth, 50));
        setMinimumSize(new Dimension(panelWidth, 50));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        setLayout(new GridBagLayout());

        titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(titleLabel);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(210, 225, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Container topLevel = SwingUtilities.getWindowAncestor(MovieSlotPanel.this);
                if (topLevel instanceof MovieRegisterPage frame) {
                    frame.loadMovieEditor(movie); // ✅ now valid
                }
            }
        });
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test MovieSlotPanel");
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            MovieSlotPanel slotPanel = new MovieSlotPanel(Movie.dummyMovie);
            frame.add(slotPanel);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
