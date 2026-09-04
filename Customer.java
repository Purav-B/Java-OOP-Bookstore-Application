package bookstoreapp;

public class Customer {
    private String username;
    private String password;
    private int points;
    private CustomerState state;

    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        updateState();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = Math.max(points, 0);
        updateState();
    }

    public CustomerState getState() {
        return state;
    }

    public String getStatus() {
        return state.getStatusName();
    }

    public void updateState() {
        if (points >= 1000) {
            state = new GoldState();
        } else {
            state = new SilverState();
        }
    }
}
