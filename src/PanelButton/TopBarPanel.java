package MainPage;

import Pages.MyTicketSpacePage;
import Pages.PersonalSpacePage;
import Pages.LatestNews;
import global.*;
import PanelButton.*;
import LoginRegisterForm.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopBarPanel extends JPanel {

    public TopBarPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT));
        setBackground(new Color(169, 183, 198));

        // Logo Panel (redirects to StartingPage)
        LogoPanel logo = new LogoPanel();
        int verticalPadding = (UIConstants.TOP_BAR_HEIGHT - UIConstants.LOGO_HEIGHT) / 2;
        logo.setBounds(30, verticalPadding, UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT);
        logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(TopBarPanel.this);
                frame.dispose();
                new StartingPage().setVisible(true);
            }
        });
        add(logo);

        // Function Panels from right to left
        int panelWidth = UIConstants.ICON_PANEL_WIDTH;
        int panelHeight = UIConstants.ICON_PANEL_HEIGHT;
        int spacing = 10;
        int toggleWidth = 40;
        int edgePadding = 35;
        int startX = UIConstants.FRAME_WIDTH - edgePadding - toggleWidth;

        // Toggle List Panel
        ToggleListPanel toggle = new ToggleListPanel();
        toggle.setBounds(startX, 40, toggleWidth, 40);
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
                    new LoginForm();
                } else {
                    new PersonalSpacePage().setVisible(true);
                }
                frame.dispose();
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
                    new LoginForm();
                } else {
                    new MyTicketSpacePage().setVisible(true);
                }
                frame.dispose();
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
                new LatestNews().setVisible(true);
            }
        });
        add(news);
    }
}
