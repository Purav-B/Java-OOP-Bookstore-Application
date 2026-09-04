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

public class OwnerCustomersPanel extends JPanel {
    private final BookStoreFrame frame;
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField usernameField;
    private final JTextField passwordField;

    public OwnerCustomersPanel(BookStoreFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        setBackground(new Color(245,245,245));

        model = new DefaultTableModel(new Object[]{"Username", "Password", "Points"}, 0) {
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
        middlePanel.add(new JLabel("Username:"));
        usernameField = new JTextField(12);
        middlePanel.add(usernameField);
        middlePanel.add(new JLabel("Password:"));
        passwordField = new JTextField(10);
        middlePanel.add(passwordField);
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

        addButton.addActionListener(e -> addCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        backButton.addActionListener(e -> frame.showOwnerStart());
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Customer customer : frame.getStore().getCustomers()) {
            model.addRow(new Object[]{customer.getUsername(), customer.getPassword(), customer.getPoints()});
        }
    }

    private void addCustomer() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        boolean added = frame.getStore().addCustomer(username, password);
        if (!added) {
            JOptionPane.showMessageDialog(this, "Invalid data or duplicate customer.");
            return;
        }
        usernameField.setText("");
        passwordField.setText("");
        refreshTable();
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a customer first.");
            return;
        }
        String username = model.getValueAt(selectedRow, 0).toString();
        frame.getStore().removeCustomer(username);
        refreshTable();
    }
}