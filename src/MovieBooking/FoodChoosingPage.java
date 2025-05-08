package MovieBooking;

import Data.Food;
import Data.Showtime;
import Data.Movie;
import Data.Seat;
import Main.help.TopBarPanel;
import MovieBooking.foodPanels.FoodEntry;
import global.*;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

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

    private final Color GRAY = new Color(245, 245, 245);

    private Showtime showtime;
    private ArrayList<Seat> seatList;

    private JPanel foodContentPanel;
    private final int FOOD_PANEL_HEIGHT = UIConstants.FOOD_PANEL_HEIGHT;
    private final int FOOD_PANEL_WIDTH = UIConstants.FRAME_WIDTH / 2;
    private final int FOOD_PANEL_Y = gap10 * 2 + 40;

    private ArrayList<AbstractMap.SimpleEntry<Food, Integer>> selectedFoods = new ArrayList<>();
    private int total = 0;
    private JPanel selectedFoodDisplayArea;
    private JLabel headingLabel;

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
        int leftUpHeight = 60;
        int leftUpWidth = UIConstants.FRAME_WIDTH / 2;

        // Wrapper panel for the top-left category bar
        JPanel leftUpPanel = new JPanel(null);
        leftUpPanel.setBounds(0, 0, leftUpWidth, leftUpHeight);
        leftUpPanel.setBackground(GRAY);
        //leftUpPanel.setBorder(new LineBorder(Color.RED));
        leftPanel.add(leftUpPanel);

        // === Category Buttons ===
        int panelWidth = 360;
        int panelHeight = 30;  // Match leftUpPanel height
        int x = (leftUpWidth - panelWidth) / 2;
        int y = 15;

        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        categoryPanel.setBounds(x, y, panelWidth, panelHeight);
        categoryPanel.setOpaque(false); // Let the background of leftUpPanel show through

        Font font = new Font("SansSerif", Font.PLAIN, 14);
        String[] categories = {"飲料類", "熱食類", "爆米花類"};
        boolean[] toggled = {true, false, false};
        JLabel[] labels = new JLabel[3];

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

        leftUpPanel.add(categoryPanel);

        // === Food List Panel ===
        foodContentPanel = new JPanel(new BorderLayout());
        int xx = UIConstants.FRAME_WIDTH / 4 - FOOD_PANEL_WIDTH / 2;
        foodContentPanel.setBounds(xx, FOOD_PANEL_Y, FOOD_PANEL_WIDTH, FOOD_PANEL_HEIGHT);
        foodContentPanel.setBackground(GRAY);
        leftPanel.add(foodContentPanel);

        loadFoodPanel(0); // default to 飲料類
    }

    private void initRight(Movie movie) {
        int y = UIConstants.TOP_BAR_HEIGHT + 30;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        rightPanel = new JPanel(null);
        rightPanel.setBounds(UIConstants.FRAME_WIDTH / 2, y, UIConstants.FRAME_WIDTH / 2, height);
        rightPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);

        y = 30;

        JLabel posterLabel = new JLabel();
        ImageIcon rawPoster = new ImageIcon(movie.getPosterPath());
        Image scaled = rawPoster.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
        posterLabel.setIcon(new ImageIcon(scaled));
        posterLabel.setBounds(30, y, 120, 180);
        rightPanel.add(posterLabel);

        JLabel titleLabel = new JLabel("\u3000" + movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBounds(170, y, 300, 25);
        rightPanel.add(titleLabel);
        y += 40;

        JLabel ratingLabel = new JLabel("分級：" + movie.getRating());
        ratingLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ratingLabel.setBounds(170, y, 300, 20);
        rightPanel.add(ratingLabel);
        y += 30;

        JLabel durationLabel = new JLabel("時長：" + movie.getDuration() + " 分鐘");
        durationLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        durationLabel.setBounds(170, y, 300, 20);
        rightPanel.add(durationLabel);
        y += 30;

        JLabel showtimeLabel = new JLabel("場次：" + showtime.getStartTime().toString());
        showtimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        showtimeLabel.setBounds(170, y, 300, 20);
        rightPanel.add(showtimeLabel);
        y = 220;

        selectedLabel = new JLabel("座位（共 " + seatList.size() + " 個）:");
        selectedLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectedLabel.setBounds(30, y, 300, 25);
        rightPanel.add(selectedLabel);
        y += 30;

        selectedSeatDisplay = new JPanel();
        selectedSeatDisplay.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        selectedSeatDisplay.setBounds(30, y, 300, 30);
        selectedSeatDisplay.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        for (Seat seat : seatList) {
            JLabel seatLabel = new JLabel(seat.getLabel());
            seatLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            selectedSeatDisplay.add(seatLabel);
        }
        rightPanel.add(selectedSeatDisplay);
        y += 40;

        headingLabel = new JLabel("<html>餐點（已選 <font color='blue'>0</font> 份）</html>");
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        headingLabel.setBounds(30, y, 300, 25);
        rightPanel.add(headingLabel);
        y += 30;

        selectedFoodDisplayArea = new JPanel();
        selectedFoodDisplayArea.setLayout(new BoxLayout(selectedFoodDisplayArea, BoxLayout.Y_AXIS));
        selectedFoodDisplayArea.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        selectedFoodDisplayArea.setBounds(30, y, 300, 100);
        rightPanel.add(selectedFoodDisplayArea);
        y += 120;

        CapsuleButton confirmBtn = new CapsuleButton("開始訂位", BOOK_color, BOOK_color_hover, new Dimension(130, 40));
        confirmBtn.setBounds(250, y, 130, 40);
        rightPanel.add(confirmBtn);

        CapsuleButton backBtn = new CapsuleButton("回上頁", BACK_color, BACK_color_hover, new Dimension(130, 40));
        backBtn.setBounds(100, y, 130, 40);
        rightPanel.add(backBtn);

        add(rightPanel);
    }

    private void loadFoodPanel(int categoryIndex) {
        foodContentPanel.removeAll();

        ArrayList<Food> foods = Food.getAllFoods();

        // Dynamic container panel using BoxLayout
        JPanel dynamicContent = new JPanel();
        dynamicContent.setLayout(new BoxLayout(dynamicContent, BoxLayout.Y_AXIS));
        dynamicContent.setBackground(GRAY);

        boolean hasAny = false;
        boolean first = true;
        for (Food food : foods) {
            if (food.getCategory() == categoryIndex) {
                if (!first) {
                    dynamicContent.add(Box.createVerticalStrut(6));
                } else {
                    first = false;
                }

                FoodEntry entry = new FoodEntry(food);
                entry.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center it horizontally
                entry.setQuantityChangeListener((changedFood, newQty) -> {
                    boolean found = false;
                    for (var pair : selectedFoods) {
                        if (pair.getKey().equals(changedFood)) {
                            pair.setValue(newQty);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        selectedFoods.add(new AbstractMap.SimpleEntry<>(changedFood, newQty));
                    }
                    reloadList();
                });
                dynamicContent.add(entry);
                hasAny = true;
            }
        }

        if (!hasAny) {
            JLabel empty = new JLabel("此分類尚無品項", SwingConstants.CENTER);
            empty.setFont(new Font("SansSerif", Font.PLAIN, 16));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            dynamicContent.add(Box.createVerticalStrut(20));
            dynamicContent.add(empty);
        }

        // Wrap inside JScrollPane
        JScrollPane scrollPane = new JScrollPane(dynamicContent,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.getViewport().setBackground(UIConstants.COLOR_MAIN_LIGHT);

        // Replace old bounds-based layout with BorderLayout for flexibility
        foodContentPanel.setLayout(new BorderLayout());
        foodContentPanel.add(scrollPane, BorderLayout.CENTER);

        foodContentPanel.revalidate();
        foodContentPanel.repaint();
    }

    private void reloadList() {
        selectedFoodDisplayArea.removeAll();
        total = 0;

        for (var pair : selectedFoods) {
            Food food = pair.getKey();
            int quantity = pair.getValue();
            if (quantity > 0) {
                JLabel label = new JLabel("• " + food.getName() + " x " + quantity);
                label.setFont(new Font("SansSerif", Font.BOLD, 14));
                selectedFoodDisplayArea.add(label);
                total += quantity;
            }
        }

        // Update the heading label with new total
        headingLabel.setText("<html>餐點（已選 <font color='blue'>" + total + "</font> 份）</html>");

        selectedFoodDisplayArea.revalidate();
        selectedFoodDisplayArea.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArrayList<Seat> dummySeats = Seat.dummySeats;
            new FoodChoosingPage(Movie.dummyMovie, Showtime.dummyShowtime, dummySeats);
        });
    }
}
