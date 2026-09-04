package bookstoreapp;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookStoreFrame frame = new BookStoreFrame();
            frame.setVisible(true);
        });
    }
}
