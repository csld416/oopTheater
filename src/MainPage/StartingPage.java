/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPage;

import global.UIConstants;

import javax.swing.*;
import java.awt.*;

public class StartingPage extends JFrame {

    private JPanel topBarSlot;
    private JPanel movieSlot;

    public StartingPage() {
        setTitle("Starting Page");
        setSize(UIConstants.FRAME_WIDTH, UIConstants.FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Absolute layout for precise positioning

        initTopBarSlot();
        initMovieSlot();

        setVisible(true);
    }

    private void initTopBarSlot() {
        topBarSlot = new JPanel(null); // Allow absolute placement inside
        topBarSlot.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);

        TopBarPanel topBar = new TopBarPanel(); // your custom panel
        topBar.setBounds(0, 0, UIConstants.FRAME_WIDTH, UIConstants.TOP_BAR_HEIGHT);
        topBarSlot.add(topBar);

        add(topBarSlot);
    }

    private void initMovieSlot() {
        movieSlot = new JPanel();

        int y = UIConstants.TOP_BAR_HEIGHT + UIConstants.GAP_BETWEEN;
        int height = UIConstants.FRAME_HEIGHT - y;

        movieSlot.setBounds(0, y, UIConstants.FRAME_WIDTH, height);
        MoviePanel movieCardPanel = new MoviePanel();
        movieCardPanel.setBounds(0, 0, UIConstants.FRAME_WIDTH, movieSlot.getHeight()); // occupy full movieSlot area
        movieSlot.setLayout(null);
        movieSlot.add(movieCardPanel);
        add(movieSlot);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartingPage::new);
    }
}
