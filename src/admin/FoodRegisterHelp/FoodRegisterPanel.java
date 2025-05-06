package admin.FoodRegisterHelp;

import connection.DatabaseConnection;
import global.CapsuleButton;
import global.ToggleButtonPanel;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FoodRegisterPanel extends JPanel {

    private final JTextField nameField;
    private final JTextField priceField;
    private final JComboBox<String> categoryCombo;
    private final ToggleButtonPanel toggle;
    private final JTextField imgField;

    private final JFrame substrateFrame;

    public FoodRegisterPanel(JFrame substrateFrame) {
        this.substrateFrame = substrateFrame;

        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setSize(500, 420);
        setBounds(
                (substrateFrame.getWidth() - 500) / 2,
                (substrateFrame.getHeight() - 420) / 2,
                500, 420
        );

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        int xLabel = 30;
        int xField = 130;
        int y = 60;
        int height = 30;
        int gap = 50;

        JLabel titleLabel = new JLabel("新增品項", SwingConstants.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(UIConstants.COLOR_MAIN_GREEN);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        titleLabel.setBounds(0, 0, 500, 50);
        add(titleLabel);

        add(label("品項名稱：", xLabel, y, height, labelFont));
        nameField = field(xField, y, 300, height);
        add(nameField);

        y += gap;
        add(label("價格 (NT$)：", xLabel, y, height, labelFont));
        priceField = field(xField, y, 300, height);
        add(priceField);

        y += gap;
        add(label("分類：", xLabel, y, height, labelFont));
        categoryCombo = new JComboBox<>(new String[]{"飲料類", "熱食類", "爆米花類"});
        categoryCombo.setBounds(xField, y, 150, height);
        add(categoryCombo);

        y += gap;
        add(label("是否上架：", xLabel, y, height, labelFont));
        toggle = new ToggleButtonPanel(true);
        toggle.setBounds(xField, y, 50, height);
        add(toggle);

        y += gap;
        add(label("圖片路徑：", xLabel, y, height, labelFont));
        imgField = new JTextField();
        imgField.setBounds(xField, y, 220, height);
        add(imgField);

        CapsuleButton browseBtn = new CapsuleButton("Browse",
                new Color(105, 105, 105), new Color(160, 160, 160),
                new Dimension(80, 30), 14);
        browseBtn.setBounds(xField + 230, y, 80, height);
        browseBtn.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(substrateFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    imgField.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });
        add(browseBtn);

        y += gap + 10;

        CapsuleButton submitBtn = new CapsuleButton("新增",
                new Color(70, 130, 180), new Color(100, 149, 237),
                new Dimension(100, 40));
        submitBtn.setBounds(270, y, 100, 40);
        submitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!validateFields()) {
                    return;
                }
                insertToDatabase();
            }
        });
        add(submitBtn);

        CapsuleButton backBtn = new CapsuleButton("回上頁",
                new Color(169, 169, 169), new Color(128, 128, 128),
                new Dimension(100, 40));
        backBtn.setBounds(130, y, 100, 40);
        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                closeModal();
            }
        });
        add(backBtn);
    }

    private JLabel label(String text, int x, int y, int height, Font font) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, height);
        label.setFont(font);
        return label;
    }

    private JTextField field(int x, int y, int width, int height) {
        JTextField field = new JTextField();
        field.setBounds(x, y, width, height);
        return field;
    }

    private boolean validateFields() {
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            showErrorMessage("請填寫品項名稱與價格！");
            return false;
        }

        try {
            int price = Integer.parseInt(priceText);
            if (price < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showErrorMessage("價格必須為正整數！");
            return false;
        }

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT COUNT(*) FROM food WHERE name = ? AND category = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, categoryCombo.getSelectedIndex());

            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            stmt.close();
            conn.close();

            if (count > 0) {
                showErrorMessage("該品項已存在！");
                return false;
            }
        } catch (Exception e) {
            showErrorMessage("檢查重複時錯誤：" + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void insertToDatabase() {
        try {
            Connection conn = new DatabaseConnection().getConnection();

            String sql = "INSERT INTO food (name, price, category, valid, image_path) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText().trim());
            stmt.setInt(2, Integer.parseInt(priceField.getText().trim()));
            stmt.setInt(3, categoryCombo.getSelectedIndex());
            stmt.setBoolean(4, toggle.isOn());
            stmt.setString(5, imgField.getText().trim());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showSuccessMessage("新增成功！");
                closeModal();
            } else {
                showErrorMessage("新增失敗，請重試。");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            showErrorMessage("資料庫錯誤：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeModal() {
        JPanel glass = (JPanel) substrateFrame.getGlassPane();
        glass.setVisible(false);
        glass.removeAll();
        glass.repaint();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "錯誤", JOptionPane.ERROR_MESSAGE);
    }

    public static void showModal(JFrame substrate) {
        JPanel glass = (JPanel) substrate.getGlassPane();
        glass.setVisible(true);
        glass.setOpaque(false);
        glass.setLayout(null);
        glass.removeAll();

        JPanel dim = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        dim.setBounds(0, 0, substrate.getWidth(), substrate.getHeight());
        dim.setOpaque(false);
        dim.setLayout(null);

        dim.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Component clicked = SwingUtilities.getDeepestComponentAt(glass, e.getX(), e.getY());
                if (clicked == dim) {
                    glass.setVisible(false);
                    glass.removeAll();
                    glass.repaint();
                }
            }
        });

        glass.add(dim);
        glass.add(new FoodRegisterPanel(substrate));
        glass.repaint();
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "錯誤", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "成功", JOptionPane.INFORMATION_MESSAGE);
    }
}
