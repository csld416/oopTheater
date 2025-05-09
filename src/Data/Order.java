package Data;

import connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.AbstractMap;

public class Order {

    private Movie movie = null;
    private Showtime showtime = null;
    private ArrayList<Seat> seatList = new ArrayList<>();
    private ArrayList<AbstractMap.SimpleEntry<Food, Integer>> foodList = new ArrayList<>();
    private int totalCost = 0;
    private User user;

    private static ArrayList<Order> cachedOrderList = null;

    public static final Order dummyOrder;

    static {
        User dummyUser = new User(1, "測試用戶", "0912345678");
        Order temp = new Order();
        temp.setUser(dummyUser);
        temp.setMovie(Movie.dummyMovie);
        temp.setShowtime(Showtime.dummyShowtime);
        ArrayList<Seat> dummySeats = new ArrayList<>(Seat.dummySeats);
        temp.setSeatList(dummySeats);
        ArrayList<AbstractMap.SimpleEntry<Food, Integer>> dummyFoodList = new ArrayList<>();
        dummyFoodList.add(new AbstractMap.SimpleEntry<>(new Food("path/to/cola.png", 60, "可樂", true, 0), 2));
        dummyFoodList.add(new AbstractMap.SimpleEntry<>(new Food("path/to/popcorn.png", 80, "爆米花", true, 2), 1));
        temp.setFoodList(dummyFoodList);
        int ticketTotal = dummySeats.size() * 320;
        int foodTotal = dummyFoodList.stream().mapToInt(e -> e.getKey().getPrice() * e.getValue()).sum();
        temp.setTotalCost(ticketTotal + foodTotal + 20);
        dummyOrder = temp;
    }

    public Order() {
        clear();
    }

    // === Setters ===
    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }

    public void setFoodList(ArrayList<AbstractMap.SimpleEntry<Food, Integer>> foodList) {
        this.foodList = foodList;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    // === Getters ===
    public Movie getMovie() {
        return movie;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    public ArrayList<AbstractMap.SimpleEntry<Food, Integer>> getFoodList() {
        return foodList;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public User getUser() {
        return user;
    }

    public void clear() {
        movie = null;
        showtime = null;
        seatList.clear();
        foodList.clear();
        totalCost = 0;
    }

    public static ArrayList<Order> getList() {
        if (cachedOrderList == null) {
            fetchOrdersFromDB();
        }
        return cachedOrderList;
    }

    private static void fetchOrdersFromDB() {
        cachedOrderList = new ArrayList<>();
        try (Connection conn = new DatabaseConnection().getConnection()) {
            String sql = "SELECT * FROM Tickets";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                int ticketId = rs.getInt("id");

                order.setUser(User.getCurrentUser());
                order.setMovie(Movie.fetchById(rs.getInt("movie_id")));
                order.setShowtime(Showtime.fetchById(rs.getInt("showtime_id")));
                order.setSeatList(Seat.fetchByTicketId(ticketId));
                order.setTotalCost(rs.getInt("total_price"));

                // Note: foodList not stored in tickets table
                cachedOrderList.add(order);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching orders: " + e.getMessage());
        }
    }

    public static void insertOrder(Order order) {
        String ticketSql = "INSERT INTO Tickets (user_id, movie_id, showtime_id, total_price, status) VALUES (?, ?, ?, ?, 0)";
        String seatSql = "INSERT INTO BookedSeat (ticket_id, seat_label, showtime_id) VALUES (?, ?, ?)";

        try (Connection conn = new DatabaseConnection().getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            try (PreparedStatement ticketStmt = conn.prepareStatement(ticketSql, Statement.RETURN_GENERATED_KEYS)) {
                ticketStmt.setInt(1, order.getUser().getId());
                ticketStmt.setInt(2, order.getMovie().getId());
                ticketStmt.setInt(3, order.getShowtime().getId());
                ticketStmt.setInt(4, order.getTotalCost());

                int affectedRows = ticketStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("❌ Inserting ticket failed, no rows affected.");
                }

                int ticketId;
                try (ResultSet generatedKeys = ticketStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ticketId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("❌ Inserting ticket failed, no ID obtained.");
                    }
                }

                // Insert Booked Seats
                try (PreparedStatement seatStmt = conn.prepareStatement(seatSql)) {
                    for (Seat seat : order.getSeatList()) {
                        seatStmt.setInt(1, ticketId);
                        seatStmt.setString(2, seat.getLabel());
                        seatStmt.setInt(3, order.getShowtime().getId());
                        seatStmt.addBatch();
                    }
                    seatStmt.executeBatch();
                }

                conn.commit();
                System.out.println("✅ Order inserted with ticket ID: " + ticketId);
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("❌ Transaction rolled back: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("❌ DB error during order insert: " + e.getMessage());
        }
    }

}
