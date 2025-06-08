package admin.movieRegisterhelp;

import admin.MovieRegisterPage;
import Data.Movie;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovieSlotPanel extends JPanel {

    private JLabel titleLabel;
    private Movie movie;

    private final Color defaultColor = new Color(213, 221, 226);
    private final Color hoverColor = new Color(200, 191, 184);
    private boolean isHovered = false;

    public MovieSlotPanel(Movie movie) {
        this.movie = movie;

        int panelWidth = Const.LEFT_PANEL_WIDTH;
        setPreferredSize(new Dimension(panelWidth, 50));
        setMaximumSize(new Dimension(panelWidth, 50));
        setMinimumSize(new Dimension(panelWidth, 50));

        setLayout(new GridBagLayout());

        titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(titleLabel);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(false); // Important to allow custom background painting

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                titleLabel.setForeground(Color.WHITE); // <-- set to white on hover
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                titleLabel.setForeground(Color.BLACK); // <-- revert to black when not hovering
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Container topLevel = SwingUtilities.getWindowAncestor(MovieSlotPanel.this);
                if (topLevel instanceof MovieRegisterPage frame) {
                    frame.loadMovieEditor(movie);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = 10; // fully rounded top/bottom
        g2.setColor(defaultColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        g2.dispose();
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
