package text;

import Data.*;
import MovieBooking.PayPage;
import global.CapsuleButton;
import global.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.AbstractMap;
import java.util.ArrayList;

public class Confirm extends JFrame {

    private final int arc = 40; // 圓角半徑

    public Confirm(JFrame substrateFrame, Order order) {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setSize(360, 160);
        setLocationRelativeTo(substrateFrame);
        setContentPane(new RoundedPanel());
        setBackground(new Color(0, 0, 0, 0)); // <-- 讓外框透明
        setContentPane(new RoundedPanel());

        setLayout(null);

        int y = 15;
        JLabel title = new JLabel("提醒", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 23));
        title.setBounds(0, y, getWidth(), 30);
        add(title);

        y += 45;
        JLabel label = new JLabel("您已經詳讀購票須知及行動支付服務條款", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setBounds(0, y, getWidth(), 25);
        add(label);

        y += 40;
        CapsuleButton confirmBtn = new CapsuleButton("是", UIConstants.COLOR_MAIN_GREEN, new Color(60, 130, 125), new Dimension(60, 35));
        confirmBtn.setBounds(190, y, 60, 35);
        add(confirmBtn);

        CapsuleButton cancelBtn = new CapsuleButton("否", new Color(204, 204, 204), new Color(180, 180, 180), new Dimension(60, 35));
        cancelBtn.setBounds(260, 100, 60, 35);
        add(cancelBtn);

        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
                new PayPage(order);
            }
        });

        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
            }
        });

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                    if (focusedWindow != Confirm.this) {
                        Confirm.this.dispose();
                        substrateFrame.getGlassPane().setVisible(false);
                        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        setVisible(true);
    }

    // Inner panel with rounded corners
    private class RoundedPanel extends JPanel {

        RoundedPanel() {
            setOpaque(false);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.setColor(new Color(230, 230, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame dummy = new JFrame();
        dummy.setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dummy.setVisible(true);
        new Confirm(dummy, Order.dummyOrder);
    }
}
