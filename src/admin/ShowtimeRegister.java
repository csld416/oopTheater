package admin;

import admin.movieRegisterhelp.MovieSlotPanel;
import admin.showtimehelp.ShowtimeFormPanel;
import admin.showtimehelp.ShowtimeListPanel;
import admin.topBar.AdminTopBarPanel;
import connection.DatabaseConnection;
import global.CapsuleButton;
import Data.Movie;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowtimeRegister extends JFrame {

    private static final int SPACING = 20;

    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel buttonRow;
    private JPanel slotsPanel;
    private JScrollPane scrollPane;

    public ShowtimeRegister() {
        setTitle("Movie Itinerary Registration");
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

        CapsuleButton upBtn = new CapsuleButton("\u2B06", new Color(180, 180, 180), new Color(140, 140, 140), new Dimension(40, 40));
        CapsuleButton downBtn = new CapsuleButton("\u2B07", new Color(180, 180, 180), new Color(140, 140, 140), new Dimension(40, 40));

        buttonRow = new JPanel(null);
        buttonRow.setBounds((Const.LEFT_PANEL_WIDTH - 80) / 2, SPACING, 80, 45);
        buttonRow.setOpaque(false);

        upBtn.setBounds(0, 0, 40, 40);
        downBtn.setBounds(40, 0, 40, 40);

        buttonRow.add(upBtn);
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
                    setSelectedMovie(movie);
                }
            });
            slotsPanel.add(slot);
        }

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }

    private void setSelectedMovie(Movie movie) {
        rightPanel.removeAll();

        ShowtimeListPanel listPanel = new ShowtimeListPanel(movie);
        listPanel.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight()); // REMOVE "-320"
        rightPanel.add(listPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShowtimeRegister::new);
    }
}
