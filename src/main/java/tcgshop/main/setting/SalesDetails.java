package tcgshop.main.setting;

public class SalesDetails {
    private String name;
    private int amount;
    private double price;

    public SalesDetails(String name, int amount, double price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public int getAmount() {
        return amount;
    }
    public double getPrice() {
        return price;
    }
}
