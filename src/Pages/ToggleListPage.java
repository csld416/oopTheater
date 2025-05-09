package Pages;

import Main.help.topbarPanel.MyTicketSpacePanel;
import Main.help.topbarPanel.LatestNewsPanel;
import Main.help.topbarPanel.PersonalSpacePanel;
import Main.help.topbarPanel.LogoutPanel;
import Main.help.topbarPanel.OnShelfMoviePanel;
import Main.help.topbarPanel.StaffPanel;
import UserSpace.PersonalSpacePage;
import global.UIConstants;
import Main.help.topbarPanel.LogoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import LoginRegisterForm.*;
import Main.StartingPage;
import admin.AdminMainPage;
import admin.MovieRegisterPage;
import Data.SessionManager;

public class ToggleListPage extends JFrame {

    private final int FIRST_ROW_Y_SPACING = 140;
    private final int VERTICAL_SPACING = 95;
    private final int LOGO_Y = 40;

    private final Color backgroundColor = new Color(216, 200, 196);
    private final Color HoverColor = new Color(143, 121, 102);

    private JFrame substrateFrame;

    private boolean shouldDismiss = true;

    public ToggleListPage(JFrame substrateFrame) {
        this.substrateFrame = substrateFrame;
        setTitle("Toggle List Page");
        setSize(UIConstants.TOGGLE_PAGE_WIDTH, UIConstants.TOGGLE_PAGE_HEIGHT);
        setUndecorated(true);
        setLocationRelativeTo(substrateFrame);
        setAlwaysOnTop(true);
        setLayout(null);
        getContentPane().setBackground(backgroundColor);

        initUI();

        setVisible(true);

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent && ((MouseEvent) event).getID() == MouseEvent.MOUSE_PRESSED) {
                    if (shouldDismiss) {
                        Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                        if (focusedWindow != ToggleListPage.this) {
                            dispose();
                            substrateFrame.getGlassPane().setVisible(false);
                            Toolkit.getDefaultToolkit().removeAWTEventListener(this);
                        }
                    } else {
                        // reset the flag for future clicks
                        shouldDismiss = true;
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    private void initUI() {
        // === Logo panel ===
        LogoPanel logo = new LogoPanel();
        int logoX = (UIConstants.TOGGLE_PAGE_WIDTH - UIConstants.LOGO_WIDTH) / 2;
        logo.setBounds(logoX, LOGO_Y, UIConstants.LOGO_WIDTH, UIConstants.LOGO_HEIGHT);
        logo.setOpaque(true);
        logo.setBackground(backgroundColor);
        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
                substrateFrame.dispose();
                new StartingPage().setVisible(true);
            }
        });
        add(logo);

        int spacing = 20;
        int panelWidth = UIConstants.ICON_PANEL_WIDTH;
        int panelHeight = UIConstants.ICON_PANEL_HEIGHT;
        int totalWidthRow = panelWidth * 3 + spacing * 2;
        int startX = (UIConstants.TOGGLE_PAGE_WIDTH - totalWidthRow) / 2;

        int row1Y = LOGO_Y + FIRST_ROW_Y_SPACING;

