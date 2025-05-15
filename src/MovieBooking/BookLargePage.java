package MovieBooking;

import GlobalConst.Const;
import Data.SessionManager;
import Data.Showtime;
import Data.Movie;
import Data.Order;
import Data.Seat;
import Data.User;
import LoginRegisterForm.LoginForm;
import Main.TopBarPanel;
import MovieBooking.help.BigRoomSeatPanel;
import global.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BookLargePage extends JFrame {

    private JPanel topBarSlot;
    private JPanel contentPanel;

    private JLabel selectedLabel;
    private JPanel selectedSeatDisplay;
    private BigRoomSeatPanel seatPanel;

    private final Color BACK_color = new Color(183, 181, 175);
    private final Color BACK_color_hover = new Color(163, 159, 152);
    private final Color BOOK_color = new Color(185, 128, 109);
    private final Color BOOK_color_hover = new Color(163, 109, 93);

    private final Movie movie;
    private final Showtime showtime;
    private final Order order;

    public BookLargePage(Order order) {
        this.order = order;
        this.movie = order.getMovie();
        this.showtime = order.getShowtime();
        setTitle("Online Booking - " + movie.getTitle());
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        initTopBar();
        initContent(movie);

        setVisible(true);
    }

    private void initTopBar() {
        topBarSlot = new JPanel(null);
        topBarSlot.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initContent(Movie movie) {
        int y = Const.TOP_BAR_HEIGHT;
        int height = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;

        contentPanel = new JPanel();
        contentPanel.setBounds(0, y, Const.FRAME_WIDTH, height);
        contentPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        contentPanel.setLayout(null);

        // === Title Label ===
        JLabel titleLabel = new JLabel("Booking for: " + movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Const.COLOR_MAIN_LIGHT);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(68, 149, 145));
        titleLabel.setBounds(0, 0, Const.FRAME_WIDTH, 30);
        contentPanel.add(titleLabel);

        // === Seat Panel ===
        seatPanel = new BigRoomSeatPanel();
        seatPanel.setBounds((Const.FRAME_WIDTH - 928) / 2, 30, 928, 412);
        seatPanel.setOnSeatSelectionChange(this::refreshSeatList);
        contentPanel.add(seatPanel);

        // === Remaining Space UI ===
        int belowY = 30 + 412;

        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        bottomPanel.setBounds((Const.FRAME_WIDTH - 928) / 2, belowY, 928, 150);
        contentPanel.add(bottomPanel);

        // --- Left Section ---
        selectedLabel = new JLabel("座位（共 0 個）");
        selectedLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        selectedLabel.setBounds(20, 15, 300, 25);
        bottomPanel.add(selectedLabel);

        selectedSeatDisplay = new JPanel();
        selectedSeatDisplay.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        selectedSeatDisplay.setBounds(15, 40, 500, 70);
        selectedSeatDisplay.setBackground(Const.COLOR_MAIN_LIGHT);
        bottomPanel.add(selectedSeatDisplay);

        int buttonY = 30;

        CapsuleButton confirmBtn = new CapsuleButton("開始訂位", BOOK_color, BOOK_color_hover, new Dimension(130, 40));
        confirmBtn.setBounds(750, buttonY, 130, 40);
        confirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                List<String> selectedSeatLabels = seatPanel.getSeatList();
                
                ArrayList<Seat> selectedSeats = new ArrayList<>();
                for (String label : selectedSeatLabels) {
                    selectedSeats.add(new Seat(label));
                }
                order.setSeatList(selectedSeats);
                if (User.getCurrentUser() == null) {
                    JFrame frame = BookLargePage.this;
                    DimLayer dim = new DimLayer(frame);
                    frame.setGlassPane(dim);
                    dim.setVisible(true);
                    SessionManager.returnAfterLogin = frame;
                    SessionManager.redirectTargetPage = () -> {
                        order.setUser(User.currUser);
                        new FoodChoosingPage(order).setVisible(true);
                    };
                    new LoginForm(frame);
                } else {
                    order.setUser(User.currUser);
                    new FoodChoosingPage(order);
                    BookLargePage.this.dispose();
                }
            }
        });
        bottomPanel.add(confirmBtn);

        CapsuleButton backBtn = new CapsuleButton("回上頁", BACK_color, BACK_color_hover, new Dimension(130, 40));
        backBtn.setBounds(750 - 150, buttonY, 130, 40); // 150 px gap from confirm button
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                new ShowtimeChoosePage(order);
                dispose();
            }
        });
        bottomPanel.add(backBtn);

        add(contentPanel);
    }

    private void refreshSeatList() {
        List<String> seats = seatPanel.getSeatList();
        selectedLabel.setText("座位（共 " + seats.size() + " 個）:");

        selectedSeatDisplay.removeAll();
        for (String seat : seats) {
            JLabel seatLabel = new JLabel(seat);
            seatLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            selectedSeatDisplay.add(seatLabel);
        }

        selectedSeatDisplay.revalidate();
        selectedSeatDisplay.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookLargePage(Order.dummyOrder);
        });
    }
}
