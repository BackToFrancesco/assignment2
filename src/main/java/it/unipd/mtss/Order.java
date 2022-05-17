////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import java.time.LocalTime;
import java.util.List;

public class Order {

    private LocalTime orderTime;
    private User user;
    private List<EItem> itemList;
    private double price;

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public User getUser() {
        return user;
    }

    public Order(LocalTime orderTime, User user,
                 List<EItem> itemList, double price) {
        this.orderTime = orderTime;
        this.user = user;
        this.itemList = itemList;
        this.price = price;
    }

    public static LocalTime startFreeOrderTime = LocalTime.of(18,00,00);
    public static LocalTime endFreeOrderTime = LocalTime.of(19,00,00);

}
