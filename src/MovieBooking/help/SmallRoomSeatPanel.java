package MovieBooking.help;

import Data.Showtime;
import connection.DatabaseConnection;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.*;

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

    private Runnable onSeatSelectionChange;

    private final ArrayList<String> selectedSeats = new ArrayList<>();
    private boolean isFull = false;
    private final List<RoundedSeatPanel> allSeatPanels = new ArrayList<>();

    private final Set<String> bookedSeats;

    public SmallRoomSeatPanel(Showtime showtime) {
        this.bookedSeats = fetchBookedSeatsFromDatabase(showtime);
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
                boolean isSeat = true;
                Color panelColor = DEFAULT_COLOR;
                String label = "";
                Color textColor = TEXT_COLOR;
                String rowValue = "";

                if (row == 0 || col == 0 || col == COLS - 1) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                }

                if ((col == 5 || col == 14) && row > 0) {
                    isSeat = false;
                    panelColor = BACKGROUND_COLOR;
                    textColor = Color.BLACK;
                    label = String.valueOf((char) ('A' + row - 1));
                }

                if (isSeat && row > 0) {
                    rowValue = String.valueOf((char) ('A' + row - 1));
                    if (col >= 1 && col <= 4)
                        label = String.valueOf(col);
                    else if (col >= 6 && col <= 13)
                        label = String.valueOf(col - 1);
                    else if (col >= 15 && col <= 18)
                        label = String.valueOf(col - 2);
                }

                String seatId = rowValue + "-" + label;
                RoundedSeatPanel seat;

                if (isSeat) {
                    if (bookedSeats.contains(seatId)) {
                        Color bookedColor = Color.RED;
                        seat = new RoundedSeatPanel(bookedColor, textColor, label, rowValue, true);
                    } else {
                        seat = new RoundedSeatPanel(DEFAULT_COLOR, HOVER_COLOR, PRESSED_COLOR, textColor, label, rowValue, true);
                    }
                } else {
                    seat = new RoundedSeatPanel(Color.WHITE, HOVER_COLOR, PRESSED_COLOR, textColor, label, rowValue, false);
                }

                if (isSeat) {
                    seat.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            String sid = seat.getSeat();

                            if (selectedSeats.contains(sid)) {
                                selectedSeats.remove(sid);
                                seat.setSelectedState(false);
                            } else {
                                if (selectedSeats.size() >= 10) return;
                                selectedSeats.add(sid);
                                seat.setSelectedState(true);
                            }

                            isFull = selectedSeats.size() >= 10;
                            if (isFull) {
                                for (RoundedSeatPanel s : allSeatPanels) {
                                    if (!s.isSelected()) s.setFull();
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

        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(1, 10));
        spacer.setOpaque(true);
        spacer.setBackground(Color.WHITE);
        add(spacer, BorderLayout.SOUTH);
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
            String sql = "SELECT seat_label FROM BookedSeat WHERE showtime_id = ?";
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
}