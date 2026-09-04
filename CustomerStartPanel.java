package bookstoreapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomerStartPanel extends JPanel {
    private final BookStoreFrame frame;
    private final JLabel welcomeLabel;
    private final DefaultTableModel model;
    private final JTable table;

    public CustomerStartPanel(BookStoreFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        setBackground(new Color(245,245,245));

        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setForeground(new Color(70,130,180));
        add(welcomeLabel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Book Name", "Book Price", "Select"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                }
                if (columnIndex == 1) {
                    return Double.class;
                }
                return String.class;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setBackground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getTableHeader().setBackground(new Color(70,130,180));
        table.getTableHeader().setForeground(Color.WHITE);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(245,245,245));
        JButton buyButton = new JButton("Buy");
        JButton redeemButton = new JButton("Redeem points and Buy");
        JButton logoutButton = new JButton("Logout");

        buyButton.setBackground(new Color(70,130,180));
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);

        redeemButton.setBackground(new Color(70,130,180));
        redeemButton.setForeground(Color.WHITE);
        redeemButton.setFocusPainted(false);

        logoutButton.setBackground(new Color(70,130,180));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        bottomPanel.add(buyButton);
        bottomPanel.add(redeemButton);
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        buyButton.addActionListener(e -> completePurchase(false));
        redeemButton.addActionListener(e -> completePurchase(true));
        logoutButton.addActionListener(e -> frame.logout());
    }

    public void refreshView() {
        Customer customer = frame.getCurrentCustomer();
        welcomeLabel.setText("Welcome " + customer.getUsername() + ". You have " + customer.getPoints() + " points. Your status is " + customer.getStatus());
        model.setRowCount(0);
        for (Book book : frame.getStore().getBooks()) {
            model.addRow(new Object[]{book.getName(), book.getPrice(), false});
        }
    }

    private void completePurchase(boolean redeem) {
        List<Book> selectedBooks = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean selected = (Boolean) model.getValueAt(i, 2);
            if (selected != null && selected) {
                String name = model.getValueAt(i, 0).toString();
                Book book = frame.getStore().findBook(name);
                if (book != null) {
                    selectedBooks.add(book);
                }
            }
        }

        if (selectedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one book.");
            return;
        }

        PurchaseResult result = frame.getStore().purchaseBooks(frame.getCurrentCustomer(), selectedBooks, redeem);
        frame.showCustomerCost(result);
    }
}