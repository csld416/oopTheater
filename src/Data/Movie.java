package Data;

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
    private int isDisplaying = 1; // default to showing

    // === Global Shared Movie List ===
    public static ArrayList<Movie> allMovies = null;
    public static Movie dummyMovie = new Movie(1, "500 Days of Summer", 95, "Boy meets girl. Boy falls in love. Girl doesn't.", "PG-13",
            java.sql.Date.valueOf("2009-08-07"), java.sql.Date.valueOf("2009-12-07"), "src/MoviePosters/500DaysOfSummer.jpg");

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
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getRemovalDate() {
        return removalDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getIsDisplaying() {
        return isDisplaying;
    }

    public void setIsDisplaying(int isDisplaying) {
        this.isDisplaying = isDisplaying;
    }

    public int getAgeLimit() {
        if (rating == null) {
            return 0;
        }

        switch (rating) {
            case "普遍級": // General audience
                return 0;
            case "保護級": // Parental Guidance
                return 6;
            case "輔12":   // PG-12
                return 12;
            case "輔15":   // PG-15
                return 15;
            case "限制級": // Restricted (18+)
                return 18;
            default:       // Unknown or unregistered rating
                return 0;
        }
    }

    // === Setters ===
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

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

        try (Connection conn = new DatabaseConnection().getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies ORDER BY release_date ASC"); ResultSet rs = stmt.executeQuery()) {

            java.util.Date today = new java.util.Date();

            while (rs.next()) {
                Date removalDate = rs.getDate("removal_date");

                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("description"),
                        rs.getString("rating"),
                        rs.getDate("release_date"),
                        removalDate,
                        rs.getString("poster_path")
                );

                int flag = (removalDate != null && removalDate.before(today)) ? 0 : 1;
                movie.setIsDisplaying(flag);

                list.add(movie);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching movies: " + e.getMessage());
        }

        return list;
    }

    public static Movie fetchById(int id) {
        for (Movie movie : getAllMovies()) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

}
