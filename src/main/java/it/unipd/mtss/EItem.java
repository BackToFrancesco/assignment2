package it.unipd.mtss;

public class EItem {
    private String name;
    private EItemType type;
    private double price;

    public EItem(String name, EItemType type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
