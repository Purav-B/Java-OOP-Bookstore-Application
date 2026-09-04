package bookstoreapp;

public class PurchaseResult {
    private final double totalCost;
    private final int remainingPoints;
    private final String status;

    public PurchaseResult(double totalCost, int remainingPoints, String status) {
        this.totalCost = totalCost;
        this.remainingPoints = remainingPoints;
        this.status = status;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    public String getStatus() {
        return status;
    }
}
