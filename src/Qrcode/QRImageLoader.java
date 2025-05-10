package Qrcode;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class QRImageLoader {
    private static final String QR_FOLDER = "src/Qrcode/random";

    public static ImageIcon getRandomQRCode() {
        File dir = new File(QR_FOLDER);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

        if (files == null || files.length == 0) {
            System.err.println("❌ No QR code images found in " + QR_FOLDER);
            return null;
        }

        File selected = files[new Random().nextInt(files.length)];
        return new ImageIcon(selected.getAbsolutePath());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Random QR Code");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 300);
            frame.setLayout(new BorderLayout());

            ImageIcon qrIcon = getRandomQRCode();
            if (qrIcon != null) {
                JLabel label = new JLabel(qrIcon);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                frame.add(label, BorderLayout.CENTER);
            } else {
                frame.add(new JLabel("No QR image found."), BorderLayout.CENTER);
            }

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}