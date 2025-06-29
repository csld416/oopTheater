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
    private String description;
    private String rating;
    private Date releaseDate;
    private Date removalDate;
    private String posterPath;
    private byte[] posterBlob;
    private boolean isDisplaying = true;

    public static ArrayList<Movie> allMovies = null;
    public static Movie dummyMovie = new Movie(
        1,
        "500 Days of Summer",
        95,
        "Boy meets girl. Boy falls in love. Girl doesn't.",
        "PG-13",
        java.sql.Date.valueOf("2009-08-07"),
        java.sql.Date.valueOf("2009-12-07"),
        "src/MoviePosters/500DaysOfSummer.jpg",
        null
    );

    public Movie(Integer id, String title, int duration, String description, String rating,
                 Date releaseDate, Date removalDate, String posterPath, byte[] posterBlob) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.removalDate = removalDate;
        this.posterPath = posterPath;
        this.posterBlob = posterBlob;
    }

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

    public byte[] getPosterBlob() {
        return posterBlob;
    }

    public boolean getIsDisplaying() {
        if (removalDate == null) {
            return true;
        }
        java.util.Date today = new java.util.Date();
        return !removalDate.before(today);
    }

    public void setIsDisplaying(boolean isDisplaying) {
        this.isDisplaying = isDisplaying;
    }

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

    public void setPosterBlob(byte[] posterBlob) {
        this.posterBlob = posterBlob;
    }

    public int getAgeLimit() {
        if (rating == null) {
            return 0;
        }
        return switch (rating) {
            case "普遍級" -> 0;
            case "保護級" -> 6;
            case "輔12" -> 12;
            case "輔15" -> 15;
            case "限制級" -> 18;
            default -> 0;
        };
    }

    public static ArrayList<Movie> getAllMovies() {
        if (allMovies == null) {
            allMovies = fetchMoviesFromDatabase();
        }
        return allMovies;
    }

    private static ArrayList<Movie> fetchMoviesFromDatabase() {
        ArrayList<Movie> list = new ArrayList<>();

        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Movies ORDER BY release_date ASC");
             ResultSet rs = stmt.executeQuery()) {

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
                        rs.getString("poster_path"),
                        rs.getBytes("poster_blob")
                );

                boolean flag = (removalDate != null && removalDate.before(today)) ? false : true;
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