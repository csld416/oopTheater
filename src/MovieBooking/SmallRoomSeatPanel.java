package MovieBooking;

import javax.swing.*;
import java.awt.*;

public class SmallRoomSeatPanel extends JPanel {

    private static final int TOTAL_ROWS = 11;
    private static final int SEAT_ROWS = 10;
    private static final int COLS = 20;
    private static final int CELL_SIZE = 20;

    private static final Color DEFAULT_COLOR = Color.GRAY;
    private static final Color HOVER_COLOR = Color.CYAN;
    private static final Color PRESSED_COLOR = Color.GREEN;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    public SmallRoomSeatPanel() {
        setLayout(new BorderLayout());

        JPanel screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(COLS * (CELL_SIZE + 2), 30));
        screenPanel.setBackground(Color.LIGHT_GRAY);
        JLabel screenLabel = new JLabel("螢SCREEN幕", SwingConstants.CENTER);
        screenLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        screenPanel.setLayout(new BorderLayout());
        screenPanel.add(screenLabel, BorderLayout.CENTER);
        add(screenPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(SEAT_ROWS, COLS, 2, 2));
        gridPanel.setBackground(Color.WHITE);

        for (int row = 0; row < SEAT_ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boolean isTouchable = true;
                Color panelColor = DEFAULT_COLOR;
                String label = "";
                Color textColor = TEXT_COLOR;
                String rowValue = "";

                // Row 1 (index 0 in grid) is untouchable
                if (row == 0) {
                    isTouchable = false;
                    panelColor = BACKGROUND_COLOR;
                }

                // First and last columns are untouchable
                if (col == 0 || col == COLS - 1) {
                    isTouchable = false;
                    panelColor = BACKGROUND_COLOR;
                }

                // Label columns 5 and 14 (0-based index)
                if ((col == 5 || col == 14) && row > 0) {
                    isTouchable = false;
                    panelColor = BACKGROUND_COLOR;
                    textColor = Color.BLACK;
                    label = String.valueOf((char) ('A' + row - 1));
                }

                // Seat numbering orientation
                if (isTouchable && row > 0) {
                    rowValue = String.valueOf((char) ('A' + row - 1));
                    if (col >= 1 && col <= 4) {
                        label = String.valueOf(col);
                    } else if (col >= 6 && col <= 13) {
                        label = String.valueOf(col - 1);
                    } else if (col >= 15 && col <= 18) {
                        label = String.valueOf(col - 2);
                    }
                }

                RoundedSeatPanel seat = new RoundedSeatPanel(
                        panelColor,
                        HOVER_COLOR,
                        PRESSED_COLOR,
                        textColor,
                        label,
                        rowValue,
                        isTouchable
                );
                seat.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                gridPanel.add(seat);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Small Room Seat Layout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(new SmallRoomSeatPanel()));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}