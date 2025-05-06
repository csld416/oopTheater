package admin.theaterManagehelp;

import Data.Theater;
import admin.TheaterManagePage;
import global.CapsuleButton;
import global.UIConstants;
import connection.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheaterRegisterPanel extends JPanel {

    private JTextField nameField;
    private JComboBox<String> typeBox;

    private boolean isEditing = false;
    private Theater editingTheater;

    public TheaterRegisterPanel(Theater theater) {
        this(); // call default constructor
        this.isEditing = true;
        this.editingTheater = theater;

        nameField.setText(theater.getName());
        nameField.setEditable(false); // prevent name modification if needed
        typeBox.setSelectedItem(theater.getType());
    }

    public TheaterRegisterPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(UIConstants.FRAME_WIDTH - 300, UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT));
        setBackground(UIConstants.COLOR_MAIN_LIGHT);

        // === Title Bar ===
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, UIConstants.FRAME_WIDTH - 300, 30);
        titlePanel.setBackground(UIConstants.COLOR_MAIN_GREEN);

        JLabel title = new JLabel("Theater Registration", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        titlePanel.add(title);
        add(titlePanel);

        int labelWidth = 100;
        int fieldWidth = 250;
        int height = 30;
        int gapY = 20;
        int baseY = 100;
        int centerX = (getPreferredSize().width - fieldWidth) / 2 - labelWidth / 2;

        JLabel nameLabel = new JLabel("名稱:");
        nameLabel.setBounds(centerX, baseY, labelWidth, height);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(centerX + labelWidth, baseY, fieldWidth, height);
        add(nameField);

        JLabel typeLabel = new JLabel("類型:");
        typeLabel.setBounds(centerX, baseY + (height + gapY), labelWidth, height);
        add(typeLabel);

        typeBox = new JComboBox<>(new String[]{"小廳", "大廳"});
        typeBox.setBounds(centerX + labelWidth, baseY + (height + gapY), fieldWidth, height);
        add(typeBox);

        CapsuleButton saveButton = new CapsuleButton("儲存",
                new Color(76, 175, 80), new Color(139, 195, 74), new Dimension(150, 40));
        saveButton.setBounds((getPreferredSize().width - 150) / 2, baseY + 2 * (height + gapY) + 20, 150, 40);
        add(saveButton);

        // === Save button logic ===
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String name = nameField.getText().trim();
                String type = (String) typeBox.getSelectedItem();
                int capacity = type.equals("小廳") ? 144 : 407;
                boolean isActive = true; // default to true (can be modified if you later expose a toggle)

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "請輸入影廳名稱。");
                    return;
                }

                try (Connection conn = new DatabaseConnection().getConnection()) {
                    String sql;
                    PreparedStatement stmt;

                    if (isEditing) {
                        sql = "UPDATE theaters SET type = ?, capacity = ?, is_active = ? WHERE id = ?";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, type);
                        stmt.setInt(2, capacity);
                        stmt.setBoolean(3, isActive);  // Use correct value if toggle is added
                        stmt.setInt(4, editingTheater.getId());
                    } else {
                        if (!isValidTheaterName(name)) {
                            JOptionPane.showMessageDialog(null, "影廳名稱已存在，請更換名稱。");
                            return;
                        }

                        sql = "INSERT INTO theaters (name, type, capacity, is_active) VALUES (?, ?, ?, ?)";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, name);
                        stmt.setString(2, type);
                        stmt.setInt(3, capacity);
                        stmt.setBoolean(4, isActive); // again, toggle-able if needed
                    }

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, isEditing ? "影廳更新成功！" : "影廳新增成功！");
                    Window window = SwingUtilities.getWindowAncestor(TheaterRegisterPanel.this);
                    if (window instanceof TheaterManagePage) {
                        ((TheaterManagePage) window).refreshList();
                    }
                    if (!isEditing) {
                        nameField.setText("");
                        typeBox.setSelectedIndex(0);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "操作失敗，請檢查資料庫連線。\n" + ex.getMessage());
                }
            }
        });
    }

    // === Validate if theater name already exists ===
    private boolean isValidTheaterName(String name) {
        try {
            DatabaseConnection db = new DatabaseConnection();  // instantiate
            try (Connection conn = db.getConnection()) {
                String sql = "SELECT COUNT(*) FROM theaters WHERE name = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