        // === 1. 個人專區 (Personal Space)
        PersonalSpacePanel personalSpacePanel = new PersonalSpacePanel();
        personalSpacePanel.setBounds(startX, row1Y, panelWidth, panelHeight);
        personalSpacePanel.setBackground(backgroundColor);
        JLabel personalLabel = personalSpacePanel.getLabel();
        personalSpacePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                personalSpacePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                personalLabel.setForeground(HoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                personalSpacePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                personalLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                shouldDismiss = false;  // prevent premature dismissal

                JFrame frame = substrateFrame;
                dispose();
                if (!SessionManager.isLoggedIn()) {
                    SessionManager.returnAfterLogin = frame;
                    SessionManager.redirectTargetPage = () -> new PersonalSpacePage().setVisible(true);

                    new LoginForm(frame);
                } else {
                    frame.getGlassPane().setVisible(false);
                    frame.dispose();
                    new PersonalSpacePage().setVisible(true);
                }
            }
        });
        add(personalSpacePanel);

        // === 2. 我的票夾 (My Tickets)
        MyTicketSpacePanel myTicketsPanel = new MyTicketSpacePanel();
        myTicketsPanel.setBounds(startX + (panelWidth + spacing), row1Y, panelWidth, panelHeight);
        myTicketsPanel.setBackground(backgroundColor);
        JLabel ticketLabel = myTicketsPanel.getLabel();
        myTicketsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                myTicketsPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                ticketLabel.setForeground(HoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                myTicketsPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                ticketLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                shouldDismiss = false;  // prevent premature dismissal

                JFrame frame = substrateFrame;
                dispose();
                if (!SessionManager.isLoggedIn()) {
                    SessionManager.returnAfterLogin = frame;
                    SessionManager.redirectTargetPage = () -> new MyTicketSpacePage().setVisible(true);

                    new LoginForm(frame);
                } else {
                    frame.getGlassPane().setVisible(false);
                    frame.dispose();
                    new MyTicketSpacePage().setVisible(true);
                }
            }
        });
        add(myTicketsPanel);

        // === 3. 業務專區 (Staff/Admin Space)
        StaffPanel staffPanel = new StaffPanel();
        staffPanel.setBounds(startX + 2 * (panelWidth + spacing), row1Y, panelWidth, panelHeight);
        staffPanel.setBackground(backgroundColor);
        JLabel staffLabel = staffPanel.getLabel();
        staffPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                staffPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                staffLabel.setForeground(HoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                staffPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                staffLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
                substrateFrame.dispose();
                SessionManager.returnAfterLogin = substrateFrame;
                SessionManager.redirectTargetPage = () -> new AdminMainPage();
                new LoginFormOP();
            }
        });
        add(staffPanel);

        // === Second row ===
        int row2Y = row1Y + VERTICAL_SPACING;

        // === 4. 最新消息 (Latest News)
        LatestNewsPanel latestNewsPanel = new LatestNewsPanel();
        latestNewsPanel.setBounds(startX, row2Y, panelWidth, panelHeight);
        latestNewsPanel.setBackground(backgroundColor);
        JLabel newsLabel = latestNewsPanel.getLabel();
        latestNewsPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                latestNewsPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                newsLabel.setForeground(HoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                latestNewsPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                newsLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
                substrateFrame.dispose();
                new LatestNewsPage().setVisible(true);
            }
        });
        add(latestNewsPanel);

        // === 5. 上映電影 (On-Shelf Movies)
        OnShelfMoviePanel onShelfMoviesPanel = new OnShelfMoviePanel();
        onShelfMoviesPanel.setBounds(startX + (panelWidth + spacing), row2Y, panelWidth, panelHeight);
        onShelfMoviesPanel.setBackground(backgroundColor);
        JLabel onShelfLabel = onShelfMoviesPanel.getLabel();
        onShelfMoviesPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                onShelfMoviesPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                onShelfLabel.setForeground(HoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                onShelfMoviesPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                onShelfLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
                substrateFrame.dispose();
                new StartingPage().setVisible(true); // Fallback
            }
        });
        add(onShelfMoviesPanel);

        // === 6. 登出會員 (Logout)
        LogoutPanel logoutPanel = new LogoutPanel();
        logoutPanel.setBounds(startX + 2 * (panelWidth + spacing), row2Y, panelWidth, panelHeight);
        logoutPanel.setBackground(backgroundColor);
        JLabel logoutLabel = logoutPanel.getLabel();
        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                logoutLabel.setForeground(HoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                logoutLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                dispose();
                substrateFrame.getGlassPane().setVisible(false);
                substrateFrame.dispose();
                //SessionManager.logout();
                new StartingPage().setVisible(true);
            }
        });
        add(logoutPanel);
    }

    public static void main(String[] args) {
        JFrame dummyFrame = new JFrame();
        dummyFrame.setSize(900, 600);
        dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dummyFrame.setVisible(true);

        new ToggleListPage(dummyFrame);
    }
}
