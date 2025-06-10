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
            new CapsuleButton("電影檔案", new Color(90, 108, 136), new Color(75, 91, 115), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("營收管理", new Color(160, 145, 100), new Color(140, 125, 85), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("場次管理", new Color(125, 110, 140), new Color(108, 94, 122), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("操作紀錄", new Color(160, 110, 115), new Color(140, 95, 100), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("影廳管理", new Color(150, 130, 110), new Color(130, 112, 95), new Dimension(buttonWidth, buttonHeight)),
            new CapsuleButton("餐點管理", new Color(175, 125, 155), new Color(155, 105, 135), new Dimension(buttonWidth, buttonHeight))
        };

        buttons[0].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dispose();
                new MovieRegisterPage();
            }
        });

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
