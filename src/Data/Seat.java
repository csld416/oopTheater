package Data;

import connection.DatabaseConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Seat {

    private String label;

    // Constructor
    public Seat(String label) {
        this.label = label;
    }

    // Getter
    public String getLabel() {
        return label;
    }

    // Setter
    public void setLabel(String label) {
        this.label = label;
    }

    private static final Map<Integer, ArrayList<Seat>> seatCache = new HashMap<>();

    public static ArrayList<Seat> fetchByTicketId(int ticketId) {
        if (seatCache.containsKey(ticketId)) {
            return seatCache.get(ticketId);
        }

        ArrayList<Seat> seatList = new ArrayList<>();
        String sql = "SELECT seat_label FROM BookedSeat WHERE ticket_id = ?";

        try (Connection conn = new DatabaseConnection().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                seatList.add(new Seat(rs.getString("seat_label")));
            }

            // cache the result
            seatCache.put(ticketId, seatList);

        } catch (SQLException e) {
            System.err.println("❌ Error fetching seats for ticket_id " + ticketId + ": " + e.getMessage());
        }

        return seatList;
    }

    // Static dummy list
    public static ArrayList<Seat> dummySeats = new ArrayList<>() {
        {
            add(new Seat("A-1"));
            add(new Seat("A-2"));
            add(new Seat("B-1"));
            add(new Seat("B-2"));
            add(new Seat("C-1"));
        }
    };
}
