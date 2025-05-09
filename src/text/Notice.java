package text;

import GlobalConst.Const;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Notice extends JFrame {

    public Notice(JFrame substrateFrame) {
        setTitle("購票須知");
        setSize(700, 460);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(substrateFrame);

        // === Load HTML content ===
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        try {
            URL url = getClass().getResource("Notice.html");
            editorPane.setPage(url);
        } catch (Exception e) {
            editorPane.setText("<html><body><h2>無法載入購票須知。</h2></body></html>");
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setContentPane(scrollPane);

        // === Click outside to dispose ===
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                    if (focusedWindow != Notice.this) {
                        Notice.this.dispose();
                        substrateFrame.getGlassPane().setVisible(false);
                        Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame dummyFrame = new JFrame();
        dummyFrame.setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dummyFrame.setVisible(true);
        dummyFrame.setLocationRelativeTo(null);
        new Notice(dummyFrame);
    }
}
