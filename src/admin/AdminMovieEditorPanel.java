package admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class AdminMovieEditorPanel extends JPanel {
    private JComboBox<String> movieSelector;
    private JTextField titleField;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JLabel posterLabel;
    private JButton browseBtn;
    private JButton saveBtn;

    public AdminMovieEditorPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // === Top section (Movie selection) ===
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(new JLabel("Select Movie:"));

        movieSelector = new JComboBox<>(new String[]{"Oppenheimer", "Inception", "Tenet"});
        movieSelector.setPreferredSize(new Dimension(200, 25));
        topPanel.add(movieSelector);

        add(topPanel, BorderLayout.NORTH);

        // === Main form area ===
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === Title Field ===
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);

        titleField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(titleField, gbc);

        // === Release Date ===
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Release Date:"), gbc);

        dateField = new JTextField();
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(dateField, gbc);

        // === Description ===
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(new JLabel("Description:"), gbc);

        descriptionArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(scrollPane, gbc);

        // === Poster Image ===
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Poster:"), gbc);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        imagePanel.setBackground(Color.WHITE);

        posterLabel = new JLabel("No Image");
        posterLabel.setPreferredSize(new Dimension(120, 160));
        posterLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(posterLabel);

        browseBtn = new JButton("Browse");
        browseBtn.addActionListener(this::onBrowseImage);
        imagePanel.add(browseBtn);

        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(imagePanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // === Save Button ===
        saveBtn = new JButton("Save Changes");
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(saveBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void onBrowseImage(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(selected.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaled));
            posterLabel.setText(null);
        }
    }
}
