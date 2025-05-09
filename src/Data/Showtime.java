package Data;

import connection.DatabaseConnection;

import java.sql.*;
import java.util.*;

public class Showtime {

    private int id;
    private int movieId;
    private int theaterId;
    private String theaterName = null;
    private Timestamp startTime;
    private Timestamp endTime;
    private boolean isCanceled;
    private boolean theaterTypeIsBig = false;
    private boolean theaterTypeIsBigSet = false;

    private static final Map<Integer, Showtime> cache = new HashMap<>();

    public static final Showtime dummyShowtime = new Showtime(
            1, 101, 5,
            Timestamp.valueOf("2025-05-03 14:30:00"),
            Timestamp.valueOf("2025-05-03 16:30:00"),
            false
    );

    public Showtime(int id, int movieId, int theaterId, Timestamp startTime, Timestamp endTime, boolean isCanceled) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCanceled = isCanceled;
    }

    // === Getters ===
    public int getId() { return id; }
    public int getMovieId() { return movieId; }
    public int getTheaterId() { return theaterId; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public boolean isCanceled() { return isCanceled; }

    public String getTheaterName() {
        if (theaterName == null) {
            for (Theater t : Theater.fetchTheaterList()) {
                if (t.getId() == theaterId) {
                    theaterName = t.getName();
                    break;
                }
            }
        }
        return theaterName;
    }

    public boolean getTheaterTypeIsBig() {
        if (!theaterTypeIsBigSet) {
            for (Theater t : Theater.fetchTheaterList()) {
                if (t.getId() == theaterId) {
                    theaterTypeIsBig = "大廳".equals(t.getType());
                    theaterTypeIsBigSet = true;
                    break;
                }
            }
        }
        return theaterTypeIsBig;
    }

    // === Lazy Load (Cache-backed) ===
    public static List<Showtime> getAllShowtimes() {
        if (cache.isEmpty()) {
            fetchShowtimesFromDatabase();
        }
        return new ArrayList<>(cache.values());
    }

    public static Showtime fetchById(int id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ShowTimes WHERE id = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Showtime showtime = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movies_id"),
                        rs.getInt("theater_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getBoolean("is_canceled")
                );
                cache.put(id, showtime);
                return showtime;
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to fetch showtime by ID: " + e.getMessage());
        }

        return null;
    }

    private static void fetchShowtimesFromDatabase() {
        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Showtimes ORDER BY start_time ASC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Showtime s = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movies_id"),
                        rs.getInt("theater_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getBoolean("is_canceled")
                );
                cache.put(s.getId(), s);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching showtimes: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", theaterId=" + theaterId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isCanceled=" + isCanceled +
                '}';
    }
}