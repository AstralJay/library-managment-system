import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class LibraryManagementSystem extends JFrame {
    private ArrayList<Book> bookList = new ArrayList<>();
    private DefaultTableModel tableModel;

    public LibraryManagementSystem() {
        // JFrame setup
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Toolbar
        JToolBar toolBar = new JToolBar();
        JButton addBookButton = new JButton("Add Book");
        JButton removeBookButton = new JButton("Remove Book");
        JButton searchButton = new JButton("Search");
        toolBar.add(addBookButton);
        toolBar.add(removeBookButton);
        toolBar.add(searchButton);
        add(toolBar, BorderLayout.NORTH);

        // TabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Book Details", createBookDetailsPanel());
        tabbedPane.addTab("Book List", createBookListPanel());
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // Book Details Panel
    private JPanel createBookDetailsPanel() {
        JPanel bookDetailsPanel = new JPanel(new GridLayout(6, 2));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
        JLabel yearLabel = new JLabel("Publication Year:");
        JTextField yearField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JComboBox<String> genreComboBox = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Science", "History"});
        JCheckBox availabilityCheckBox = new JCheckBox("Available");

        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");

        bookDetailsPanel.add(titleLabel);
        bookDetailsPanel.add(titleField);
        bookDetailsPanel.add(authorLabel);
        bookDetailsPanel.add(authorField);
        bookDetailsPanel.add(isbnLabel);
        bookDetailsPanel.add(isbnField);
        bookDetailsPanel.add(yearLabel);
        bookDetailsPanel.add(yearField);
        bookDetailsPanel.add(genreLabel);
        bookDetailsPanel.add(genreComboBox);
        bookDetailsPanel.add(availabilityCheckBox);
        bookDetailsPanel.add(addButton);
        bookDetailsPanel.add(updateButton);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String year = yearField.getText();
            String genre = (String) genreComboBox.getSelectedItem();
            boolean isAvailable = availabilityCheckBox.isSelected();

            if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty() && !year.isEmpty()) {
                Book book = new Book(title, author, isbn, year, genre, isAvailable);
                bookList.add(book);
                tableModel.addRow(new Object[]{title, author, isbn, genre, isAvailable ? "Yes" : "No"});
            } else {
                showErrorDialog("Please fill in all fields.");
            }
        });

        return bookDetailsPanel;
    }

    // Book List Panel
    private JPanel createBookListPanel() {
        JPanel bookListPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Title", "Author", "ISBN", "Genre", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().toLowerCase();
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                if (book.getTitle().toLowerCase().contains(searchTerm) ||
                        book.getAuthor().toLowerCase().contains(searchTerm)) {
                    bookTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        });

        bookListPanel.add(scrollPane, BorderLayout.CENTER);
        bookListPanel.add(searchPanel, BorderLayout.NORTH);

        return bookListPanel;
    }

    // Error Dialog
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
}

// Book Class
class Book {
    private String title;
    private String author;
    private String isbn;
    private String year;
    private String genre;
    private boolean isAvailable;

    public Book(String title, String author, String isbn, String year, String genre, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.genre = genre;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
