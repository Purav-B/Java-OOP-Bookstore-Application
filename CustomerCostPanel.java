package bookstoreapp;

import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomerCostPanel extends JPanel {
    private final BookStoreFrame frame;
    private final JLabel totalCostLabel;
    private final JLabel pointsStatusLabel;

    public CustomerCostPanel(BookStoreFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(3, 1, 10, 10));

        setBackground(new Color(245,245,245));

        totalCostLabel = new JLabel("Total Cost: 0");
        totalCostLabel.setForeground(new Color(70,130,180));

        pointsStatusLabel = new JLabel("Points: 0, Status: Silver");
        pointsStatusLabel.setForeground(new Color(70,130,180));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(70,130,180));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.addActionListener(e -> frame.logout());
        add(totalCostLabel);
        add(pointsStatusLabel);
        add(logoutButton);
    }

    public void showResult(PurchaseResult result) {
        totalCostLabel.setText("Total Cost: " + result.getTotalCost());
        pointsStatusLabel.setText("Points: " + result.getRemainingPoints() + ", Status: " + result.getStatus());
    }
}