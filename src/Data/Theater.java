package Data;

import connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Theater {

    // === Fields ===
    private int id;
    private String name;
    private String type;
    private boolean isActive;

    // === Shared List of Theaters ===
    public static ArrayList<Theater> theaterList = null;

    // === Constructor ===
    public Theater(int id, String name, String type, boolean isActive) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isActive = isActive;
    }

    // === Getters ===
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isActive() {
        return isActive;
    }

    // === Setters ===
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // === Fetch Theater List ===
    public static ArrayList<Theater> fetchTheaterList() {
        if (theaterList != null) {
            return theaterList;
        }

        theaterList = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();

        try (Connection conn = dbConn.getConnection()) {
            String sql = "SELECT id, name, type, is_active FROM theaters";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                boolean isActive = rs.getBoolean("is_active");

                Theater t = new Theater(id, name, type, isActive);
                theaterList.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return theaterList;
    }

    // === Refresh Theater List (force reload from DB) ===
    public static void refreshTheaterList() {
        theaterList = null;
    }

    // === Optional Debug ===
    @Override
    public String toString() {
        return "Theater{id=" + id + ", name='" + name + "', type='" + type + "', active=" + isActive + "}";
    }
}
