package bookstoreapp;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;

public class BookStoreFrame extends JFrame {
    private final BookStore store;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final OwnerBooksPanel ownerBooksPanel;
    private final OwnerCustomersPanel ownerCustomersPanel;
    private final CustomerStartPanel customerStartPanel;
    private final CustomerCostPanel customerCostPanel;
    private Customer currentCustomer;

    public BookStoreFrame() {
        store = new BookStore();
        store.loadData();

        setTitle("BookStore App");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.setBackground(new Color(245,245,245));

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        loginPanel.setBackground(new Color(245,245,245));

        usernameField = new JTextField(12);
        passwordField = new JPasswordField(12);
        JButton loginButton = new JButton("Login");

        loginButton.setBackground(new Color(70,130,180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        JPanel ownerStartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));

        ownerStartPanel.setBackground(new Color(245,245,245));

        JButton booksButton = new JButton("Books");
        JButton customersButton = new JButton("Customers");
        JButton ownerLogoutButton = new JButton("Logout");

        booksButton.setBackground(new Color(70,130,180));
        booksButton.setForeground(Color.WHITE);
        booksButton.setFocusPainted(false);

        customersButton.setBackground(new Color(70,130,180));
        customersButton.setForeground(Color.WHITE);
        customersButton.setFocusPainted(false);

        ownerLogoutButton.setBackground(new Color(70,130,180));
        ownerLogoutButton.setForeground(Color.WHITE);
        ownerLogoutButton.setFocusPainted(false);

        ownerStartPanel.add(booksButton);
        ownerStartPanel.add(customersButton);
        ownerStartPanel.add(ownerLogoutButton);

        ownerBooksPanel = new OwnerBooksPanel(this);
        ownerCustomersPanel = new OwnerCustomersPanel(this);
        customerStartPanel = new CustomerStartPanel(this);
        customerCostPanel = new CustomerCostPanel(this);

        mainPanel.add(wrapPanel(loginPanel), "LOGIN");
        mainPanel.add(wrapPanel(ownerStartPanel), "OWNER_START");
        mainPanel.add(ownerBooksPanel, "OWNER_BOOKS");
        mainPanel.add(ownerCustomersPanel, "OWNER_CUSTOMERS");
        mainPanel.add(customerStartPanel, "CUSTOMER_START");
        mainPanel.add(wrapPanel(customerCostPanel), "CUSTOMER_COST");

        add(mainPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> login());
        booksButton.addActionListener(e -> showOwnerBooks());
        customersButton.addActionListener(e -> showOwnerCustomers());
        ownerLogoutButton.addActionListener(e -> logout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                store.saveData();
            }
        });
    }

    private JPanel wrapPanel(JPanel panel) {
        JPanel wrapper = new JPanel(new BorderLayout());

        wrapper.setBackground(new Color(245,245,245));

        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("admin")) {
            showOwnerStart();
            return;
        }

        Customer customer = store.authenticateCustomer(username, password);
        if (customer != null) {
            currentCustomer = customer;
            customerStartPanel.refreshView();
            cardLayout.show(mainPanel, "CUSTOMER_START");
            return;
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password.");
    }

    public void showOwnerStart() {
        clearLoginFields();
        cardLayout.show(mainPanel, "OWNER_START");
    }

    public void showOwnerBooks() {
        ownerBooksPanel.refreshTable();
        cardLayout.show(mainPanel, "OWNER_BOOKS");
    }

    public void showOwnerCustomers() {
        ownerCustomersPanel.refreshTable();
        cardLayout.show(mainPanel, "OWNER_CUSTOMERS");
    }

    public void showCustomerCost(PurchaseResult result) {
        customerCostPanel.showResult(result);
        cardLayout.show(mainPanel, "CUSTOMER_COST");
    }

    public void logout() {
        currentCustomer = null;
        clearLoginFields();
        ownerBooksPanel.refreshTable();
        ownerCustomersPanel.refreshTable();
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void clearLoginFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public BookStore getStore() {
        return store;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
}