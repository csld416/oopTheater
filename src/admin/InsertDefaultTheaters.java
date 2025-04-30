package admin;

import connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsertDefaultTheaters {

    private static boolean theaterExists(Connection conn, String roomNum) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM Theaters WHERE room_num = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, roomNum);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        checkStmt.close();
        return count > 0;
    }

    public static void main(String[] args) {
        try {
            Connection conn = new DatabaseConnection().getConnection();

            String[][] theaters = {
                {"Room A", "大型廳"},
                {"Room B", "大型廳"},
                {"Room C", "小型廳"}
            };

            String insertSql = "INSERT INTO Theaters (room_num, room_type, is_active) VALUES (?, ?, 1)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);

            int inserted = 0;
            for (String[] theater : theaters) {
                if (!theaterExists(conn, theater[0])) {
                    insertStmt.setString(1, theater[0]);
                    insertStmt.setString(2, theater[1]);
                    insertStmt.executeUpdate();
                    inserted++;
                } else {
                    System.out.println("⚠ 已存在放映廳：" + theater[0]);
                }
            }

            insertStmt.close();
            conn.close();
            System.out.println("✅ 插入完成，共新增 " + inserted + " 筆資料");

        } catch (Exception e) {
            System.err.println("❌ 錯誤：" + e.getMessage());
            e.printStackTrace();
        }
    }
}