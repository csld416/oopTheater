package UserSpace;

import Data.SessionManager;
import Main.help.TopBarPanel;
import Main.StartingPage;
import UserSpace.Panels.*;
import UserSpace.Buttons.*;
import global.*;

import javax.swing.*;
import java.awt.*;

public class PersonalSpacePage extends JFrame {

    private JPanel topBarSlot;
    private JPanel leftPanel;
    private JPanel rightPanel;

    private JPanel userscope;
    private JPanel data_modification;
    private JPanel change_password;
    private JPanel purchase_record;
    private JPanel log_out;

    public PersonalSpacePage() {
        setTitle("Personal Space Page");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout

        initTopBar();
        initLeftPanel();
        initRightPanel();

        setVisible(true);
    }

    private void initTopBar() {
        topBarSlot = new JPanel(null);
        topBarSlot.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel();
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initLeftPanel() {
        int y = UIConstants.TOP_BAR_HEIGHT;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;
        int leftPanelWidth = (int) (UIConstants.FRAME_WIDTH * 0.25); // About 25% for left panel

        leftPanel = new JPanel(null);
        leftPanel.setBounds(0, y, leftPanelWidth, height);
        leftPanel.setBackground(Color.WHITE);

        int slotHeight = UserUIConstants.SLOT_HEIGHT;

        // === UserScope Button ===
        userscope = new UserSpace_Button();
        userscope.setBounds(0, 0, leftPanelWidth, slotHeight);
        userscope.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                userscope.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                userscope.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                rightPanel.removeAll();
                UserSpace_Panel panel = new UserSpace_Panel();
                panel.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
                rightPanel.add(panel);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        // === InfoChange Button ===
        data_modification = new InfoChange_Button();
        data_modification.setBounds(0, slotHeight, leftPanelWidth, slotHeight);
        data_modification.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                data_modification.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                data_modification.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                rightPanel.removeAll();
                InfoChange_Panel panel = new InfoChange_Panel();
                panel.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
                rightPanel.add(panel);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        // === ChangePassword Button ===
        change_password = new ChangePassword_Button();
        change_password.setBounds(0, slotHeight * 2, leftPanelWidth, slotHeight);
        change_password.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                change_password.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                change_password.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                rightPanel.removeAll();
                ChangePassword_Panel panel = new ChangePassword_Panel();
                panel.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
                rightPanel.add(panel);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        // === PurchaseRecord Button ===
        purchase_record = new PurchaseRecord_Button();
        purchase_record.setBounds(0, slotHeight * 3, leftPanelWidth, slotHeight);
        purchase_record.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                purchase_record.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                purchase_record.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                rightPanel.removeAll();
                PurchaseRecord_Panel panel = new PurchaseRecord_Panel();
                panel.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
                rightPanel.add(panel);
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        // === LogOut Button ===
        log_out = new LogOut_Button();
        log_out.setBounds(0, slotHeight * 4, leftPanelWidth, slotHeight);
        log_out.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                log_out.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                log_out.setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                SessionManager.currentUserPhone = null; // Clear user session
                dispose(); // Close current window
                new StartingPage(); // Open starting page
            }
        });

        // Add all buttons to leftPanel
        leftPanel.add(userscope);
        leftPanel.add(data_modification);
        leftPanel.add(change_password);
        leftPanel.add(purchase_record);
        leftPanel.add(log_out);

        add(leftPanel);
    }

    private void initRightPanel() {
        int y = UIConstants.TOP_BAR_HEIGHT;
        int height = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;
        int leftPanelWidth = (int) (UIConstants.FRAME_WIDTH * 0.25);
        int rightPanelWidth = UIConstants.FRAME_WIDTH - leftPanelWidth;

        rightPanel = new UserSpace_Panel();
        rightPanel.setBounds(leftPanelWidth, y, rightPanelWidth, height);
        rightPanel.setBackground(UIConstants.COLOR_MAIN_LIGHT);

        add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PersonalSpacePage::new);
    }
}
