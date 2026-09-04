package bookstoreapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class OwnerBooksPanel extends JPanel {
    private final BookStoreFrame frame;
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField nameField;
    private final JTextField priceField;

    public OwnerBooksPanel(BookStoreFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        setBackground(new Color(245,245,245));

        model = new DefaultTableModel(new Object[]{"Book Name", "Book Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getTableHeader().setBackground(new Color(70,130,180));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel middlePanel = new JPanel(new FlowLayout());
        middlePanel.setBackground(new Color(245,245,245));
        middlePanel.add(new JLabel("Name:"));
        nameField = new JTextField(12);
        middlePanel.add(nameField);
        middlePanel.add(new JLabel("Price:"));
        priceField = new JTextField(8);
        middlePanel.add(priceField);
        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(70,130,180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        middlePanel.add(addButton);
        add(middlePanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(245,245,245));
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        deleteButton.setBackground(new Color(70,130,180));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        backButton.setBackground(new Color(70,130,180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addBook());
        deleteButton.addActionListener(e -> deleteBook());
        backButton.addActionListener(e -> frame.showOwnerStart());
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Book book : frame.getStore().getBooks()) {
            model.addRow(new Object[]{book.getName(), book.getPrice()});
        }
    }

    private void addBook() {
        try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            boolean added = frame.getStore().addBook(name, price);
            if (!added) {
                JOptionPane.showMessageDialog(this, "Invalid data or duplicate book.");
                return;
            }
            nameField.setText("");
            priceField.setText("");
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid price.");
        }
    }

    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first.");
            return;
        }
        String bookName = model.getValueAt(selectedRow, 0).toString();
        frame.getStore().removeBookByName(bookName);
        refreshTable();
    }
}