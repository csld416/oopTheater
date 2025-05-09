package Data;

import java.time.LocalDateTime;

public class Ticket {

    private int id;
    private int userId;
    private int movieId;
    private int showtimeId;
    private int totalPrice;
    private boolean isCancelled;
    private LocalDateTime createdAt;

    // === Constructor ===
    public Ticket(int userId, int movieId, int showtimeId, int totalPrice) {
        this.userId = userId;
        this.movieId = movieId;
        this.showtimeId = showtimeId;
        this.totalPrice = totalPrice;
        this.isCancelled = false;
        this.createdAt = LocalDateTime.now();
    }

    // === Getters ===
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // === Setters ===
    public void setId(int id) {
        this.id = id;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    // === Utility ===
    @Override
    public String toString() {
        return "Ticket{"
                + "id=" + id
                + ", userId=" + userId
                + ", movieId=" + movieId
                + ", showtimeId=" + showtimeId
                + ", totalPrice=" + totalPrice
                + ", isCancelled=" + isCancelled
                + ", createdAt=" + createdAt
                + '}';
    }
}
