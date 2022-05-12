package it.unipd.mtss;

import java.util.List;

public interface Bill {
    double getOrderPrice(List<EItem> itemsOrdered, User user) throws TotalAmountExceededException;
}
