package MovieBooking.help;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedSeatPanel extends JPanel {

    private final Color defaultColor;
    private final Color hoverColor;
    private final Color pressedColor;
    private final Color textColor;
    private final String text;
    private final String row;
    private final boolean touchable;

    private Color currentColor;

    private static final int BASE_SIZE = 40;
    private static final double SCALE = 0.5;
    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    private boolean isFull = false;
    private boolean isSelected = false;

    public RoundedSeatPanel(Color defaultColor, Color hoverColor, Color pressedColor, Color textColor, String text, boolean touchable) {
        this(defaultColor, hoverColor, pressedColor, textColor, text, "", touchable);
    }

    public RoundedSeatPanel(Color defaultColor, Color hoverColor, Color pressedColor, Color textColor, String text, String row, boolean touchable) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        this.textColor = textColor;
        this.text = text;
        this.row = row;
        this.touchable = touchable;
        this.currentColor = defaultColor;

        int scaledSize = (int) (BASE_SIZE * SCALE);
        setPreferredSize(new Dimension(scaledSize, scaledSize));
        setOpaque(false);

        if (touchable) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isFull) {
                        if (isSelected) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        }
                    }else{
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                    if (!isFull && !isSelected) {
                        currentColor = hoverColor;
                        repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    if (!isSelected) {
                        currentColor = defaultColor;
                        repaint();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (!touchable) {
                        return;
                    }
                    if (isFull) {
                        if (!isSelected) {
                            return;
                        }
                        currentColor = defaultColor;
                        repaint();
                        System.out.println("Unselected" + getSeat());
                    } else {
                        isSelected = !isSelected;
                        currentColor = isSelected ? pressedColor : defaultColor;
                        repaint();
                        System.out.println((isSelected ? "Selected: " : "Unselected: ") + getSeat());
                    }
                }
            });
        }
    }

    public void setFull() {
        isFull = true;
    }

    public void disableFull() {
        isFull = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.scale(SCALE, SCALE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, BASE_SIZE, BASE_SIZE, ARC_WIDTH, ARC_HEIGHT);

        g2.setColor(textColor);
        Font font = new Font("SansSerif", Font.BOLD, 20);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int x = (BASE_SIZE - textWidth) / 2;
        int y = (BASE_SIZE - fm.getHeight()) / 2 + textHeight;

        g2.drawString(text, x, y);
        g2.dispose();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void reset() {
        isSelected = false;
        currentColor = defaultColor;
        repaint();
    }

    public void setSelectedState(boolean selected) {
        this.isSelected = selected;
        this.currentColor = selected ? pressedColor : defaultColor;
        repaint();
    }

    public boolean isTouchable() {
        return touchable;
    }

    public String getText() {
        return text;
    }

    public String getRow() {
        return row;
    }

    public String getSeat() {
        return row + "-" + text;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rounded Seat Toggle Demo (Scaled + Touchable + Custom Text Color)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        RoundedSeatPanel seat1 = new RoundedSeatPanel(Color.GRAY, Color.CYAN, Color.GREEN, Color.WHITE, "28", "A", true);
        RoundedSeatPanel seat2 = new RoundedSeatPanel(Color.DARK_GRAY, Color.GRAY, Color.GRAY, Color.YELLOW, "N/A", "", false);
        RoundedSeatPanel seat3 = new RoundedSeatPanel(Color.BLUE, Color.ORANGE, Color.RED, Color.BLACK, "1", "B", true);

        frame.add(seat1);
        frame.add(seat2);
        frame.add(seat3);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
