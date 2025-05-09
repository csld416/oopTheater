package admin;

import admin.movieRegisterhelp.MovieRegisterPanel;
import admin.movieRegisterhelp.MovieSlotPanel;
import admin.topBar.AdminTopBarPanel;
import global.CapsuleButton;
import Data.Movie;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MovieRegisterPage extends JFrame {

    private static final int SPACING = 20;

    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel buttonRow;
    private JPanel slotsPanel;
    private JScrollPane scrollPane;

    public MovieRegisterPage() {
        setTitle("Admin Movie Panel");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
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
        topPanel.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        add(topPanel);
    }

    private void initLeftPanel() {
        int topOffset = Const.TOP_BAR_HEIGHT;
        int height = Const.FRAME_HEIGHT - topOffset;

        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, topOffset, Const.LEFT_PANEL_WIDTH, height);
        leftPanel.setBackground(Color.WHITE);
        add(leftPanel);

        CapsuleButton addBtn = new CapsuleButton("+ Add Movie",
                new Color(80, 140, 160), new Color(100, 160, 180), new Dimension(120, 35));

        CapsuleButton upBtn = new CapsuleButton("\u2B06",
                new Color(180, 180, 180), new Color(140, 140, 140), new Dimension(40, 40));

        CapsuleButton downBtn = new CapsuleButton("\u2B07",
                new Color(180, 180, 180), new Color(140, 140, 140), new Dimension(40, 40));

        buttonRow = new JPanel();
        buttonRow.setLayout(null);
        buttonRow.setBounds((Const.LEFT_PANEL_WIDTH - 220) / 2, SPACING, 220, 45);
        buttonRow.setOpaque(false);

        upBtn.setBounds(0, 0, 40, 40);
        addBtn.setBounds(50, 5, 120, 35);
        downBtn.setBounds(180, 0, 40, 40);

        buttonRow.add(upBtn);
        buttonRow.add(addBtn);
        buttonRow.add(downBtn);
        leftPanel.add(buttonRow);

        slotsPanel = new JPanel();
        slotsPanel.setLayout(new BoxLayout(slotsPanel, BoxLayout.Y_AXIS));
        slotsPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(slotsPanel);
        scrollPane.setBounds(0, SPACING + 50, Const.LEFT_PANEL_WIDTH, height - SPACING - 50);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        leftPanel.add(scrollPane);

        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showNewMovieForm();
            }
        });

        upBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue() - 100);
            }
        });

        downBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue() + 100);
            }
        });

        loadMovieSlots();
    }

    private void initRightPanel() {
        int topOffset = Const.TOP_BAR_HEIGHT;
        int width = Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH;
        int height = Const.FRAME_HEIGHT - topOffset;

        rightPanel = new JPanel(null);
        rightPanel.setBounds(Const.LEFT_PANEL_WIDTH, topOffset, width, height);
        rightPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        add(rightPanel);
    }

    private void loadMovieSlots() {
        slotsPanel.removeAll();

        for (Movie movie : Movie.getAllMovies()) {
            MovieSlotPanel slot = new MovieSlotPanel(movie);
            slot.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    loadMovieEditor(movie);
                }
            });
            slotsPanel.add(slot);
        }

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }

    private void showNewMovieForm() {
        rightPanel.removeAll();
        MovieRegisterPanel panel = new MovieRegisterPanel();
        int w = Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH;
        int h = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;
        panel.setBounds(0, 0, w, h);
        rightPanel.add(panel);
        rightPanel.repaint();
        rightPanel.revalidate();
    }

    public void loadMovieEditor(Movie movie) {
        rightPanel.removeAll();
        MovieRegisterPanel panel = new MovieRegisterPanel(movie);
        int w = Const.FRAME_WIDTH - Const.LEFT_PANEL_WIDTH;
        int h = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;
        panel.setBounds(0, 0, w, h);
        rightPanel.add(panel);
        rightPanel.repaint();
        rightPanel.revalidate();
    }

    public void refreshMovieLeftPanel() {
        loadMovieSlots(); // Reload the slots from database
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieRegisterPage::new);
    }
}
