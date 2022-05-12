////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import java.util.List;

public class EShopBill implements Bill {
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user)
            throws TotalAmountExceededException {
        return itemsOrdered.stream()
                .mapToDouble(eItem -> eItem.getPrice())
                .sum();
    }
}
