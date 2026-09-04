package bookstoreapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookStore {
    private final List<Book> books;
    private final List<Customer> customers;
    private final File booksFile;
    private final File customersFile;

    public BookStore() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
        booksFile = new File("books.txt");
        customersFile = new File("customers.txt");
    }

    public void loadData() {
        books.clear();
        customers.clear();
        loadBooks();
        loadCustomers();
    }

    private void loadBooks() {
        if (!booksFile.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(booksFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    books.add(new Book(name, price));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        if (!customersFile.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(customersFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int points = Integer.parseInt(parts[2].trim());
                    customers.add(new Customer(username, password, points));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        saveBooks();
        saveCustomers();
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFile, false))) {
            for (Book book : books) {
                writer.write(book.getName() + ";" + book.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customersFile, false))) {
            for (Customer customer : customers) {
                writer.write(customer.getUsername() + ";" + customer.getPassword() + ";" + customer.getPoints());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean addBook(String name, double price) {
        if (name == null || name.trim().isEmpty() || price < 0) {
            return false;
        }
        if (findBook(name) != null) {
            return false;
        }
        books.add(new Book(name.trim(), price));
        return true;
    }

    public boolean removeBookByName(String name) {
        Book book = findBook(name);
        if (book == null) {
            return false;
        }
        books.remove(book);
        return true;
    }

    public Book findBook(String name) {
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(name)) {
                return book;
            }
        }
        return null;
    }

    public boolean addCustomer(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        if (findCustomer(username) != null) {
            return false;
        }
        customers.add(new Customer(username.trim(), password.trim(), 0));
        return true;
    }

    public boolean removeCustomer(String username) {
        Customer customer = findCustomer(username);
        if (customer == null) {
            return false;
        }
        customers.remove(customer);
        return true;
    }

    public Customer findCustomer(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equalsIgnoreCase(username)) {
                return customer;
            }
        }
        return null;
    }

    public Customer authenticateCustomer(String username, String password) {
        Customer customer = findCustomer(username);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null;
    }

    public PurchaseResult purchaseBooks(Customer customer, List<Book> selectedBooks, boolean redeemPoints) {
        double sum = 0.0;
        for (Book book : selectedBooks) {
            sum += book.getPrice();
        }

        int currentPoints = customer.getPoints();
        double deduction = 0.0;
        int pointsUsed = 0;

        if (redeemPoints && currentPoints > 0) {
            deduction = currentPoints / 100.0;
            if (deduction > sum) {
                deduction = sum;
            }
            pointsUsed = (int) Math.round(deduction * 100);
            if (pointsUsed > currentPoints) {
                pointsUsed = currentPoints;
            }
        }

        double finalCost = sum - deduction;
        if (finalCost < 0) {
            finalCost = 0;
        }
        finalCost = Math.round(finalCost * 100.0) / 100.0;

        int newPoints = currentPoints - pointsUsed + (int) Math.round(finalCost * 10);
        customer.setPoints(newPoints);

        for (Book book : selectedBooks) {
            books.remove(book);
        }

        return new PurchaseResult(finalCost, customer.getPoints(), customer.getStatus());
    }
}
