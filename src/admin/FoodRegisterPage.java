package admin;

import admin.FoodRegisterHelp.FoodEntry;
import admin.topBar.AdminTopBarPanel;
import global.CapsuleButton;
import GlobalConst.Const;
import Data.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import admin.FoodRegisterHelp.FoodRegisterPanel;
import global.DimLayer;

public class FoodRegisterPage extends JFrame {

    private int selectedCategory = 0; // 0: 飲料類, 1: 熱食類, 2: 爆米花類
    private final int CATEGORY_WIDTH = 380;
    private final int CATEGORY_HEIGHT = 50;
    private final int MIDDLE_HEIGHT = 80;

    private JScrollPane foodEntryScrollPane;
    private JPanel topPanel, middlePanel, bottomPanel;
    private JPanel contentPanel;

    public FoodRegisterPage() {
        setTitle("餐點管理");
        setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
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
        topPanel.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        add(topPanel);

        AdminTopBarPanel topBar = new AdminTopBarPanel();
        topBar.setBounds(0, 0, Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT);
        topPanel.add(topBar);
    }

    private void initMiddlePanel() {
        middlePanel = new JPanel(null);
        middlePanel.setBounds(0, Const.TOP_BAR_HEIGHT, Const.FRAME_WIDTH, MIDDLE_HEIGHT);
        middlePanel.setBackground(Color.WHITE);
        add(middlePanel);

        String[] categories = {"飲料類", "熱食類", "爆米花類"};
        boolean[] toggled = {true, false, false};
        JLabel[] labels = new JLabel[categories.length];

        int labelWidth = CATEGORY_WIDTH / categories.length;
        int labelHeight = CATEGORY_HEIGHT;

        Font font = new Font("SansSerif", Font.PLAIN, 14);
        int x = (Const.FRAME_WIDTH - CATEGORY_WIDTH) / 2;
        int y = (MIDDLE_HEIGHT - CATEGORY_HEIGHT) / 2;

        for (int i = 0; i < categories.length; i++) {
            final int index = i;
            JLabel label = new JLabel(categories[i], SwingConstants.CENTER);
            label.setFont(font);
            label.setOpaque(true);
            label.setBackground(toggled[i] ? new Color(185, 128, 109) : new Color(230, 230, 230));
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.setBounds(x + i * labelWidth, y, labelWidth, labelHeight);

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
                    refreshFoodContent();
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
            middlePanel.add(label);
        }

        CapsuleButton addButton = new CapsuleButton("新增品項",
                new Color(70, 130, 180), new Color(100, 149, 237),
                new Dimension(100, 40));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.setBounds(
                x + CATEGORY_WIDTH + 30,
                y + (labelHeight - 40) / 2,
                100, 40
        );
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = FoodRegisterPage.this;
                DimLayer dim = new DimLayer(frame);
                dim.setSize(frame.getSize());
                FoodRegisterPanel modal = new FoodRegisterPanel(frame, () -> refreshFoodContent());
                dim.add(modal);
                frame.setGlassPane(dim);
                dim.setVisible(true);
            }
        });

        middlePanel.add(addButton);
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel(null);
        bottomPanel.setBounds(0, Const.TOP_BAR_HEIGHT + MIDDLE_HEIGHT, Const.FRAME_WIDTH,
                Const.FRAME_HEIGHT - Const.TOP_BAR_HEIGHT - MIDDLE_HEIGHT);
        bottomPanel.setBackground(Const.COLOR_MAIN_LIGHT);
        add(bottomPanel);

        // Inner content panel inside scrollpane
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Const.COLOR_MAIN_LIGHT);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        wrapper.setBackground(Const.COLOR_MAIN_LIGHT);
        wrapper.add(contentPanel);

        foodEntryScrollPane = new JScrollPane(wrapper,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        foodEntryScrollPane.setBounds(Const.FRAME_WIDTH / 2 - 250, 0, 500,
                bottomPanel.getHeight());
        foodEntryScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        foodEntryScrollPane.setBorder(null);
        foodEntryScrollPane.setViewportBorder(null);

        bottomPanel.add(foodEntryScrollPane);
        refreshFoodContent();
    }

    public void refreshFoodContent() {
        contentPanel.removeAll();

        ArrayList<Food> all = Food.getAllFoods();
        int count = 0;
        for (Food f : all) {
            if (f.getCategory() == selectedCategory) {
                contentPanel.add(new FoodEntry(f));
                contentPanel.add(Box.createVerticalStrut(8));
                count++;
            }
        }

        if (count == 0) {
            JLabel emptyLabel = new JLabel("此分類尚未有資料", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(Box.createVerticalStrut(40));
            contentPanel.add(emptyLabel);
        }

        contentPanel.add(Box.createVerticalGlue());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FoodRegisterPage::new);
    }
}