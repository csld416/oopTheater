package admin;

import admin.topBar.AdminTopBarPanel;
import global.CapsuleButton;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMainPage extends JFrame {

    public AdminMainPage() {
        setTitle("Admin Main Page");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        initTop();
        initMain();

        setVisible(true);
    }

    private void initTop() {
        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        add(topBar);
    }

    private void initMain() {
        JPanel centerPanel = new JPanel(null); // use null layout for absolute positioning
        centerPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        centerPanel.setBounds(0, UIConstants.TOP_BAR_HEIGHT,
                UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT);

        int buttonWidth = 180;
        int buttonHeight = 50;
        int spacingY = 30;
        int startY = 60;
        int centerX = (UIConstants.FRAME_WIDTH - buttonWidth) / 2;

        CapsuleButton btn1 = new CapsuleButton("電影管理",
                new Color(70, 130, 180), new Color(100, 149, 237), new Dimension(buttonWidth, buttonHeight));
        btn1.setBounds(centerX, startY, buttonWidth, buttonHeight);
        btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btn1);
                frame.dispose(); // close current AdminMainPage
                new MovieInfoPage(); // open movie management panel
            }
        });

        CapsuleButton btn2 = new CapsuleButton("上檔/下檔",
                new Color(60, 179, 113), new Color(144, 238, 144), new Dimension(buttonWidth, buttonHeight));
        btn2.setBounds(centerX, startY + (buttonHeight + spacingY), buttonWidth, buttonHeight);

        CapsuleButton btn3 = new CapsuleButton("營收管理",
                new Color(255, 140, 0), new Color(255, 165, 0), new Dimension(buttonWidth, buttonHeight));
        btn3.setBounds(centerX, startY + 2 * (buttonHeight + spacingY), buttonWidth, buttonHeight);
        btn3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btn1);
                frame.dispose(); // close current AdminMainPage
                new RevenuePage(); // open movie management panel
            }
        });

        CapsuleButton btn4 = new CapsuleButton("場次管理",
                new Color(138, 43, 226), new Color(186, 85, 211), new Dimension(buttonWidth, buttonHeight));
        btn4.setBounds(centerX, startY + 3 * (buttonHeight + spacingY), buttonWidth, buttonHeight);

        CapsuleButton btn5 = new CapsuleButton("操作紀錄",
                new Color(220, 20, 60), new Color(255, 69, 0), new Dimension(buttonWidth, buttonHeight));
        btn5.setBounds(centerX, startY + 4 * (buttonHeight + spacingY), buttonWidth, buttonHeight);

        centerPanel.add(btn1);
        centerPanel.add(btn2);
        centerPanel.add(btn3);
        centerPanel.add(btn4);
        centerPanel.add(btn5);
        add(centerPanel);
    }

    public static void main(String[] args) {
        new AdminMainPage();
    }
}
