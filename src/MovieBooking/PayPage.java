package MovieBooking;

import Data.Food;
import Data.Movie;
import Data.Order;
import Data.Seat;
import Data.Showtime;
import Main.help.TopBarPanel;
import Pages.MyTicketSpacePage;
import global.CapsuleButton;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractMap;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

public class PayPage extends JFrame {

    private final Movie movie;
    private final Showtime showtime;
    private final ArrayList<Seat> seatList;
    private final ArrayList<AbstractMap.SimpleEntry<Food, Integer>> foodList;
    private final int total;
    private final Order order;

    private JPanel topBarPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private final Color GRAY = new Color(245, 245, 245);

    public PayPage(Order order) {
        this.order = order;
        this.movie = order.getMovie();
        this.showtime = order.getShowtime();
        this.seatList = order.getSeatList();
        this.foodList = order.getFoodList();
        this.total = order.getTotalCost();

        setTitle("付款頁面");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
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
        topBarPanel.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topBarPanel.add(topBar);

        add(topBarPanel);
    }

    private void initLeft() {
        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, UIConstants.TOP_BAR_HEIGHT, UIConstants.FRAME_WIDTH / 2, UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT);
        leftPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);
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

        JLabel showtimeLabel = new JLabel("場次：" + showtime.getStartTime().toString());
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
        y += 120;

        // === Total Price ===
        JLabel priceLabel = new JLabel("總計 NT$" + total);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        priceLabel.setForeground(new Color(185, 128, 109));
        priceLabel.setBounds(30, y, 300, 30);
        leftPanel.add(priceLabel);
        add(leftPanel);
    }

    private int centerX(int width) {
        return (UIConstants.FRAME_WIDTH / 2 - width) / 2;
    }

    private void initRight() {
        rightPanel = new JPanel(null);
        rightPanel.setBounds(UIConstants.FRAME_WIDTH / 2, UIConstants.TOP_BAR_HEIGHT,
                UIConstants.FRAME_WIDTH / 2, UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT);
        rightPanel.setBackground(GRAY);
        add(rightPanel);

        int y = 40;
        int centerxx = UIConstants.FRAME_WIDTH / 2 / 2;

        ImageHoverPanel linePayPanel = new ImageHoverPanel("src/Qrcode/LINE_Pay_logo.png");
        linePayPanel.setBounds(centerxx - 150, y, 300, 60);
        rightPanel.add(linePayPanel);

        y += 70;
        ImageHoverPanel JKOPayPanel = new ImageHoverPanel("src/Qrcode/JKOPAY_logo.png");
        JKOPayPanel.setBounds(centerxx - 150, y, 300, 60);
        rightPanel.add(JKOPayPanel);

        y += 70;
        ImageHoverPanel SAMSUNG_PayPanel = new ImageHoverPanel("src/Qrcode/SAMSUNG_Pay_logo.jpg");
        SAMSUNG_PayPanel.setBounds(centerxx - 150, y, 300, 60);
        rightPanel.add(SAMSUNG_PayPanel);

        // Load demo QR code below
        y += 80;
        ImageIcon qrIcon = new ImageIcon("src/Qrcode/demo_qrcode.png");
        Image scaledQR = qrIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel qrLabel = new JLabel(new ImageIcon(scaledQR));
        qrLabel.setBounds(centerX(200), y, 200, 200); // adjust Y as needed
        rightPanel.add(qrLabel);

        y += 220;
        Color OK_DEFAULT = new Color(160, 200, 180);  // soft green
        Color OK_HOVER = new Color(140, 180, 160);    // darker on hover
        Dimension preferredSize = new Dimension(120, 40);
        CapsuleButton OKbtn = new CapsuleButton("完成", OK_DEFAULT, OK_HOVER, preferredSize);
        OKbtn.setBounds(centerxx - 60, y, preferredSize.width, preferredSize.height);

        OKbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();  // or any logic to close the PayPage
                Order.storeOrderInDB(order);
                new MyTicketSpacePage();
            }
        });

        rightPanel.add(OKbtn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PayPage(Order.dummyOrder);
        });
    }

    private class ImageHoverPanel extends JPanel {

        private final int PANEL_WIDTH = 300;
        private final int PANEL_HEIGHT = 100;
        private final int ARC_RADIUS = 40;

        private final Color DEFAULT_BG = new Color(255, 255, 255);
        private final Color HOVER_BG = new Color(230, 230, 230);

        public ImageHoverPanel(String imagePath) {
            setLayout(null);
            setOpaque(false); // needed for rounded corners to work
            setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setSize(PANEL_WIDTH, PANEL_HEIGHT);
            setBackground(DEFAULT_BG);
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                if (icon.getIconWidth() <= 0) {
                    throw new Exception("Image not found");
                }

                // --- Resize while keeping aspect ratio ---
                int maxWidth = 160;
                int maxHeight = 40;

                double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();
                int iconWidth = maxWidth;
                int iconHeight = (int) (maxWidth / aspectRatio);

                if (iconHeight > maxHeight) {
                    iconHeight = maxHeight;
                    iconWidth = (int) (maxHeight * aspectRatio);
                }

                Image scaled = icon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaled));

                int x = (PANEL_WIDTH - iconWidth) / 2;
                int y = (PANEL_HEIGHT - iconHeight) / 2 - 20;

                imageLabel.setBounds(x, y, iconWidth, iconHeight);
                add(imageLabel);

            } catch (Exception e) {
                System.err.println("❌ Failed to load image: " + imagePath);
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(HOVER_BG);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(DEFAULT_BG);
                    setCursor(Cursor.getDefaultCursor());
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_RADIUS, ARC_RADIUS);

            g2.dispose();
            super.paintComponent(g); // draws children like JLabel
        }
    }

}
