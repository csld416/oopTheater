package Qrcode;

import java.io.File;
import java.util.Random;
import javax.swing.ImageIcon;

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
}