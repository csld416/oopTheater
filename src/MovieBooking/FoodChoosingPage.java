package MovieBooking;

import Data.Showtime;
import Data.Movie;
import Data.Seat;
import Main.help.TopBarPanel;
import MovieBooking.foodPanels.DrinkPanel;
import MovieBooking.foodPanels.HotFoodPanel;
import MovieBooking.foodPanels.PopcornPanel;
import global.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FoodChoosingPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JLabel selectedLabel;
    private JPanel selectedSeatDisplay;

    private final int gap10 = 10;

    private final Color BACK_color = new Color(183, 181, 175);
    private final Color BACK_color_hover = new Color(163, 159, 152);
    private final Color BOOK_color = new Color(185, 128, 109);
    private final Color BOOK_color_hover = new Color(163, 109, 93);

    private final Color DEFAULT_BG = new Color(230, 230, 230);
    private final Color HOVER_BG = new Color(210, 210, 210);
    private final Color CLICKED_BG = new Color(185, 128, 109);

    private Showtime showtime;
    private ArrayList<Seat> seatList;

    private JPanel foodContentPanel;
    private final int FOOD_PANEL_HEIGHT = UIConstants.FOOD_PANEL_HEIGHT;
    private final int FOOD_PANEL_WIDTH = UIConstants.FRAME_WIDTH / 2;
    private final int FOOD_PANEL_Y = gap10 * 2 + 40;

    public FoodChoosingPage(Movie movie, Showtime showtime, ArrayList<Seat> selectedSeats) {
        this.showtime = showtime;
        this.seatList = selectedSeats;

        setTitle("Online Booking - " + movie.getTitle());
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        initTopBar();
        initTitle(movie);
        initLeft();
        initRight(movie);

        setVisible(true);
    }

    private void initTopBar() {
        topBarSlot = new JPanel(null);
        topBarSlot.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initTitle(Movie movie) {
        JLabel titleLabel = new JLabel("Booking for: " + movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(UIConstants.COLOR_TITLE);
        titleLabel.setBounds(0, UIConstants.TOP_BAR_HEIGHT, UIConstants.FRAME_WIDTH, 30);
        add(titleLabel);
    }

    private void initLeft() {
        int y = UIConstants.TOP_BAR_HEIGHT + 30;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, y, UIConstants.FRAME_WIDTH / 2, height);
        leftPanel.setBackground(new Color(245, 245, 245));

        initLeftUp();

        add(leftPanel);
    }

    private void initLeftUp() {
        int panelWidth = 360;
        int panelHeight = 40;
        int x = (leftPanel.getWidth() - panelWidth) / 2;
        int y = gap10;

        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        categoryPanel.setBounds(x, y, panelWidth, panelHeight);
        categoryPanel.setBackground(DEFAULT_BG);

        Font font = new Font("SansSerif", Font.PLAIN, 14);
        String[] categories = {"飲料類", "熱食類", "爆米花類"};
        boolean[] toggled = {true, false, false};
        JLabel[] labels = new JLabel[3];

        // Initialize container for content below category bar
        foodContentPanel = new JPanel(null);
        foodContentPanel.setBounds((leftPanel.getWidth() - FOOD_PANEL_WIDTH) / 2, FOOD_PANEL_Y, FOOD_PANEL_WIDTH, FOOD_PANEL_HEIGHT);
        foodContentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.add(foodContentPanel);

        for (int i = 0; i < categories.length; i++) {
            final int index = i;
            JLabel label = new JLabel(categories[i], SwingConstants.CENTER);
            label.setFont(font);
            label.setOpaque(true);
            label.setBackground(toggled[i] ? CLICKED_BG : DEFAULT_BG);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (!toggled[index]) {
                        label.setBackground(HOVER_BG);
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (!toggled[index]) {
                        label.setBackground(DEFAULT_BG);
                    }
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    for (int j = 0; j < labels.length; j++) {
                        toggled[j] = false;
                        labels[j].setBackground(DEFAULT_BG);
                    }
                    toggled[index] = true;
                    label.setBackground(CLICKED_BG);
                    loadFoodPanel(index);
                }
            });

            labels[i] = label;
            categoryPanel.add(label);
        }

        leftPanel.add(categoryPanel);
        loadFoodPanel(0); // default to 飲料類
    }

    private void loadFoodPanel(int categoryIndex) {
        foodContentPanel.removeAll();

        JPanel content;
        switch (categoryIndex) {
            case 0 ->
                content = new DrinkPanel();
            case 1 ->
                content = new HotFoodPanel();
            case 2 ->
                content = new PopcornPanel();
            default ->
                content = new JPanel();
        }

        content.setBounds(0, 0, FOOD_PANEL_WIDTH, FOOD_PANEL_HEIGHT);
        foodContentPanel.add(content);
        foodContentPanel.revalidate();
        foodContentPanel.repaint();
    }

    private void initRight(Movie movie) {
        int y = UIConstants.TOP_BAR_HEIGHT + 30;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        rightPanel = new JPanel(null);
        rightPanel.setBounds(UIConstants.FRAME_WIDTH / 2, y, UIConstants.FRAME_WIDTH / 2, height);
        rightPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);

        JLabel posterLabel = new JLabel();
        ImageIcon rawPoster = new ImageIcon(movie.getPosterPath());
        Image scaled = rawPoster.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
        posterLabel.setIcon(new ImageIcon(scaled));
        posterLabel.setBounds(30, 30, 120, 180);
        rightPanel.add(posterLabel);

        JLabel titleLabel = new JLabel("\u3000" + movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBounds(160, 30, 300, 25);
        rightPanel.add(titleLabel);

        JLabel ratingLabel = new JLabel("分級：" + movie.getRating());
        ratingLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ratingLabel.setBounds(160, 60, 300, 20);
        rightPanel.add(ratingLabel);

        JLabel durationLabel = new JLabel("時長：" + movie.getDuration() + " 分鐘");
        durationLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        durationLabel.setBounds(160, 85, 300, 20);
        rightPanel.add(durationLabel);

        JLabel showtimeLabel = new JLabel("場次：" + showtime.getStartTime().toString());
        showtimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        showtimeLabel.setBounds(160, 110, 300, 20);
        rightPanel.add(showtimeLabel);

        selectedLabel = new JLabel("座位（共 " + seatList.size() + " 個）:");
        selectedLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectedLabel.setBounds(30, 220, 300, 25);
        rightPanel.add(selectedLabel);

        selectedSeatDisplay = new JPanel();
        selectedSeatDisplay.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        selectedSeatDisplay.setBounds(30, 250, 300, 80);
        selectedSeatDisplay.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        for (Seat seat : seatList) {
            JLabel seatLabel = new JLabel(seat.getLabel());
            seatLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            selectedSeatDisplay.add(seatLabel);
        }
        rightPanel.add(selectedSeatDisplay);

        CapsuleButton confirmBtn = new CapsuleButton("開始訂位", BOOK_color, BOOK_color_hover, new Dimension(130, 40));
        confirmBtn.setBounds(250, 400, 130, 40);
        rightPanel.add(confirmBtn);

        CapsuleButton backBtn = new CapsuleButton("回上頁", BACK_color, BACK_color_hover, new Dimension(130, 40));
        backBtn.setBounds(100, 400, 130, 40);
        rightPanel.add(backBtn);

        add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Seat> dummySeats = Seat.dummySeats;
            new FoodChoosingPage(Movie.dummyMovie, Showtime.dummyShowtime, dummySeats);
        });
    }
}
