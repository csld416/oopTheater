package admin;

import admin.FoodRegisterHelp.FoodEntry;
import admin.topBar.AdminTopBarPanel;
import global.CapsuleButton;
import global.UIConstants;
import Data.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.border.LineBorder;

import admin.FoodRegisterHelp.FoodRegisterPanel;
import global.DimLayer;

public class FoodRegisterPage extends JFrame {

    private int selectedCategory = 0; // 0: 飲料類, 1: 熱食類, 2: 爆米花類
    private final int CATEGORY_WIDTH = 380;
    private final int CATEGORY_HEIGHT = 50;
    private final int MIDDLE_HEIGHT = 580;

    private JScrollPane foodEntryScrollPane;
    private JPanel topPanel, middlePanel, bottomPanel;

    public FoodRegisterPage() {
        setTitle("餐點管理");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        initTopPanel();
        initMiddlePanel();
        initBottomPanel();

        setVisible(true);
    }

    private void initTopPanel() {
        topPanel = new JPanel(null);
        topPanel.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        add(topPanel);

        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topPanel.add(topBar);
    }

    private void initMiddlePanel() {
        middlePanel = new JPanel(null);
        middlePanel.setBounds(0, UIConstants.TOP_BAR_HEIGHT, UIConstants.FRAME_WIDTH, MIDDLE_HEIGHT);
        middlePanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        add(middlePanel);

        String[] categories = {"飲料類", "熱食類", "爆米花類"};
        boolean[] toggled = {true, false, false};
        JLabel[] labels = new JLabel[categories.length];

        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        categoryPanel.setPreferredSize(new Dimension(CATEGORY_WIDTH, CATEGORY_HEIGHT));
        categoryPanel.setBackground(new Color(230, 230, 230));

        Font font = new Font("SansSerif", Font.PLAIN, 14);

        for (int i = 0; i < categories.length; i++) {
            final int index = i;
            JLabel label = new JLabel(categories[i], SwingConstants.CENTER);
            label.setFont(font);
            label.setOpaque(true);
            label.setBackground(toggled[i] ? new Color(185, 128, 109) : new Color(230, 230, 230));
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < labels.length; j++) {
                        labels[j].setBackground(new Color(230, 230, 230));
                        toggled[j] = false;
                    }
                    toggled[index] = true;
                    label.setBackground(new Color(185, 128, 109));
                    selectedCategory = index;
                    loadFoodEntries(); // re-render
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!toggled[index]) {
                        label.setBackground(new Color(210, 210, 210));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!toggled[index]) {
                        label.setBackground(new Color(230, 230, 230));
                    }
                }
            });

            labels[i] = label;
            categoryPanel.add(label);
        }

        CapsuleButton addButton = new CapsuleButton("新增品項",
                new Color(70, 130, 180), new Color(100, 149, 237),
                new Dimension(100, 40));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = FoodRegisterPage.this;

                DimLayer dim = new DimLayer(frame);
                dim.setSize(frame.getSize());

                // Inject modal panel into dim layer
                FoodRegisterPanel modal = new FoodRegisterPanel(frame);
                dim.add(modal);

                // Set and show glass pane
                frame.setGlassPane(dim);
                dim.setVisible(true);
            }
        });

        // === Container for categoryPanel + spacing + addButton ===
        JPanel horizontalContainer = new JPanel(null);
        int totalWidth = CATEGORY_WIDTH + 30 + 100;
        int xOffset = (UIConstants.FRAME_WIDTH - totalWidth) / 2;
        horizontalContainer.setBounds(xOffset, 20, totalWidth, CATEGORY_HEIGHT);
        horizontalContainer.setOpaque(false);

        categoryPanel.setBounds(0, 0, CATEGORY_WIDTH, CATEGORY_HEIGHT);
        addButton.setBounds(CATEGORY_WIDTH + 30, 5, 100, 40);

        horizontalContainer.add(categoryPanel);
        horizontalContainer.add(addButton);
        middlePanel.add(horizontalContainer);
    }

    private void initBottomPanel() {
        foodEntryScrollPane = new JScrollPane();
        foodEntryScrollPane.setBounds(50, 90, UIConstants.FRAME_WIDTH - 100,
                UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT - 110);
        foodEntryScrollPane.setBorder(null);
        foodEntryScrollPane.setBackground(UIConstants.COLOR_MAIN_LIGHT);
        foodEntryScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        foodEntryScrollPane.setBorder(new LineBorder(Color.BLACK));

        middlePanel.add(foodEntryScrollPane);
        loadFoodEntries();
    }

    private void loadFoodEntries() {
        ArrayList<Food> all = Food.getAllFoods();
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(UIConstants.COLOR_MAIN_LIGHT);

        int count = 0;
        for (Food f : all) {
            if (f.getCategory() == selectedCategory) {
                container.add(new FoodEntry(f));
                count++;
            }
        }

        if (count == 0) {
            JLabel emptyLabel = new JLabel("此分類尚未有資料", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(Box.createVerticalStrut(40));
            container.add(emptyLabel);
        }

        container.add(Box.createVerticalGlue());
        foodEntryScrollPane.setViewportView(container);
    }

    public static void main(String[] args) {
        new FoodRegisterPage();
    }
}
