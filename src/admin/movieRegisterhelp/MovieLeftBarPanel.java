package admin.movieRegisterhelp;

import connection.DatabaseConnection;
import Data.Movie;
import GlobalConst.Const;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.function.Consumer;

public class MovieLeftBarPanel extends JPanel {

    private JButton addMovieButton;
    private JPanel slotsPanel;
    private JScrollPane scrollPane;
    private final Consumer<Void> onAddMovieClicked;

    public MovieLeftBarPanel(Consumer<Void> onAddMovieClicked) {
        this.onAddMovieClicked = onAddMovieClicked;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Const.LEFT_PANEL_WIDTH, Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT));

        // === Panel for listing movie slots ===
        slotsPanel = new JPanel();
        slotsPanel.setLayout(new BoxLayout(slotsPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(slotsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // === Add Movie Button ===
        addMovieButton = new JButton("+ Add Movie");
        addMovieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addMovieButton.setMaximumSize(new Dimension(200, 40));
        addMovieButton.addActionListener(e -> onAddMovieClicked.accept(null));

        JPanel addButtonPanel = new JPanel();
        addButtonPanel.setLayout(new BoxLayout(addButtonPanel, BoxLayout.Y_AXIS));
        addButtonPanel.add(Box.createVerticalGlue());
        addButtonPanel.add(addMovieButton);

        scrollPane.setColumnHeaderView(addButtonPanel);

        reloadMovieSlots(); // Auto reload when panel is created
    }

    public JPanel getSlotsPanel() {
        return slotsPanel;
    }

    public void reloadMovieSlots() {
        slotsPanel.removeAll();

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT * FROM Movies ORDER BY release_date ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("description"),
                        rs.getString("rating"),
                        rs.getDate("release_date"),
                        rs.getDate("removal_date"),
                        rs.getString("poster_path")
                );

                MovieSlotPanel slot = new MovieSlotPanel(movie);
                slot.setCursor(new Cursor(Cursor.HAND_CURSOR));
                slotsPanel.add(slot);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.err.println("❌ Failed to load movie slots: " + e.getMessage());
        }

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }
} 
