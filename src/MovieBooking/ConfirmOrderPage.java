package MovieBooking;

import GlobalConst.Const;
import Data.Food;
import Data.Movie;
import Data.Order;
import Data.Seat;
import Data.Showtime;
import Main.TopBarPanel;
import global.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.AbstractMap;
import text.Confirm;
import text.Notice;
import text.Terms;

public class ConfirmOrderPage extends JFrame {

    private JPanel topBarPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private final Color PRIMARY = Const.COLOR_MAIN_GREEN;
    private final Color PRIMARY_HOVER = new Color(60, 130, 125);
    private final Color BACK = new Color(183, 181, 175);
    private final Color BACK_HOVER = new Color(163, 159, 152);
    private final Color GRAY = new Color(245, 245, 245);

    private final Movie movie;
    private final Showtime showtime;
    private final ArrayList<Seat> seatList;
    private final ArrayList<AbstractMap.SimpleEntry<Food, Integer>> foodList;
    private final Order order;
    private int total;

    public ConfirmOrderPage(Order order) {
        this.order = order;
        this.movie = order.getMovie();
        this.showtime = order.getShowtime();
        this.seatList = order.getSeatList();
        this.foodList = order.getFoodList();
        setTitle("付款頁面");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        initTopBar();
        initLeft();
        initRight();

        setVisible(true);
    }

    private void initTopBar() {
        topBarPanel = new JPanel(null);
        topBarPanel.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        topBarPanel.add(topBar);

        add(topBarPanel);
    }

    private void initLeft() {
        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH / 2, Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT);
        leftPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        int y = 30;

        JLabel posterLabel = new JLabel();
        ImageIcon rawPoster = new ImageIcon(movie.getPosterPath());
        Image scaled = rawPoster.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
        posterLabel.setIcon(new ImageIcon(scaled));
        posterLabel.setBounds(30, y, 120, 180);
        leftPanel.add(posterLabel);

