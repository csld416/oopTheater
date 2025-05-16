package MovieBooking;

import GlobalConst.Const;
import Data.Showtime;
import Data.Movie;
import Data.Order;
import Main.TopBarPanel;
import MovieBooking.help.UserShowtimeEntryPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ShowtimeChoosePage extends JFrame {

    private JPanel topBarSlot;
    private JLabel titleLabel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private static final int LEFT_WIDTH = 300;
    private static final int GAP_Y = 10;
    private static final int ENTRY_WIDTH = 600;
    
    private final Movie movie;
    private final Order order;

    public ShowtimeChoosePage(Order order) {
        this.order = order;
        this.movie = order.getMovie();
        setTitle("Online Booking - " + movie.getTitle());
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        initTopBar();
        initTitle(movie);
        initLeft(movie);
        initRight(movie);

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
        titleLabel = new JLabel("選擇場次：" + movie.getTitle(), SwingConstants.CENTER); // center text
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBackground(new Color(209, 202, 202));
        titleLabel.setOpaque(true); // enable background rendering
        titleLabel.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH, 40); // full width
        add(titleLabel);
    }

    private void initLeft(Movie movie) {
        int y = Const.TOP_BAR_HEIGHT + 40;

        leftPanel = new JPanel(null);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(0, y, LEFT_WIDTH, Const.FRAME_HEIGHT - y);

        // === Poster ===
        ImageIcon posterIcon = new ImageIcon(movie.getPosterPath());
        Image img = posterIcon.getImage().getScaledInstance(260, 364, Image.SCALE_SMOOTH);
        JLabel posterLabel = new JLabel(new ImageIcon(img));
        posterLabel.setBounds((LEFT_WIDTH - 260) / 2, 10, 260, 364);
        leftPanel.add(posterLabel);

        int infoStartY = 384;

        // === Title ===
        JLabel title = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBounds(0, infoStartY, LEFT_WIDTH, 30);
        leftPanel.add(title);

        // === Rating ===
        JLabel rating = new JLabel("分級：" + movie.getRating(), SwingConstants.CENTER);
        rating.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rating.setBounds(0, infoStartY + 35, LEFT_WIDTH, 25);
        leftPanel.add(rating);

        // === Duration ===
        JLabel duration = new JLabel("時長：" + movie.getDuration() + " 分鐘", SwingConstants.CENTER);
        duration.setFont(new Font("SansSerif", Font.PLAIN, 14));
        duration.setBounds(0, infoStartY + 65, LEFT_WIDTH, 25);
        leftPanel.add(duration);

        add(leftPanel);
    }

    private void initRight(Movie movie) {
        int topY = Const.TOP_BAR_HEIGHT + 40;
        int rightX = LEFT_WIDTH;
        int rightWidth = Const.FRAME_WIDTH - rightX;

        rightPanel = new JPanel(null);
        rightPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        rightPanel.setBounds(rightX, topY, rightWidth, Const.FRAME_HEIGHT - topY);

        JScrollPane scroll = new JScrollPane(rightPanel);
        scroll.setBounds(rightX, topY, rightWidth, Const.FRAME_HEIGHT - topY);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll);

        int y = 0;

        List<Showtime> filtered = Showtime.getAllShowtimes()
                .stream()
                .filter(s -> s.getMovieId() == movie.getId() && !s.isCanceled())
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            JLabel noShowtime = new JLabel("此電影目前無可用場次。", SwingConstants.CENTER);
            noShowtime.setFont(new Font("SansSerif", Font.PLAIN, 16));

            int labelWidth = 300;
            int labelHeight = 30;
            int labelX = (rightWidth - labelWidth) / 2;
            int labelY = (rightPanel.getHeight() - labelHeight) / 2;

            noShowtime.setBounds(labelX, labelY, labelWidth, labelHeight);
            rightPanel.add(noShowtime);
        } else {
            int yy = 10; // 10px vertical margin before the first entry

            for (Showtime s : filtered) {
                UserShowtimeEntryPanel entry = new UserShowtimeEntryPanel(s);
                int entryWidth = ENTRY_WIDTH;
                int entryHeight = entry.getPreferredSize().height;

                int x = (rightWidth - entryWidth) / 2;
                entry.setBounds(x, yy, entryWidth, entryHeight);
                order.setShowtime(s);
                entry.setClickListener(e -> {
                    if (s.getTheaterTypeIsBig()) {
                        new BookLargePage(order);
                    } else {
                        new BookSmallPage(order);
                    }
                    dispose(); // Close the current booking page
                });
                rightPanel.add(entry);
                yy += entryHeight + GAP_Y;
            }

            rightPanel.setPreferredSize(new Dimension(rightWidth, y + 50));
        }

        rightPanel.setPreferredSize(new Dimension(rightWidth, y + 50));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShowtimeChoosePage(Order.dummyOrder);
        });
    }
}
