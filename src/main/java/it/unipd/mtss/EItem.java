package it.unipd.mtss;

public class EItem {
    private String name;
    private EItemType type;
    private double amount;

    public EItem(String name, EItemType type, double amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
    }
}
