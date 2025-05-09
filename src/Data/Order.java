package Data;

import java.util.ArrayList;
import java.util.AbstractMap;

public class Order {

    private Movie movie = null;
    private Showtime showtime = null;
    private ArrayList<Seat> seatList = new ArrayList<>();
    private ArrayList<AbstractMap.SimpleEntry<Food, Integer>> foodList = new ArrayList<>();
    private int totalCost = 0;
    private User user;

    public static final Order dummyOrder;

    static {
        // Dummy user
        User dummyUser = new User(1, "測試用戶", "0912345678");
        Order temp = new Order();

        // Dummy movie and showtime
        temp.setMovie(Movie.dummyMovie);
        temp.setShowtime(Showtime.dummyShowtime);

        // Dummy seat list
        ArrayList<Seat> dummySeats = new ArrayList<>(Seat.dummySeats);
        temp.setSeatList(dummySeats);

        // Dummy food list
        ArrayList<AbstractMap.SimpleEntry<Food, Integer>> dummyFoodList = new ArrayList<>();
        dummyFoodList.add(new AbstractMap.SimpleEntry<>(new Food("path/to/cola.png", 60, "可樂", true, 0), 2));
        dummyFoodList.add(new AbstractMap.SimpleEntry<>(new Food("path/to/popcorn.png", 80, "爆米花", true, 2), 1));
        temp.setFoodList(dummyFoodList);

        // Calculate total
        int ticketTotal = dummySeats.size() * 320;
        int foodTotal = dummyFoodList.stream().mapToInt(e -> e.getKey().getPrice() * e.getValue()).sum();
        temp.setTotalCost(ticketTotal + foodTotal + 20);

        dummyOrder = temp;
    }

    // === Constructor ===
    public Order() {
        clear();
    }

    // === Setters ===
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

    // === Clear Method ===
    public void clear() {
        movie = null;
        showtime = null;
        seatList.clear();
        foodList.clear();
        totalCost = 0;
    }

    // === Store Order in DB Placeholder ===
    public static void storeOrderInDB(Order order) {
        // TODO: Implement real DB logic
        System.out.println("📦 Storing order in DB:");
        System.out.println("👤 User: " + order.getUser().getName());
        System.out.println("🎬 Movie: " + (order.getMovie() != null ? order.getMovie().getTitle() : "N/A"));
        System.out.println("🕒 Showtime: " + (order.getShowtime() != null ? order.getShowtime().getStartTime() : "N/A"));
        System.out.println("💺 Seats: " + order.getSeatList().size());
        System.out.println("🍿 Foods: " + order.getFoodList().size());
        System.out.println("💰 Total: NT$" + order.getTotalCost());
    }
}
