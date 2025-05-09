package Main;

import GlobalConst.Const;
import Main.help.topbarPanel.MyTicketSpacePanel;
import Main.help.topbarPanel.ToggleListPanel;
import Main.help.topbarPanel.LogoPanel;
import Main.help.topbarPanel.LatestNewsPanel;
import Main.help.topbarPanel.PersonalSpacePanel;
import UserSpace.PersonalSpacePage;
import LoginRegisterForm.*;
import global.DimLayer;
import Data.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopBarPanel extends JPanel {

    public TopBarPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(Const.FRAME_WIDTH, Const.FRAME_HEIGHT));
        setBackground(new Color(169, 183, 198));

        // Logo Panel (redirects to StartingPage)
        LogoPanel logo = new LogoPanel();
        int verticalPadding = (Const.TOP_BAR_HEIGHT - Const.LOGO_HEIGHT) / 2;
        logo.setBounds(30, verticalPadding, Const.LOGO_WIDTH, Const.LOGO_HEIGHT);
        add(logo);

        // Function Panels from right to left
        int panelWidth = Const.ICON_PANEL_WIDTH;
        int panelHeight = Const.ICON_PANEL_HEIGHT;
        int spacing = 10;
        int toggleWidth = Const.TOGGLE_ICON_WIDTH;
        int edgePadding = 35;
        int startX = Const.FRAME_WIDTH - edgePadding - toggleWidth;

        // Toggle List Panel
        ToggleListPanel toggle = new ToggleListPanel();
        toggle.setBounds(startX, 35, toggleWidth, 40);
        toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggle.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TopBarPanel.this);
                DimLayer dim = new DimLayer(frame);
                frame.setGlassPane(dim);
                dim.setVisible(true);
                new ToggleListPage(frame);
            }
        });
        add(toggle);

        startX -= panelWidth + spacing;
        PersonalSpacePanel personal = new PersonalSpacePanel();
        personal.setBounds(startX, 40, panelWidth, panelHeight);
        personal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        personal.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TopBarPanel.this);
                if (!SessionManager.isLoggedIn()) {
                    SessionManager.returnAfterLogin = frame;
                    SessionManager.redirectTargetPage = () -> new PersonalSpacePage().setVisible(true);
                    DimLayer dim = new DimLayer(frame);
                    frame.setGlassPane(dim);
                    dim.setVisible(true);
                    new LoginForm(frame);
                } else {
                    frame.dispose();
                    new PersonalSpacePage().setVisible(true);
                }
            }
        });
        add(personal);

        startX -= panelWidth + spacing;
        MyTicketSpacePanel tickets = new MyTicketSpacePanel();
        tickets.setBounds(startX, 40, panelWidth, panelHeight);
        tickets.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tickets.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TopBarPanel.this);
                if (!SessionManager.isLoggedIn()) {
                    SessionManager.returnAfterLogin = frame;
                    SessionManager.redirectTargetPage = () -> new MyTicketSpacePage().setVisible(true);
                    DimLayer dim = new DimLayer(frame);
                    frame.setGlassPane(dim);
                    dim.setVisible(true);
                    new LoginForm(frame);
                } else {
                    frame.dispose();
                    new MyTicketSpacePage().setVisible(true);
                }
            }
        });
        add(tickets);

        startX -= panelWidth + spacing;
        LatestNewsPanel news = new LatestNewsPanel();
        news.setBounds(startX, 40, panelWidth, panelHeight);
        news.setCursor(new Cursor(Cursor.HAND_CURSOR));
        news.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TopBarPanel.this);
                frame.dispose();
                new LatestNewsPage().setVisible(true);
            }
        });
        add(news);
    }
}
