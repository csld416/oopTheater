package admin;

import form.TopBarPanel;
import global.*;

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

    private MovieLeftBarPanel movieLeftBarPanel;

    private final int leftPanelWidth = UIConstants.LEFT_PANEL_WIDTH;

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
        int topOffset = UIConstants.TOP_BAR_HEIGHT;
        int leftPanelHeight = UIConstants.FRAME_HEIGHT - topOffset;

        movieLeftBarPanel = new MovieLeftBarPanel(v -> showNewMovieForm());
        movieLeftBarPanel.setBounds(0, topOffset, UIConstants.LEFT_PANEL_WIDTH, leftPanelHeight);
        this.leftPanel = movieLeftBarPanel;
        this.scrollPane = (JScrollPane) movieLeftBarPanel.getComponent(0);
        this.slotsPanel = movieLeftBarPanel.getSlotsPanel();
        this.add(movieLeftBarPanel);
    }

    private void initRightPanel() {
        rightPanel = new JPanel(null);
        int topOffset = UIConstants.TOP_BAR_HEIGHT;
        int rightPanelX = leftPanelWidth;
        int rightPanelY = topOffset;
        int rightPanelWidth = UIConstants.FRAME_WIDTH - rightPanelX;
        int rightPanelHeight = UIConstants.FRAME_HEIGHT - topOffset;

        rightPanel.setBounds(rightPanelX, rightPanelY, rightPanelWidth, rightPanelHeight);
        rightPanel.setBackground(new Color(247, 244, 241));

        add(rightPanel);
    }

    private void showNewMovieForm() {
        rightPanel.removeAll();
        rightPanel.setLayout(null);

        // === Title Bar Panel ===
        JPanel registerTitleBar = new JPanel(null);
        registerTitleBar.setBackground(new Color(204, 211, 218));
        registerTitleBar.setBounds(0, 0, rightPanel.getWidth(), 30);

        JLabel titleLabel = new JLabel("Movie Register Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(0, 0, registerTitleBar.getWidth(), 30);  // center across the full width
        registerTitleBar.add(titleLabel);

        rightPanel.add(registerTitleBar);

        // === Movie Register Panel ===
        MovieRegisterPanel registerPanel = new MovieRegisterPanel();
        int formWidth = 620;
        int formHeight = 430;
        int x = (rightPanel.getWidth() - formWidth) / 2;
        int y = 40;

        registerPanel.setBounds(x, y, formWidth, formHeight);
        rightPanel.add(registerPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public void loadMovieEditor(Movie movie) {
        rightPanel.removeAll();

        MovieRegisterPanel registerPanel = new MovieRegisterPanel(movie); // overloaded constructor
        int formWidth = 620;
        int formHeight = 430;
        int x = (rightPanel.getWidth() - formWidth) / 2;
        int y = 40;

        registerPanel.setBounds(x, y, formWidth, formHeight);
        rightPanel.setLayout(null);
        rightPanel.add(registerPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public void refreshMovieLeftPanel() {
        movieLeftBarPanel.reloadMovieSlots();
    }   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminMovieFrame::new);
    }
}
