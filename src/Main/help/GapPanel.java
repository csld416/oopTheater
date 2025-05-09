package Main.help;

import Main.StartingPage;
import Main.help.ArrowPanel;
import javax.swing.*;
import java.awt.*;
import GlobalConst.Const;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GapPanel extends JPanel {
    
    private ArrowPanel arrowRight;
    private ArrowPanel arrowLeft;

    public GapPanel() {
        setLayout(null); // Manual layout
        setOpaque(false); // Transparent background
        setPreferredSize(new Dimension(Const.FRAME_WIDTH, Const.GAP_BETWEEN));

        // Dimensions and spacing
        int diameter = 40;
        int spacing = 20;

        // Absolute positions
        int y = (Const.GAP_BETWEEN - diameter) / 2;
        int rightX = Const.FRAME_WIDTH - diameter - 70;
        int leftX = rightX - diameter - spacing;

        // Create arrows
        arrowRight = new ArrowPanel(">");
        arrowRight.setBounds(rightX, y, diameter, diameter);

        arrowLeft = new ArrowPanel("<");
        arrowLeft.setBounds(leftX, y, diameter, diameter);

        arrowRight.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int remaining = StartingPage.allMovies.size() - (StartingPage.currentStartIndex + StartingPage.MOVIES_PER_PAGE);
                if (remaining > 0) {
                    int step = Math.min(StartingPage.MOVIES_PER_PAGE, remaining);
                    StartingPage.currentStartIndex += step;
                    ((StartingPage) SwingUtilities.getWindowAncestor(GapPanel.this)).refreshMovieCards();
                }
            }
        });

        arrowLeft.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (StartingPage.currentStartIndex > 0) {
                    int step = Math.min(StartingPage.MOVIES_PER_PAGE, StartingPage.currentStartIndex);
                    StartingPage.currentStartIndex -= step;
                    ((StartingPage) SwingUtilities.getWindowAncestor(GapPanel.this)).refreshMovieCards();
                }
            }
        });

        add(arrowLeft);
        add(arrowRight);
    }

    public void updateArrowStates() {
        int total = StartingPage.allMovies.size();
        int index = StartingPage.currentStartIndex;
        int limit = StartingPage.MOVIES_PER_PAGE;

        arrowLeft.setArrowEnabled(index > 0);

        int remaining = total - (index + limit);
        arrowRight.setArrowEnabled(remaining > 0);
    }

}
