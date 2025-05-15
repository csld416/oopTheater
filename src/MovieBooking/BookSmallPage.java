package MovieBooking;

import GlobalConst.Const;
import Data.Showtime;
import Data.Movie;
import Data.Order;
import Data.Seat;
import Data.SessionManager;
import Data.User;
import LoginRegisterForm.LoginForm;
import Main.TopBarPanel;
import MovieBooking.help.SmallRoomSeatPanel;
import global.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BookSmallPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JLabel selectedLabel;
    private JPanel selectedSeatDisplay;
    private SmallRoomSeatPanel seatPanel;

    private final Color BACK_color = new Color(183, 181, 175);
    private final Color BACK_color_hover = new Color(163, 159, 152);
    private final Color BOOK_color = new Color(185, 128, 109);
    private final Color BOOK_color_hover = new Color(163, 109, 93);

    private final Movie movie;
    private final Showtime showtime;
    private final Order order;

    public BookSmallPage(Order order) {
        this.order = order;
        this.movie = order.getMovie();
        this.showtime = order.getShowtime();
        setTitle("Online Booking - " + movie.getTitle());
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

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

    private void initTitle(Movie movie) {
        JLabel titleLabel = new JLabel("Booking for: " + movie.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Const.COLOR_TITLE);
        titleLabel.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH, 30);
        add(titleLabel);
    }

    private void initContent(Movie movie) {
        initTitle(movie);
        int y = Const.TOP_BAR_HEIGHT + 30;
        int height = Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT;

        // === Left Panel ===
        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, y, Const.FRAME_WIDTH / 2, height);
        leftPanel.setBackground(new Color(245, 245, 245));

        seatPanel = new SmallRoomSeatPanel();
        Dimension preferred = seatPanel.getPreferredSize();
        int seatPanelX = (leftPanel.getWidth() - preferred.width) / 2;
        int seatPanelY = 100;
        seatPanel.setBounds(seatPanelX, seatPanelY, preferred.width, preferred.height);
        seatPanel.setOnSeatSelectionChange(this::refreshSeatList);
        leftPanel.add(seatPanel);

        add(leftPanel);

        // === Right Panel ===
        rightPanel = new JPanel(null);
        rightPanel.setBounds(Const.FRAME_WIDTH / 2, y, Const.FRAME_WIDTH / 2, height);
        rightPanel.setBackground(Const.COLOR_MAIN_LIGHT);

        // Movie poster
        JLabel posterLabel = new JLabel();
        ImageIcon rawPoster = new ImageIcon(movie.getPosterPath());
        Image scaled = rawPoster.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
        posterLabel.setIcon(new ImageIcon(scaled));
        posterLabel.setBounds(30, 30, 120, 180);
        rightPanel.add(posterLabel);

        // Movie information
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

        // Seat info
        selectedLabel = new JLabel("座位（共 0 個）:");
        selectedLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectedLabel.setBounds(30, 220, 300, 25);
        rightPanel.add(selectedLabel);

        selectedSeatDisplay = new JPanel();
        selectedSeatDisplay.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        selectedSeatDisplay.setBounds(30, 250, 300, 80);
        selectedSeatDisplay.setBackground(Const.COLOR_MAIN_LIGHT);
        rightPanel.add(selectedSeatDisplay);

        // Buttons
        CapsuleButton confirmBtn = new CapsuleButton("開始訂位", BOOK_color, BOOK_color_hover, new Dimension(130, 40));
        confirmBtn.setBounds(250, 380, 130, 40);
        confirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                List<String> selectedSeatLabels = seatPanel.getSeatList();

                if (selectedSeatLabels.isEmpty()) {
                    JOptionPane.showMessageDialog(BookSmallPage.this, "請選擇至少一個座位！", "尚未選擇座位", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ArrayList<Seat> selectedSeats = new ArrayList<>();
                for (String label : selectedSeatLabels) {
                    selectedSeats.add(new Seat(label));
                }
                order.setSeatList(selectedSeats);

                if (User.getCurrentUser() == null) {
                    JFrame frame = BookSmallPage.this;
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
                    BookSmallPage.this.dispose();
                }
            }
        });
        rightPanel.add(confirmBtn);

        CapsuleButton backBtn = new CapsuleButton("回上頁", BACK_color, BACK_color_hover, new Dimension(130, 40));
        backBtn.setBounds(100, 380, 130, 40);
        rightPanel.add(backBtn);

        add(rightPanel);
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
            new BookSmallPage(Order.dummyOrder);
        });
    }
}
