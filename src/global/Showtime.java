package global;

import connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class Showtime {

    // === Fields ===
    private int id;
    private int movieId;
    private int theaterId;
    private Timestamp startTime;
    private Timestamp endTime;
    private boolean isCanceled;

    // === Shared List Cache ===
    public static ArrayList<Showtime> allShowtimes = null;

    public static Showtime dummyShowtime = new Showtime(
            1, // id
            101, // movieId
            5, // theaterId
            Timestamp.valueOf("2025-05-03 14:30:00"), // startTime
            Timestamp.valueOf("2025-05-03 16:30:00"), // endTime
            false // isCanceled
    );

    // === Constructor ===
    public Showtime(int id, int movieId, int theaterId, Timestamp startTime, Timestamp endTime, boolean isCanceled) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCanceled = isCanceled;
    }

    // === Getters ===
    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    // === Setters ===
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    // === Lazy Fetch Method ===
    public static ArrayList<Showtime> getAllShowtimes() {
        if (allShowtimes == null) {
            allShowtimes = fetchShowtimesFromDatabase();
        }
        return allShowtimes;
    }

    // === DB Fetch Helper ===
    private static ArrayList<Showtime> fetchShowtimesFromDatabase() {
        ArrayList<Showtime> list = new ArrayList<>();

        try (Connection conn = new DatabaseConnection().getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Showtimes ORDER BY start_time ASC"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Showtime s = new Showtime(
                        rs.getInt("id"),
                        rs.getInt("movies_id"),
                        rs.getInt("theater_id"),
                        rs.getTimestamp("start_time"),
                        rs.getTimestamp("end_time"),
                        rs.getBoolean("is_canceled")
                );
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching showtimes: " + e.getMessage());
        }

        return list;
    }

    @Override
    public String toString() {
        return "Showtime{"
                + "id=" + id
                + ", movieId=" + movieId
                + ", theaterId=" + theaterId
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", isCanceled=" + isCanceled
                + '}';
    }
}
