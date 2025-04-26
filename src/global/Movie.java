package global;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.DatabaseConnection;

public class Movie {

    // === Fields ===
    private Integer id;
    private String title;
    private int duration;
    private String description; // Text field - String is appropriate
    private String rating;
    private Date releaseDate;
    private Date removalDate;
    private String posterPath;

    // === Global Shared Movie List ===
    public static ArrayList<Movie> allMovies = null;
    public static Movie dummyMovie = new Movie(1, "500 Days of Summer", 95, "Boy meets girl. Boy falls in love. Girl doesn't.", "PG-13", 
                                         java.sql.Date.valueOf("2009-08-07"), java.sql.Date.valueOf("2009-12-07"), "src/MoviePosters/sample.jpg");

    // === Constructor ===
    public Movie(Integer id, String title, int duration, String description, String rating,
                 Date releaseDate, Date removalDate, String posterPath) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.removalDate = removalDate;
        this.posterPath = posterPath;
    }

    // === Getters ===
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public String getDescription() { return description; }
    public String getRating() { return rating; }
    public Date getReleaseDate() { return releaseDate; }
    public Date getRemovalDate() { return removalDate; }
    public String getPosterPath() { return posterPath; }

    // === Setters ===
    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setDescription(String description) { this.description = description; }
    public void setRating(String rating) { this.rating = rating; }
    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }
    public void setRemovalDate(Date removalDate) { this.removalDate = removalDate; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    // === Lazy Fetch Method ===
    public static ArrayList<Movie> getAllMovies() {
        if (allMovies == null) {
            allMovies = fetchMoviesFromDatabase();
        }
        return allMovies;
    }

    // === Private Fetch Helper ===
    private static ArrayList<Movie> fetchMoviesFromDatabase() {
        ArrayList<Movie> list = new ArrayList<>();

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies ORDER BY release_date ASC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("description"),
                        rs.getString("rating"),
                        rs.getDate("release_date"),
                        rs.getDate("removal_date"),
                        rs.getString("poster_path")
                );
                list.add(movie);
            }
        } catch (SQLException e) {
            System.err.println("\u274C Error fetching movies: " + e.getMessage());
        }

        return list;
    }
}
