package text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class test extends JFrame {

    public test() {
        setTitle("購票須知");
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");

            // Make sure this path is valid relative to your resources directory
            URL termsUrl = getClass().getResource("/text/terms_notice.html");
            if (termsUrl == null) {
                throw new IOException("terms_notice.html not found in resources/terms/");
            }
            editorPane.setPage(termsUrl);

            JScrollPane scrollPane = new JScrollPane(editorPane);
            add(scrollPane, BorderLayout.CENTER);

        } catch (IOException e) {
            JLabel errorLabel = new JLabel("無法載入購票須知。");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(errorLabel, BorderLayout.CENTER);
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(test::new);
    }
}
