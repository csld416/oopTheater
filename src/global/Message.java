package global;

import global.CapsuleButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Message extends JFrame {

    private final int WIDTH = 320;
    private final int HEIGHT = 170;
    private final int ARC = 25;
    private final Color BACKGROUND = new Color(255, 255, 255, 240);
    private final Color BORDER_COLOR = new Color(180, 180, 180);

    public Message(JFrame substrateFrame, String messageText, Runnable onConfirm) {
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(substrateFrame);
        setAlwaysOnTop(true);
        setShape(new RoundRectangle2D.Double(0, 0, WIDTH, HEIGHT, ARC, ARC));
        setLayout(null);
        getContentPane().setBackground(new Color(0, 0, 0, 0));

        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC);
                g2.setColor(BACKGROUND);
                g2.fill(shape);
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(shape);
                g2.dispose();
            }
        };
        bubble.setLayout(null);
        bubble.setBounds(0, 0, WIDTH, HEIGHT);
        bubble.setOpaque(false);

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + messageText + "</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        messageLabel.setForeground(Color.DARK_GRAY);
        messageLabel.setBounds(30, 30, WIDTH - 60, 50);
        bubble.add(messageLabel);

        CapsuleButton confirmBtn = new CapsuleButton("確認", new Color(100, 180, 100), new Color(70, 160, 70), new Dimension(80, 35));
        confirmBtn.setBounds((WIDTH - 80) / 2, HEIGHT - 60, 80, 35);
        confirmBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        AWTEventListener listener = new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                    if (focusedWindow != Message.this) {
                        setVisible(false);
                        substrateFrame.getGlassPane().setVisible(false);
                        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                    }
                }
            }
        };

        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK);

        // Later when user confirms:
        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                substrateFrame.getGlassPane().setVisible(false);
                Toolkit.getDefaultToolkit().removeAWTEventListener(listener);  // ✅ this now works
                onConfirm.run(); // Trigger refund logic
            }
        });

        bubble.add(confirmBtn);
        add(bubble);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame substrate = new JFrame("Substrate");
        substrate.setSize(800, 600);
        substrate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        substrate.setLayout(null);
        substrate.setVisible(true);

        JButton trigger = new JButton("Show Message");
        trigger.setBounds(300, 250, 200, 40);
        trigger.addActionListener(e -> {
            DimLayer dim = new DimLayer(substrate);
            substrate.setGlassPane(dim);
            dim.setVisible(true);
            new Message(substrate, "是否確認退票？退票後將無法復原。", () -> System.out.println("✅ Refund logic executed!"));
        });

        substrate.add(trigger);
    }
}
