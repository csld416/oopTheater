package MovieBooking.help;

import Data.*;
import connection.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BigRoomSeatPanel extends JPanel {

    private static final int TOTAL_ROWS = 17;
    private static final int SEAT_ROWS = 16;
    private static final int COLS = 42;
    private static final int CELL_SIZE = 20;

    private static final Color DEFAULT_COLOR = Color.GRAY;
    private static final Color HOVER_COLOR = Color.CYAN;
    private static final Color PRESSED_COLOR = Color.GREEN;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private Runnable onSeatSelectionChange;

    private final ArrayList<String> selectedSeats = new ArrayList<>();
    private boolean isFull = false;
    private final List<RoundedSeatPanel> allSeatPanels = new ArrayList<>();

    private Showtime showtime;
    private Set<String> bookedSeats;

    public BigRoomSeatPanel(Showtime showtime) {

        this.showtime = showtime;
        this.bookedSeats = fetchBookedSeatsFromDatabase(showtime);

        setLayout(new BorderLayout());

        JPanel screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(COLS * (CELL_SIZE + 2), 30));
        screenPanel.setMaximumSize(new Dimension(928, 412));
        screenPanel.setBackground(Color.LIGHT_GRAY);
        JLabel screenLabel = new JLabel("銀SCREEN幕", SwingConstants.CENTER);
        screenLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        screenPanel.setLayout(new BorderLayout());
        screenPanel.add(screenLabel, BorderLayout.CENTER);
        add(screenPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(SEAT_ROWS, COLS, 2, 2));
        gridPanel.setBackground(Color.WHITE);

        for (int row = 0; row < SEAT_ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                boolean isSeat = true;
                Color panelColor = DEFAULT_COLOR;
                String label = "";
                Color textColor = TEXT_COLOR;

                boolean isSeatColumn = (col >= 1 && col <= 11) || (col >= 15 && col <= 26) || (col >= 30 && col <= 40);
                boolean isSeatRow = row >= 1 && row <= 7 || row >= 9 && row <= 12 || row == 15;

                if (row == 0 || row == 8 || row == 13) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                }

                if (col == 0 || col == COLS - 1) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                    textColor = Color.BLACK;
                    switch (row) {
                        case 1 ->
                            label = "A";
                        case 2 ->
                            label = "B";
                        case 3 ->
                            label = "C";
                        case 4 ->
                            label = "D";
                        case 5 ->
                            label = "E";
                        case 6 ->
                            label = "F";
                        case 7 ->
                            label = "G";
                        case 9 ->
                            label = "H";
                        case 10 ->
                            label = "I";
                        case 11 ->
                            label = "J";
                        case 12 ->
                            label = "K";
                        case 14 ->
                            label = "L";
                        case 15 ->
                            label = "M";
                    }
                }

                if (row == 15 && col >= 9 && col <= 30) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                }

                if (col == 12 || col == 13 || col == 14 || col == 27 || col == 28 || col == 29) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                }

                if ((col == 14 || col == 27) && row != 0 && row != 8 && row != 13) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                    label = String.valueOf((char) ('A' + (row - 1 - (row > 8 ? 1 : 0))));
                    if (row < 13) {
                        textColor = Color.BLACK;
                    }
                }

                if (row == 14 && col != 0 && col != COLS - 1) {
                    isSeat = true;
                    panelColor = DEFAULT_COLOR;
                    label = String.valueOf(col);
                }

                if ((row == 1 && (col >= 1 && col <= 7 || col >= 34 && col <= 40))
                        || (row == 2 && (col >= 1 && col <= 4 || col >= 37 && col <= 40))
                        || (row == 14 && col == 40)
                        || (row == 15 && (col == 39 || col == 40))) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                }

                if (isSeatRow && isSeatColumn) {
                    int seatLabel = -1;
                    if (col >= 1 && col <= 11) {
                        seatLabel = col;
                    }
                    if (col >= 15 && col <= 26) {
                        seatLabel = col - 1;
                    }
                    if (col >= 30 && col <= 40) {
                        seatLabel = col - 2;
                    }
                    if (seatLabel != -1) {
                        label = String.valueOf(seatLabel);
                    }
                }
                String rowLabel = "";
                if (row == 14) {
                    rowLabel = "L";
                }
                if (isSeatRow) {
                    int actualRow = row;
                    if (row > 13) {
                        actualRow--;
                    }
                    if (row > 8) {
                        actualRow--;
                    }
                    rowLabel = String.valueOf((char) ('A' + actualRow - 1));
                }

                String seatId = rowLabel + "-" + label;
                RoundedSeatPanel seat;

                if (isSeat) {
                    if (bookedSeats.contains(seatId)) {
                        Color bookedColor = Color.RED;
                        seat = new RoundedSeatPanel(bookedColor, HOVER_COLOR, PRESSED_COLOR, textColor, label, rowLabel, false);
                    } else {
                        seat = new RoundedSeatPanel(DEFAULT_COLOR, HOVER_COLOR, PRESSED_COLOR, textColor, label, rowLabel, true);
                    }
                } else {
                    seat = new RoundedSeatPanel(Color.WHITE, HOVER_COLOR, PRESSED_COLOR, textColor, label, rowLabel, false);
                }

                // Add listeners if touchable
                if (isSeat) {
                    seat.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            String sid = seat.getSeat();

                            if (selectedSeats.contains(sid)) {
                                selectedSeats.remove(sid);
                                seat.setSelectedState(false);
                            } else {
                                if (selectedSeats.size() >= 10) {
                                    return;
                                }
                                selectedSeats.add(sid);
                                seat.setSelectedState(true);
                            }

                            isFull = selectedSeats.size() >= 10;
                            if (isFull) {
                                for (RoundedSeatPanel s : allSeatPanels) {
                                    if (!s.isSelected()) {
                                        s.setFull();
                                    }
                                }
                            }
                            if (onSeatSelectionChange != null) {
                                onSeatSelectionChange.run();
                            }
                        }
                    });
                }

                seat.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                gridPanel.add(seat);
                allSeatPanels.add(seat);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    public void setOnSeatSelectionChange(Runnable callback) {
        this.onSeatSelectionChange = callback;
    }

    public List<String> getSeatList() {
        return new ArrayList<>(selectedSeats);
    }

    private HashSet<String> fetchBookedSeatsFromDatabase(Showtime showtime) {
        HashSet<String> booked = new HashSet<>();
        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = """
                SELECT bs.seat_label
                FROM BookedSeat bs
                JOIN Tickets t ON bs.ticket_id = t.id
                WHERE bs.showtime_id = ?
                  AND t.status = 1
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showtime.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                booked.add(rs.getString("seat_label"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booked;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Seat Grid with SCREEN + Row Labels");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(new BigRoomSeatPanel(Showtime.dummyShowtime)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        System.out.println(frame.getWidth());
        System.out.println(frame.getHeight());
    }
}
