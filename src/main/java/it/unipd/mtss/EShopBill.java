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
        double totalAmount = itemsOrdered.stream()
                .mapToDouble(eItem -> eItem.getPrice())
                .sum();
        double processorDiscount = getProcessorDiscount(itemsOrdered);
        return totalAmount-processorDiscount;
    }

    private double getProcessorDiscount(List<EItem> itemsOrdered) {
         long numberProcessor = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                 .count();
        double minimumPrice = 0;
         if(numberProcessor > 5) {
             minimumPrice = itemsOrdered.stream()
                     .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                     .mapToDouble(item -> item.getPrice()).min().getAsDouble();
         }
         return 0.5*minimumPrice;
    }
}
