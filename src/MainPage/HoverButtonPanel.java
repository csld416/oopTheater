package MainPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButtonPanel extends JPanel {

    private final Color defaultColor;
    private final Color hoverColor;

    public HoverButtonPanel(String text, Color defaultColor, Color hoverColor) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;

        setLayout(new GridBagLayout()); // center text
        setBackground(defaultColor);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));

        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        add(label);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }
        });
    }
}