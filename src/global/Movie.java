package global;

import java.sql.Date;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DatabaseConnection;

public class Movie {

    public Integer id;
    private String title;
    private int duration;
    private String description;
    private String rating;
    private Date releaseDate;
    private Date removalDate;
    private String posterPath;

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

    public void setId(int id) {
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

    public static ArrayList<Movie> fetchAllMovies() {
        ArrayList<Movie> list = new ArrayList<>();

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT * FROM Movies ORDER BY release_date ASC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

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

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Error fetching movies: " + e.getMessage());
        }

        return list;
    }
}
