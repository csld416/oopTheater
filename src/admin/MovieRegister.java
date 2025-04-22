package admin;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MovieRegister extends JPanel {

    private JTextField titleField;
    private JTextField durationField;
    private JTextArea descriptionArea;
    private JComboBox<String> ratingComboBox;
    private JSpinner releaseDateSpinner;
    private JSpinner removalDateSpinner;
    private JTextField posterPathField;
    private JButton browseButton;
    private JButton saveButton;
    private JButton clearButton;

    private String selectedPosterPath;

    public MovieRegister() {
        setLayout(null);
        setBackground(new Color(247, 244, 241));

        JPanel formContainer = new JPanel(null);
        formContainer.setBounds(30, 10, 620, 480);
        formContainer.setBackground(new Color(247, 244, 241));
        add(formContainer);

        int labelX = 30, fieldX = 150, width = 420, height = 25, y = 10, gap = 35;

        formContainer.add(label("Title:", labelX, y));
        titleField = field(fieldX, y, width);
        formContainer.add(titleField);

        y += gap;
        formContainer.add(label("Duration (min):", labelX, y));
        durationField = field(fieldX, y, width);
        formContainer.add(durationField);

        y += gap;
        formContainer.add(label("Description:", labelX, y));
        descriptionArea = new JTextArea();
        descriptionArea.setBounds(fieldX, y, width, 60);
        descriptionArea.setLineWrap(true);
        formContainer.add(descriptionArea);

        y += 70;
        formContainer.add(label("Rating:", labelX, y));
        ratingComboBox = new JComboBox<>(new String[]{"普遍級", "保護級", "輔12", "輔15", "限制級"});
        ratingComboBox.setBounds(fieldX, y, width, height);
        formContainer.add(ratingComboBox);

        y += gap;
        formContainer.add(label("Release Date:", labelX, y));
        releaseDateSpinner = new JSpinner(new SpinnerDateModel());
        releaseDateSpinner.setEditor(new JSpinner.DateEditor(releaseDateSpinner, "yyyy-MM-dd"));
        releaseDateSpinner.setBounds(fieldX, y, width, height);
        formContainer.add(releaseDateSpinner);

        y += gap;
        formContainer.add(label("Removal Date:", labelX, y));
        removalDateSpinner = new JSpinner(new SpinnerDateModel());
        removalDateSpinner.setEditor(new JSpinner.DateEditor(removalDateSpinner, "yyyy-MM-dd"));
        removalDateSpinner.setBounds(fieldX, y, width, height);
        formContainer.add(removalDateSpinner);

        int browseWidth = 100;
        int pathFieldWidth = width - browseWidth - 10;

        y += (gap);
        formContainer.add(label("Poster Path:", labelX, y));

        posterPathField = new JTextField();
        posterPathField.setBounds(fieldX, y, pathFieldWidth, 25);
        formContainer.add(posterPathField);

        browseButton = new JButton("Browse");
        browseButton.setBounds(fieldX + pathFieldWidth + 10, y, browseWidth, 25);
        browseButton.setFont(new Font("Arial", Font.PLAIN, 12));
        browseButton.setBackground(new Color(182, 193, 201));
        browseButton.setForeground(Color.BLACK);
        browseButton.setFocusPainted(false);
        browseButton.setBorderPainted(false);
        browseButton.setOpaque(true);
        browseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                browseButton.setBackground(new Color(158, 171, 184));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                browseButton.setBackground(new Color(182, 193, 201));
            }
        });
        browseButton.addActionListener(e -> browsePoster());
        formContainer.add(browseButton);

        int buttonY = y + 60;

        saveButton = new JButton("Save");
        saveButton.setBounds(fieldX + 250, buttonY, 120, 35);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(189, 170, 165));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setOpaque(true);
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color(68, 149, 145));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(new Color(189, 170, 165));
            }
        });
        formContainer.add(saveButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(fieldX + 50, buttonY, 120, 35);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(107, 123, 140));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setOpaque(true);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                clearButton.setBackground(new Color(90, 107, 122));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                clearButton.setBackground(new Color(107, 123, 140));
            }
        });
        formContainer.add(clearButton);
    }

    private JLabel label(String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, 120, 25);
        l.setFont(new Font("Arial", Font.PLAIN, 13));
        l.setForeground(new Color(46, 46, 46));
        return l;
    }

    private JTextField field(int x, int y, int width) {
        JTextField f = new JTextField();
        f.setBounds(x, y, width, 25);
        return f;
    }

    private void browsePoster() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            selectedPosterPath = file.getAbsolutePath();
            posterPathField.setText(selectedPosterPath);
        }
    }
}
