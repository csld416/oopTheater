package Main.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class CustomComboBox extends JPanel {

    private final JLabel selectedLabel;
    private final JPanel dropdownPanel;
    private boolean isOpen = false;

    public CustomComboBox(String[] options, Consumer<String> onSelect) {
        setLayout(null);
        setBackground(Color.WHITE);
        //setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(200, 30));
        setMaximumSize(new Dimension(200, 30));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new BorderLayout());

        selectedLabel = new JLabel(options[0] + " ▼");
        selectedLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(selectedLabel, BorderLayout.CENTER);

        // Dropdown list panel
        dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.Y_AXIS));
        dropdownPanel.setBackground(Color.WHITE);
        dropdownPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        for (String option : options) {
            JLabel item = new JLabel(option);
            item.setOpaque(true);
            item.setBackground(Color.WHITE);
            item.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            item.setPreferredSize(new Dimension(200, 30));
            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    item.setBackground(new Color(230, 230, 230));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    item.setBackground(Color.WHITE);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    selectedLabel.setText(option + " ▼");
                    onSelect.accept(option);
                    closeDropdown();
                }
            });
            dropdownPanel.add(item);
        }

        selectedLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleDropdown();
            }
        });
    }

    private void toggleDropdown() {
        if (isOpen) {
            closeDropdown();
        } else {
            openDropdown();
        }
    }

    private void openDropdown() {
        Container root = SwingUtilities.getRootPane(this);
        if (root instanceof JRootPane rootPane) {
            JLayeredPane layeredPane = rootPane.getLayeredPane();
            Point location = getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(location, layeredPane);

            dropdownPanel.setBounds(location.x, location.y + getHeight(), getWidth(), dropdownPanel.getComponentCount() * 30);
            layeredPane.add(dropdownPanel, JLayeredPane.POPUP_LAYER);
            dropdownPanel.setVisible(true);
            isOpen = true;
        }
    }

    private void closeDropdown() {
        if (dropdownPanel.getParent() != null) {
            dropdownPanel.getParent().remove(dropdownPanel);
            dropdownPanel.getParent().repaint();
        }
        isOpen = false;
    }

    public String getSelectedItem() {
        return selectedLabel.getText().replace(" ▼", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom ComboBox Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(null);

            JLayeredPane layeredPane = frame.getLayeredPane();

            CustomComboBox comboBox = new CustomComboBox(
                    new String[]{"Default", "上映日期 ⬆", "上映日期 ⬇", "片長 ⬆", "片長 ⬇"},
                    option -> System.out.println("選擇: " + option)
            );
            comboBox.setBounds(100, 100, 200, 30);

            layeredPane.add(comboBox, JLayeredPane.DEFAULT_LAYER);
            frame.setVisible(true);
        });
    }
}
