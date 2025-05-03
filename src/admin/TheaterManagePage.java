package admin;

import Data.Theater;
import admin.topBar.AdminTopBarPanel;
import admin.theaterManagehelp.TheaterSlot;
import admin.theaterManagehelp.TheaterRegisterPanel;
import connection.DatabaseConnection;
import global.CapsuleButton;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TheaterManagePage extends JFrame {

    private final int LEFT_WIDTH = 300;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel theaterListPanel;

    private final Color LEFT_PANEL_COLOR = new Color(245, 245, 245);

    public TheaterManagePage() {
        setTitle("影廳管理");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        initTop();
        initLeft();
        initRight();

        setVisible(true);
    }

    private void initTop() {
        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        add(topBar);
    }

    private void initLeft() {
        int panelHeight = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, UIConstants.TOP_BAR_HEIGHT, LEFT_WIDTH, panelHeight);
        leftPanel.setBackground(LEFT_PANEL_COLOR);

        JLabel listTitle = new JLabel("影廳一覽", SwingConstants.LEFT);
        listTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        listTitle.setBounds(30, 20, 300, 30);
        leftPanel.add(listTitle);

        CapsuleButton addTheaterButton = new CapsuleButton(
                "新增影廳",
                new Color(100, 149, 237),
                new Color(65, 105, 225),
                new Dimension(100, 30)
        );
        addTheaterButton.setBounds(160, 20, 100, 30);
        addTheaterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                loadRegisterPanel();
            }
        });
        leftPanel.add(addTheaterButton);

        theaterListPanel = new JPanel(null);
        theaterListPanel.setBackground(LEFT_PANEL_COLOR);

        JScrollPane scrollPane = new JScrollPane(theaterListPanel);
        scrollPane.setBounds(30, 60, LEFT_WIDTH - 60, panelHeight - 80);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        leftPanel.add(scrollPane);

        loadTheatersFromDB();
        add(leftPanel);
    }

    private void initRight() {
        int rightWidth = UIConstants.FRAME_WIDTH - LEFT_WIDTH;
        int panelHeight = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        rightPanel = new JPanel(null);
        rightPanel.setBounds(LEFT_WIDTH, UIConstants.TOP_BAR_HEIGHT, rightWidth, panelHeight);
        rightPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);

        JLabel placeholder = new JLabel("請選擇左側操作或新增影廳", SwingConstants.CENTER);
        placeholder.setFont(new Font("SansSerif", Font.ITALIC, 16));
        placeholder.setBounds(0, panelHeight / 2 - 15, rightWidth, 30);
        rightPanel.add(placeholder);

        add(rightPanel);
    }

    private void loadRegisterPanel() {
        int rightWidth = UIConstants.FRAME_WIDTH - LEFT_WIDTH;
        int panelHeight = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        rightPanel.removeAll();
        TheaterRegisterPanel registerPanel = new TheaterRegisterPanel();
        registerPanel.setBounds(0, 0, rightWidth, panelHeight);
        rightPanel.add(registerPanel);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void loadRegisterPanel(Theater theater) {
        int rightWidth = UIConstants.FRAME_WIDTH - LEFT_WIDTH;
        int panelHeight = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

        rightPanel.removeAll();
        TheaterRegisterPanel registerPanel = new TheaterRegisterPanel(theater);
        registerPanel.setBounds(0, 0, rightWidth, panelHeight);
        rightPanel.add(registerPanel);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void loadTheatersFromDB() {
        int x = 0;
        int y = 0;
        int slotHeight = 60;
        int slotWidth = LEFT_WIDTH - 60;

        theaterListPanel.removeAll();

        ArrayList<Theater> theaters = Theater.fetchTheaterList();
        int count = 0;

        for (Theater t : theaters) {
            TheaterSlot slot = new TheaterSlot(t.getName(), t.isActive());
            slot.setBounds(x, y, slotWidth, slotHeight);

            // Set the onClick behavior
            slot.setOnClick(() -> loadRegisterPanel(t));

            theaterListPanel.add(slot);
            y += slotHeight + 10;
            count++;
        }

        theaterListPanel.setPreferredSize(new Dimension(slotWidth, count * (slotHeight + 10)));
        theaterListPanel.revalidate();
        theaterListPanel.repaint();
    }

    public void refreshList() {
        Theater.refreshTheaterList();   // Clear cached list
        loadTheatersFromDB();           // Reload into panel
    }

    public static void main(String[] args) {
        new TheaterManagePage();
    }
}
