package admin;

import admin.topBar.AdminTopBarPanel;
import global.CapsuleButton;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMainPage extends JFrame {

    public AdminMainPage() {
        setTitle("Admin Main Page");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        initTop();
        initMain();

        setVisible(true);
    }

    private void initTop() {
        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        add(topBar);
    }

    private void initMain() {
        JPanel centerPanel = new JPanel(null);
        centerPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        centerPanel.setBounds(0, Const.TOP_BAR_HEIGHT,
                Const.FRAME_WIDTH, Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT);

        int buttonWidth = 180;
        int buttonHeight = 50;
        int spacingX = 80;
        int spacingY = 40;
        int columns = 2;

        int totalButtonRows = 4;  // Max number of rows in either column
        int totalHeight = totalButtonRows * buttonHeight + (totalButtonRows - 1) * spacingY;
        int startY = (centerPanel.getHeight() - totalHeight) / 2;

        int totalWidth = buttonWidth * columns + spacingX;
        int startX = (Const.FRAME_WIDTH - totalWidth) / 2;

        CapsuleButton[] buttons = new CapsuleButton[]{
            new CapsuleButton("電影檔案", new Color(70, 130, 180), new Color(100, 149, 237), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("營收管理", new Color(255, 140, 0), new Color(255, 165, 0), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("場次管理", new Color(138, 43, 226), new Color(186, 85, 211), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("操作紀錄", new Color(220, 20, 60), new Color(255, 69, 0), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("影廳管理", new Color(139, 69, 19), new Color(160, 82, 45), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("餐點管理", new Color(255, 105, 180), new Color(255, 182, 193), new Dimension(buttonWidth, buttonHeight))
        };

        // Add listeners
        buttons[0].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dispose();
                new MovieRegisterPage();
            }
        });
//        buttons[1].addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent e) {
//                JOptionPane.showMessageDialog(null, "此頁面尚未開發。");
//            }
//        });
        buttons[1].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dispose();
                new RevenuePage();
            }
        });
        buttons[2].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    dispose();
                    new ShowtimeRegister();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "此頁面尚未開發。");
                }
            }
        });
        buttons[3].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "此頁面尚未開發。");
            }
        });
        buttons[4].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dispose();
                new TheaterManagePage();
            }
        });
        buttons[5].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dispose();
                new FoodRegisterPage();
            }
        });

        // Layout: split into 2 columns
        for (int i = 0; i < buttons.length; i++) {
            int col = i % 2;
            int row = i / 2;
            int x = startX + col * (buttonWidth + spacingX);
            int y = startY + row * (buttonHeight + spacingY);
            buttons[i].setBounds(x, y, buttonWidth, buttonHeight);
            centerPanel.add(buttons[i]);
        }

        add(centerPanel);
    }

    public static void main(String[] args) {
        new AdminMainPage();
    }
}
