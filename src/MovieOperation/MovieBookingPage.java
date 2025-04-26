package Pages;

import global.*;

import javax.swing.*;
import java.awt.*;

public class MovieBookingPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel contentPanel;

    public MovieBookingPage(Movie movie) {
        setTitle("Online Booking - " + movie.getTitle());
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        initTopBar();
        initContent(movie);

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

    private void initContent(Movie movie) {
        int y = UIConstants.TOP_BAR_HEIGHT;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        contentPanel = new JPanel();
        contentPanel.setBounds(0, y, UIConstants.FRAME_WIDTH, height);
        contentPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        contentPanel.setLayout(new GridBagLayout()); // center alignment

        JLabel label = new JLabel("Booking for: " + movie.getTitle());
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        contentPanel.add(label);

        add(contentPanel);
    }

    // ❗ Because now the constructor needs Movie param, we don't use main anymore
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(MovieBookingPage::new);
//    }
}