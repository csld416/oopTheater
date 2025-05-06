package Data;

import connection.DatabaseConnection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Food {

    private String imagePath;
    private int price;
    private String name;
    private boolean isValid;
    private int category;

    // === Constructor ===
    public Food(String imagePath, int price, String name, boolean isValid, int category) {
        this.imagePath = imagePath;
        this.price = price;
        this.name = name;
        this.isValid = isValid;
        this.category = category;
    }

    // === Getters ===
    public String getImagePath() {
        return imagePath;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return isValid;
    }

    public int getCategory() {
        return category;
    }

    // === Lazy Loaded List ===
    private static ArrayList<Food> allFoods = null;

    public static ArrayList<Food> getAllFoods() {
        if (allFoods == null) {
            allFoods = fetchFoodsFromDatabase(); // replace with actual DB fetch later
        }
        return allFoods;
    }

    // === Dummy Fetch (for now) ===
    private static ArrayList<Food> fetchFoodsFromDatabase() {
        ArrayList<Food> list = new ArrayList<>();

        String sql = "SELECT * FROM Foods ORDER BY id ASC";

        try (Connection conn = new DatabaseConnection().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Food food = new Food(
                        rs.getString("image_path"),
                        rs.getInt("price"),
                        rs.getString("name"),
                        rs.getBoolean("is_valid"),
                        rs.getInt("category")
                );
                list.add(food);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error fetching foods: " + e.getMessage());
        }

        return list;
    }
}
