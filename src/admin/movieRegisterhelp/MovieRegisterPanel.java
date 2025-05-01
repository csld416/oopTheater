package admin.movieRegisterhelp;

import admin.MovieRegisterPage;
import global.Movie;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.DatabaseConnection;
import global.UIConstants;

public class MovieRegisterPanel extends JPanel {

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
    private JLabel titleBar;

    private String selectedPosterPath;

    private Movie editingMovie = null;

    private int WIDTH = UIConstants.FRAME_WIDTH - UIConstants.LEFT_PANEL_WIDTH;
    private int HEIGHT = UIConstants.FRAME_HEIGHT - UIConstants.TOP_BAR_HEIGHT;

    public MovieRegisterPanel(Movie movie) {
        this();
        this.editingMovie = movie;
        loadMovieData(movie);
        titleBar.setText("Editing: " + movie.getTitle());
    }

    public MovieRegisterPanel() {
        setLayout(null);
        setBackground(UIConstants.COLOR_MAIN_LIGHT);

        JPanel formContainer = new JPanel(null);
        formContainer.setBounds(0, 0, WIDTH, HEIGHT);
        formContainer.setBackground(new Color(247, 244, 241));
        add(formContainer);

        titleBar = new JLabel("Movie Register Page", SwingConstants.CENTER);
        titleBar.setFont(new Font("Arial", Font.BOLD, 18));
        titleBar.setOpaque(true);
        titleBar.setBackground(new Color(68, 149, 145));
        titleBar.setForeground(Color.WHITE);
        titleBar.setBounds(0, 0, WIDTH, 40);
        formContainer.add(titleBar);

        int labelWidth = 120;
        int fieldWidth = 420;
        int spacing = 10;

        int formTotalWidth = labelWidth + spacing + fieldWidth;
        int startX = (WIDTH - formTotalWidth) / 2;

        int labelX = startX;
        int fieldX = startX + labelWidth + spacing;
        int height = 25;
        int width = fieldWidth;
        int gap = 35;
        int y = 60;

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

        y += gap;
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
        saveButton.addActionListener((e) -> {
            System.out.println("Movie Register button clicked!");
            if (!validateFields()) return;
            if (editingMovie != null && editingMovie.getId() != null) {
                updateMovieInDatabase(editingMovie.getId());
            } else {
                insertMovieToDatabase();
            }
            Window window = SwingUtilities.getWindowAncestor(MovieRegisterPanel.this);
            if (window instanceof MovieRegisterPage frame) {
                frame.refreshMovieLeftPanel();
            }
        });
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
        clearButton.addActionListener(e -> clearForm());
        formContainer.add(clearButton);
    }

    private void loadMovieData(Movie movie) {
        if (movie == null) {
            return;
        }

        titleField.setText(movie.getTitle());
        durationField.setText(String.valueOf(movie.getDuration()));
        descriptionArea.setText(movie.getDescription());
        ratingComboBox.setSelectedItem(movie.getRating());

        // Assuming releaseDate and removalDate are stored as java.util.Date
        releaseDateSpinner.setValue(movie.getReleaseDate());
        removalDateSpinner.setValue(movie.getRemovalDate());

        posterPathField.setText(movie.getPosterPath());
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

    private void insertMovieToDatabase() {
        try {
            // Get a connection to the database
            Connection conn = new DatabaseConnection().getConnection();

            // Define SQL insert statement (no triple quotes!)
            String sql = "INSERT INTO Movies (title, duration, description, rating, release_date, removal_date, poster_path) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Create prepared statement
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titleField.getText().trim());
            stmt.setInt(2, Integer.parseInt(durationField.getText().trim()));
            stmt.setString(3, descriptionArea.getText().trim());
            stmt.setString(4, ratingComboBox.getSelectedItem().toString());
            stmt.setDate(5, new java.sql.Date(((Date) releaseDateSpinner.getValue()).getTime()));
            stmt.setDate(6, new java.sql.Date(((Date) removalDateSpinner.getValue()).getTime()));
            stmt.setString(7, posterPathField.getText().trim());

            // Execute and check result
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showSuccessMessage("Movie added Succesfully");
                clearForm();
            } else {
                showErrorMessage("Failed unexpectedly");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            showErrorMessage("資料庫錯誤：" + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            showErrorMessage("發生錯誤：" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (titleField.getText().trim().isEmpty()
                || durationField.getText().trim().isEmpty()
                || descriptionArea.getText().trim().isEmpty()
                || posterPathField.getText().trim().isEmpty()) {
            showErrorMessage("請填寫所有欄位！");
            return false;
        }

        // Duration must be numeric
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) {
                showErrorMessage("電影長度必須為正整數！");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("電影長度必須是數字！");
            return false;
        }

        // Release date must be before removal date
        Date release = (Date) releaseDateSpinner.getValue();
        Date removal = (Date) removalDateSpinner.getValue();
        if (!release.before(removal)) {
            showErrorMessage("下檔日期必須晚於上映日期！");
            return false;
        }

        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = "SELECT COUNT(*) FROM Movies WHERE title = ? AND release_date = ? AND duration = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titleField.getText().trim());
            stmt.setDate(2, new java.sql.Date(release.getTime()));
            stmt.setInt(3, Integer.parseInt(durationField.getText().trim()));

            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            stmt.close();
            conn.close();

            if (count > 0 && (editingMovie == null || editingMovie.getId() == null)) {
                showErrorMessage("此電影已存在於資料庫！");
                return false;
            }

        } catch (Exception e) {
            showErrorMessage("檢查重複時發生錯誤：" + e.getMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void updateMovieInDatabase(int movieId) {
        try {
            Connection conn = new DatabaseConnection().getConnection();
            String sql = """
            UPDATE Movies
            SET title=?, duration=?, description=?, rating=?, release_date=?, removal_date=?, poster_path=?
            WHERE id=?
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, titleField.getText().trim());
            stmt.setInt(2, Integer.parseInt(durationField.getText().trim()));
            stmt.setString(3, descriptionArea.getText().trim());
            stmt.setString(4, ratingComboBox.getSelectedItem().toString());
            stmt.setDate(5, new java.sql.Date(((Date) releaseDateSpinner.getValue()).getTime()));
            stmt.setDate(6, new java.sql.Date(((Date) removalDateSpinner.getValue()).getTime()));
            stmt.setString(7, posterPathField.getText().trim());
            stmt.setInt(8, movieId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                showSuccessMessage("電影更新成功！");
            } else {
                showErrorMessage("更新失敗，請確認資料或重試。");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            showErrorMessage("資料庫錯誤：" + e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            showErrorMessage("發生錯誤：" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void clearForm() {
        titleField.setText("");
        durationField.setText("");
        descriptionArea.setText("");
        ratingComboBox.setSelectedIndex(0);
        releaseDateSpinner.setValue(new java.util.Date());
        removalDateSpinner.setValue(new java.util.Date());
        posterPathField.setText("");
    }

    private void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "欄位驗證錯誤", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "成功", JOptionPane.INFORMATION_MESSAGE);
    }
}
