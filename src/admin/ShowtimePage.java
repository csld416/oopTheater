package admin;

import admin.movieRegisterhelp.MovieSlotPanel;
import admin.showtimehelp.ShowtimeFormPanel;
import admin.showtimehelp.ShowtimeListPanel;
import admin.topBar.AdminTopBarPanel;
import connection.DatabaseConnection;
import global.UIConstants;
import global.Movie;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ShowtimePage extends JFrame {

    private JPanel topPanel;
    private JPanel rightPanel;
    private JScrollPane scrollPane;
    private JPanel slotsPanel;

    public ShowtimePage() {
        setTitle("Movie Itinerary Registration");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        initTopPanel();
        initLeftPanel();
        initRightPanel();

        setVisible(true);
    }

    private void initTopPanel() {
        topPanel = new AdminTopBarPanel();
        topPanel.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        add(topPanel);
    }

    private void initLeftPanel() {
        int topOffset = UIConstants.TOP_BAR_HEIGHT;
        int leftPanelHeight = UIConstants.FRAME_HEIGHT - topOffset;

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBounds(0, topOffset, UIConstants.LEFT_PANEL_WIDTH, leftPanelHeight);

        slotsPanel = new JPanel();
        slotsPanel.setLayout(new BoxLayout(slotsPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(slotsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        add(leftPanel);

        loadMovieSlots();
    }

    private void initRightPanel() {
        int topOffset = UIConstants.TOP_BAR_HEIGHT;
        int rightPanelWidth = UIConstants.FRAME_WIDTH - UIConstants.LEFT_PANEL_WIDTH;
        int rightPanelHeight = UIConstants.FRAME_HEIGHT - topOffset;

        rightPanel = new JPanel(null);
        rightPanel.setBounds(UIConstants.LEFT_PANEL_WIDTH, topOffset, rightPanelWidth, rightPanelHeight);
        rightPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);

        add(rightPanel);
    }

    private void loadMovieSlots() {
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
                slot.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent e) {
                        setSelectedMovie(movie);
                    }
                });

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

    private void setSelectedMovie(Movie movie) {
        rightPanel.removeAll();

        // Append form
        ShowtimeFormPanel formPanel = new ShowtimeFormPanel(movie);
        formPanel.setBounds(0, 0, rightPanel.getWidth(), 300);
        rightPanel.add(formPanel);

        // Show list of existing showtimes
        ShowtimeListPanel listPanel = new ShowtimeListPanel(movie);
        listPanel.setBounds(0, 320, rightPanel.getWidth(), rightPanel.getHeight() - 320);
        rightPanel.add(listPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShowtimePage::new);
    }
}