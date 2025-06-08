package Main.help;

import Main.StartingPage;
import javax.swing.*;
import java.awt.*;
import GlobalConst.Const;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GapPanel extends JPanel {

    private ArrowPanel arrowRight;
    private ArrowPanel arrowLeft;
    private JComboBox<String> sortComboBox;

    public GapPanel() {
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(Const.FRAME_WIDTH, Const.GAP_BETWEEN));

        // === Dimensions ===
        int diameter = 40;
        int spacing = 20;
        int y = (Const.GAP_BETWEEN - diameter) / 2;
        int rightX = Const.FRAME_WIDTH - diameter - 70;
        int leftX = rightX - diameter - spacing;

        // === Default JComboBox ===
        String[] options = {
            "預設排序",
            "上映日期 ⬆", "上映日期 ⬇",
            "片長 ⬆", "片長 ⬇",
            "下檔日期 ⬆", "下檔日期 ⬇"
        };
        sortComboBox = new JComboBox<>(options);
        sortComboBox.setBounds(50, y, 200, diameter);
        sortComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sortComboBox.setFocusable(false);

        sortComboBox.addActionListener(e -> {
            String selected = (String) sortComboBox.getSelectedItem();
            StartingPage.sortBy(selected);
            ((StartingPage) SwingUtilities.getWindowAncestor(GapPanel.this)).refreshMovieCards();
        });

        // === Arrows ===
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

        add(sortComboBox);
        add(arrowLeft);
        add(arrowRight);
    }

    public void updateArrowStates() {
        int total = StartingPage.allMovies.size();
        int index = StartingPage.currentStartIndex;
        int limit = StartingPage.MOVIES_PER_PAGE;

        arrowLeft.setArrowEnabled(index > 0);
        arrowRight.setArrowEnabled(total > index + limit);
    }

    public String getSortOrder() {
        return (String) sortComboBox.getSelectedItem();
    }
}