        JLabel titleLabel = new JLabel("\u3000" + movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBounds(170, y, 300, 25);
        leftPanel.add(titleLabel);
        y += 40;

        JLabel ratingLabel = new JLabel("分級：" + movie.getRating());
        ratingLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ratingLabel.setBounds(170, y, 300, 20);
        leftPanel.add(ratingLabel);
        y += 30;

        JLabel durationLabel = new JLabel("時長：" + movie.getDuration() + " 分鐘");
        durationLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        durationLabel.setBounds(170, y, 300, 20);
        leftPanel.add(durationLabel);
        y += 30;

        JLabel showtimeLabel = new JLabel("場次："
                + showtime.getStartTime()
                        .toLocalDateTime()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        showtimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        showtimeLabel.setBounds(170, y, 300, 20);
        leftPanel.add(showtimeLabel);
        y = 220;

        JLabel seatLabel = new JLabel("座位（共 " + seatList.size() + " 個）:");
        seatLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        seatLabel.setBounds(30, y, 300, 25);
        leftPanel.add(seatLabel);
        y += 30;

        JPanel seatDisplay = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        seatDisplay.setBackground(GRAY);
        seatDisplay.setBounds(30, y, 300, 30);
        for (Seat seat : seatList) {
            JLabel s = new JLabel(seat.getLabel());
            s.setFont(new Font("SansSerif", Font.PLAIN, 14));
            seatDisplay.add(s);
        }
        leftPanel.add(seatDisplay);
        y += 40;

        JLabel foodHeading = new JLabel("<html>餐點（已選 <font color='blue'>" + foodList.stream().mapToInt(AbstractMap.SimpleEntry::getValue).sum() + "</font> 份）</html>");
        foodHeading.setFont(new Font("SansSerif", Font.BOLD, 14));
        foodHeading.setBounds(30, y, 300, 25);
        leftPanel.add(foodHeading);
        y += 30;

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        foodPanel.setBackground(GRAY);
        foodPanel.setBounds(30, y, 300, 100);
        for (var pair : foodList) {
            if (pair.getValue() > 0) {
                JLabel line = new JLabel("• " + pair.getKey().getName() + " x " + pair.getValue());
                line.setFont(new Font("SansSerif", Font.BOLD, 14));
                foodPanel.add(line);
            }
        }
        leftPanel.add(foodPanel);
        add(leftPanel);
    }

    private void initRight() {
        rightPanel = new JPanel(null);
        rightPanel.setBounds(Const.FRAME_WIDTH / 2, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH / 2, Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT);
        rightPanel.setBackground(GRAY);

        int ticketPrice = 320;
        int fee = 20;
        int foodTotal = foodList.stream().mapToInt(e -> e.getKey().getPrice() * e.getValue()).sum();
        int ticketTotal = seatList.size() * ticketPrice;
        total = ticketTotal + foodTotal + fee;

        int y = 30;

        JLabel heading = new JLabel("訂單明細：");
        heading.setFont(new Font("SansSerif", Font.BOLD, 16));
        heading.setBounds(30, y, 300, 25);
        rightPanel.add(heading);
        y += 40;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);

        JLabel ticketLabel = new JLabel("票價");
        ticketLabel.setFont(labelFont);
        ticketLabel.setBounds(30, y, 100, 20);
        rightPanel.add(ticketLabel);

        JLabel ticketVal = new JLabel("$ " + ticketTotal);
        ticketVal.setFont(labelFont);
        ticketVal.setBounds(300, y, 100, 20);
        rightPanel.add(ticketVal);
        y += 25;

        JLabel foodLabel = new JLabel("餐點");
        foodLabel.setFont(labelFont);
        foodLabel.setBounds(30, y, 100, 20);
        rightPanel.add(foodLabel);

        JLabel foodVal = new JLabel("$ " + foodTotal);
        foodVal.setFont(labelFont);
        foodVal.setBounds(300, y, 100, 20);
        rightPanel.add(foodVal);
        y += 25;

        JLabel feeLabel = new JLabel("手續費");
        feeLabel.setFont(labelFont);
        feeLabel.setBounds(30, y, 100, 20);
        rightPanel.add(feeLabel);

        JLabel feeVal = new JLabel("$ " + fee);
        feeVal.setFont(labelFont);
        feeVal.setBounds(300, y, 100, 20);
        rightPanel.add(feeVal);
        y += 40;

        JLabel totalLabel = new JLabel("總計");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setBounds(30, y, 100, 25);
        rightPanel.add(totalLabel);

        JLabel totalVal = new JLabel("$ " + total);
        totalVal.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalVal.setBounds(300, y, 100, 25);
        rightPanel.add(totalVal);
        y += 50;

        JLabel invoiceLabel = new JLabel("發票");
        invoiceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        invoiceLabel.setBounds(30, y, 100, 20);
        rightPanel.add(invoiceLabel);
        y += 50;

        ButtonGroup group = new ButtonGroup();

        JRadioButton donate = new JRadioButton("捐贈發票");
        donate.setFont(labelFont);
        donate.setBounds(30, y, 200, 20);
        donate.setBackground(GRAY);
        donate.setSelected(true);
        group.add(donate);
        rightPanel.add(donate);

        JLabel donateNote = new JLabel("(財團法人台灣兒童暨家庭扶助基金會)");
        donateNote.setFont(new Font("SansSerif", Font.PLAIN, 12));
        donateNote.setForeground(Color.BLUE);
        donateNote.setBounds(130, y, 300, 20);
        rightPanel.add(donateNote);
        y += 30;

        JRadioButton print = new JRadioButton("發票現場領取");
        print.setFont(labelFont);
        print.setBounds(30, y, 200, 20);
        print.setBackground(GRAY);
        group.add(print);
        rightPanel.add(print);
        y += 30;

        JRadioButton carrier = new JRadioButton("共通載具");
        carrier.setFont(labelFont);
        carrier.setBounds(30, y, 200, 20);
        carrier.setBackground(GRAY);
        group.add(carrier);
        rightPanel.add(carrier);
        y += 80;

        CapsuleButton cancelBtn = new CapsuleButton("取消", BACK, BACK_HOVER, new Dimension(130, 40));
        cancelBtn.setBounds(Const.FRAME_WIDTH / 2 / 2 - 150, y, 130, 40);
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                new FoodChoosingPage(order);
                dispose();
            }
        });
        rightPanel.add(cancelBtn);

        CapsuleButton payBtn = new CapsuleButton("立即付款", PRIMARY, PRIMARY_HOVER, new Dimension(130, 40));
        payBtn.setBounds(Const.FRAME_WIDTH / 2 / 2 + 20, y, 130, 40);
        payBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                payBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                payBtn.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = ConfirmOrderPage.this;
                DimLayer dim = new DimLayer(frame);
                frame.setGlassPane(dim);
                dim.setVisible(true);
                order.setTotalCost(total);
                new Confirm(frame, order, () -> {
                    frame.dispose();
                });
            }
        });
        rightPanel.add(payBtn);

        y += 50;

        JLabel noticeLabel = new JLabel("<html><u>購票須知</u></html>");
        noticeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        noticeLabel.setForeground(new Color(153, 153, 153));
        noticeLabel.setBounds(Const.FRAME_WIDTH / 2 / 2 - 20, y, 80, 40);
        noticeLabel.setCursor(Cursor.getDefaultCursor());

        noticeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                noticeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                noticeLabel.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = ConfirmOrderPage.this;
                DimLayer dim = new DimLayer(frame);
                frame.setGlassPane(dim);
                dim.setVisible(true);
                new Notice(frame);
            }
        });
        rightPanel.add(noticeLabel);

        JLabel termsLabel = new JLabel("<html><u>行動支付服務條款</u></html>");

        termsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        termsLabel.setForeground(new Color(153, 153, 153));
        termsLabel.setBounds(Const.FRAME_WIDTH / 2 / 2 + 40, y, 160, 40);
        termsLabel.setCursor(Cursor.getDefaultCursor());

        termsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                termsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                termsLabel.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = ConfirmOrderPage.this;
                DimLayer dim = new DimLayer(frame);
                frame.setGlassPane(dim);
                dim.setVisible(true);
                new Terms(frame);
            }
        });
        rightPanel.add(termsLabel);

        add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConfirmOrderPage(Order.dummyOrder);
        });
    }
}
