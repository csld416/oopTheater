package admin.topBar;

import Main.help.topbarPanel.ToggleListPanel;
import Main.help.topbarPanel.LogoPanel;
import Main.help.topbarPanel.StaffPanel;
import Main.ToggleListPage;
import admin.AdminMainPage;
import global.DimLayer;
import GlobalConst.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminTopBarPanel extends JPanel {

    public AdminTopBarPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(Const.FRAME_WIDTH, Const.TOP_BAR_HEIGHT));
        setBackground(new Color(169, 183, 198));

        // === Logo ===
        LogoPanel logo = new LogoPanel();
        int verticalPadding = (Const.TOP_BAR_HEIGHT - Const.LOGO_HEIGHT) / 2;
        logo.setBounds(30, verticalPadding, Const.LOGO_WIDTH, Const.LOGO_HEIGHT);
        logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(logo);

        // === Right-side Panels ===
        int panelWidth = Const.ICON_PANEL_WIDTH;
        int panelHeight = Const.ICON_PANEL_HEIGHT;
        int spacing = 10;
        int edgePadding = 35;
        int toggleWidth = Const.TOGGLE_ICON_WIDTH;
        int startX = Const.FRAME_WIDTH - edgePadding - toggleWidth;

        // Toggle List Panel
        ToggleListPanel toggle = new ToggleListPanel();
        toggle.setBounds(startX, 35, toggleWidth, 40);
        toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggle.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(AdminTopBarPanel.this);
                DimLayer dim = new DimLayer(frame);
                frame.setGlassPane(dim);
                dim.setVisible(true);
                new ToggleListPage(frame);
            }
        });
        add(toggle);

        // Staff Button
        startX -= (panelWidth + spacing);
        StaffPanel staff = new StaffPanel();
        staff.setBounds(startX, 40, panelWidth, panelHeight);
        staff.setCursor(new Cursor(Cursor.HAND_CURSOR));
        staff.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                staff.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e){
                staff.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(AdminTopBarPanel.this);
                frame.dispose();
                new AdminMainPage();
            }
        });
        add(staff);
    }
}
