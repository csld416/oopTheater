package admin;

import form.TopBarPanel;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminMovieFrame extends JFrame {

    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JScrollPane scrollPane;
    private JPanel slotsPanel;
    private JButton addMovieButton;

    private final int leftPanelWidth = UIConstants.LEFT_PANEL_WIDTH;

    public static class Movie {

        public String title;
        public String releaseDate;
        public String removalDate;
        public String posterFilePath;

        public Movie(String title, String releaseDate, String removalDate, String posterFilePath) {
            this.title = title;
            this.releaseDate = releaseDate;
            this.removalDate = removalDate;
            this.posterFilePath = posterFilePath;
        }
    }

    private final List<Movie> movieList = new ArrayList<>();

    public AdminMovieFrame() {
        setTitle("Admin Movie Panel");
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
        topPanel = new TopBarPanel();
        topPanel.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        add(topPanel);
    }

    private void initLeftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        int topOffset = UIConstants.TOP_BAR_HEIGHT;
        int leftPanelHeight = UIConstants.FRAME_HEIGHT - topOffset;
        leftPanel.setBounds(0, topOffset, leftPanelWidth, leftPanelHeight);

        slotsPanel = new JPanel();
        slotsPanel.setLayout(new BoxLayout(slotsPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(slotsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        addMovieButton = new JButton("+ Add Movie");
        addMovieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addMovieButton.setMaximumSize(new Dimension(200, 40));
        addMovieButton.addActionListener(e -> showNewMovieForm());

        JPanel addButtonPanel = new JPanel();
        addButtonPanel.setLayout(new BoxLayout(addButtonPanel, BoxLayout.Y_AXIS));
        addButtonPanel.add(Box.createVerticalGlue());
        addButtonPanel.add(addMovieButton);

        scrollPane.setColumnHeaderView(addButtonPanel);

        add(leftPanel);
    }

    private void initRightPanel() {
        rightPanel = new JPanel(null);
        int topOffset = UIConstants.TOP_BAR_HEIGHT;
        int rightPanelX = leftPanelWidth;
        int rightPanelY = topOffset;
        int rightPanelWidth = UIConstants.FRAME_WIDTH - rightPanelX;
        int rightPanelHeight = UIConstants.FRAME_HEIGHT - topOffset;

        rightPanel.setBounds(rightPanelX, rightPanelY, rightPanelWidth, rightPanelHeight);
        rightPanel.setBackground(new Color(247, 244, 241)); // match MovieRegister

        add(rightPanel);
    }

    private void showNewMovieForm() {
        rightPanel.removeAll();
        rightPanel.setLayout(null);

        // === Title Bar Panel ===
        JPanel registerTitleBar = new JPanel(null);
        registerTitleBar.setBackground(new Color(169, 183, 198));
        registerTitleBar.setBounds(0, 0, rightPanel.getWidth(), 30);

        JLabel titleLabel = new JLabel("Movie Register Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 0, registerTitleBar.getWidth(), 30);  // center across the full width
        registerTitleBar.add(titleLabel);

        rightPanel.add(registerTitleBar);

        // === Movie Register Panel ===
        MovieRegister registerPanel = new MovieRegister();
        int formWidth = 620;
        int formHeight = 430;
        int x = (rightPanel.getWidth() - formWidth) / 2;
        int y = 40;

        registerPanel.setBounds(x, y, formWidth, formHeight);
        rightPanel.add(registerPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminMovieFrame::new);
    }
}
