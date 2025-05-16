package Data;

import connection.DatabaseConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.security.MessageDigest;

public class User {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dob; // ⬅️ Replaces int age
    private String gender;
    private BufferedImage profileImage;

    public static User currUser = null;

    public static final User dummyUser;

    static {
        dummyUser = new User(
                0,
                "測試用戶",
                "dummy@example.com",
                "0912345678",
                LocalDate.of(2004, 1, 1),
                "Male",
                null
        );
    }

    // === Constructor ===
    public User(int id, String name, String email, String phoneNumber, LocalDate dob, String gender, BufferedImage profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.gender = gender;
        this.profileImage = profileImage;
    }

    // === Session Management ===
    public static User getCurrentUser() {
        return currUser;
    }

    public static void setCurrentUser(User user) {
        currUser = user;
    }

    // === Getters ===
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public BufferedImage getProfileImage() {
        return profileImage;
    }

    // === Setters ===
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void clear() {
        this.id = -1;
        this.name = null;
        this.email = null;
        this.phoneNumber = null;
        this.dob = null;
        this.gender = null;
        this.profileImage = null;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString(); // 64-character hex string
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // === Fetch user from DB ===
    public static User fetchUserByEmail(String email) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection conn = dbConnection.getConnection();

        if (conn != null) {
            try {
                String query = "SELECT * FROM users WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("fullname");
                    String phone = rs.getString("phone");
                    Date sqlDob = rs.getDate("dob");
                    LocalDate dob = sqlDob != null ? sqlDob.toLocalDate() : null;
                    String gender = rs.getString("gender");

                    // Load profile image
                    InputStream imageStream = rs.getBinaryStream("picture");
                    BufferedImage profileImg = null;
                    if (imageStream != null) {
                        profileImg = ImageIO.read(imageStream);
                    }

                    return new User(id, name, email, phone, dob, gender, profileImg);
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    // === Register user to DB ===
    public static void registerUser(String fullname, String email, String password, String phone, LocalDate dob, String gender, File profilePictureFile) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        try {
            Connection connection = dbConnection.getConnection();
            String query = "INSERT INTO users(fullname, email, password, phone, dob, gender, picture) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, fullname);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phone);
            stmt.setDate(5, Date.valueOf(dob)); // ⬅️ store LocalDate as SQL DATE
            stmt.setString(6, gender);

            // Image handling
            if (profilePictureFile == null || !profilePictureFile.exists()) {
                profilePictureFile = new File("src/icons/profile-icon.jpg");
            }

            FileInputStream imageStream = new FileInputStream(profilePictureFile);
            stmt.setBinaryStream(7, imageStream, profilePictureFile.length());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "註冊成功！", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "註冊失敗。", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "資料庫錯誤: " + e.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static boolean checkLogin(String email, String password) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            try {
                String query = "SELECT * FROM `users` WHERE `email` = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    String hashedInput = hashPassword(password); // 🔐 hash the input
                    return hashedInput.equals(storedPassword);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email
                + "', phone='" + phoneNumber + "', dob=" + dob + ", gender='" + gender + "'}";
    }
}
