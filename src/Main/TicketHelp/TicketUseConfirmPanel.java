package Main.TicketHelp;

import Data.Order;
import Qrcode.QRImageLoader;
import global.CapsuleButton;
import global.DimLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicketUseConfirmPanel extends JFrame {

    private final int ARC = 30;

    public TicketUseConfirmPanel(JFrame parentFrame, DimLayer dimLayer, Order order, Runnable onConfirm) {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setSize(360, 400);
        setLocationRelativeTo(parentFrame);
        setContentPane(new RoundedPanel());
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        int y = 20;

        JLabel title = new JLabel("驗票確認", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBounds(0, y, getWidth(), 30);
        add(title);

        y += 50;
        ImageIcon qrIcon = QRImageLoader.getRandomQRCode();
        if (qrIcon != null) {
            Image scaled = qrIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel qrLabel = new JLabel(new ImageIcon(scaled));
            qrLabel.setBounds((getWidth() - 200) / 2, y, 200, 200);
            add(qrLabel);
        } else {
            JLabel error = new JLabel("無法載入 QR Code", SwingConstants.CENTER);
            error.setBounds(0, y, getWidth(), 200);
            add(error);
        }

        y += 220;
        CapsuleButton confirmBtn = new CapsuleButton("確認使用", new Color(120, 180, 140), new Color(100, 160, 120), new Dimension(120, 40));
        confirmBtn.setBounds((getWidth() - 120) / 2, y, 120, 40);
        confirmBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                dimLayer.setVisible(false);
                Toolkit.getDefaultToolkit().removeAWTEventListener(globalListener);
                if (onConfirm != null) {
                    onConfirm.run();
                }
            }
        });
        add(confirmBtn);

        // === Dismiss on outside click ===
        globalListener = new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    Window focused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                    if (focused != TicketUseConfirmPanel.this) {
                        dispose();
                        dimLayer.setVisible(false);
                        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                    }
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(globalListener, AWTEvent.MOUSE_EVENT_MASK);

        setVisible(true);
    }

    private AWTEventListener globalListener;

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
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC);
            g2.dispose();
        }
    }
}