package Data;

import connection.DatabaseConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import javax.swing.JOptionPane;

public class User {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private int age;
    private String gender;

    public static User currUser = null;

    public static final User dummyUser;

    static {
        dummyUser = new User(
                0,
                "測試用戶",
                "dummy@example.com",
                "0912345678",
                20,
                "Male"
        );
    }

    // === Constructor ===
    public User(int id, String name, String email, String phoneNumber, int age, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
    }

    // === Session Management (via SessionManager) ===
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

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
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

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // === Clear User Info (optional) ===
    public void clear() {
        this.id = -1;
        this.name = null;
        this.email = null;
        this.phoneNumber = null;
        this.age = 0;
        this.gender = null;
    }

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
                    String phoneNumber = rs.getString("phone");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    return new User(id, name, email, phoneNumber, age, gender);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void registerUser(String fullname, String email, String password, String phone, int age, String gender, File profilePictureFile) {
        DatabaseConnection dbConnection = new DatabaseConnection();

        try {
            Connection connection = dbConnection.getConnection();
            String query = "INSERT INTO `users`(`fullname`, `email`, `password`, `phone`, `age`, `gender`, `picture`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepareStatement = connection.prepareStatement(query);

            prepareStatement.setString(1, fullname);
            prepareStatement.setString(2, email);
            prepareStatement.setString(3, password);
            prepareStatement.setString(4, phone);
            prepareStatement.setInt(5, age);
            prepareStatement.setString(6, gender);

            // Profile picture fallback
            if (profilePictureFile == null || !profilePictureFile.exists()) {
                profilePictureFile = new File("src/icons/profile-icon.jpg");
            }

            FileInputStream fileStream = new FileInputStream(profilePictureFile);
            prepareStatement.setBinaryStream(7, fileStream, profilePictureFile.length());

            int rowsAffected = prepareStatement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "註冊成功！", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "註冊失敗。", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL 錯誤: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (FileNotFoundException eex) {
            JOptionPane.showMessageDialog(null, "圖片檔案未找到: " + eex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            eex.printStackTrace();
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
                    return password.equals(storedPassword);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // === Debug String ===
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email
                + "', phone='" + phoneNumber + "', age=" + age + ", gender='" + gender + "'}";
    }
}
