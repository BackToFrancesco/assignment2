////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import java.util.Date;
import java.util.List;

public class EShopBill implements Bill {
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user)
            throws TotalAmountExceededException {
        if(itemsOrdered.stream().count() > 30) {
            throw new TotalAmountExceededException("Limit of orders exceeded");
        }
        double totalAmount = itemsOrdered.stream()
                .mapToDouble(EItem::getPrice)
                .sum();

        double processorDiscount = getProcessorDiscount(itemsOrdered);
        double mouseDiscount = getMouseDiscount(itemsOrdered);
        double mouseOrKeyboardDiscount =
                getMouseOrKeyBoardDiscount(itemsOrdered);

        double totalDiscount = getOverallDiscount(totalAmount);

        return totalAmount - processorDiscount - mouseDiscount -
                mouseOrKeyboardDiscount - totalDiscount;
    }

    private double getProcessorDiscount(List<EItem> itemsOrdered) {
         long numberProcessor = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                 .count();
        double minimumPrice = 0;
         if(numberProcessor > 5) {
             minimumPrice = itemsOrdered.stream()
                     .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                     .mapToDouble(EItem::getPrice).min()
                     .orElse(0.00);
         }
         return 0.5*minimumPrice;
    }

    private double getMouseDiscount(List<EItem> itemsOrdered) {
        long mouses = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.MOUSE)
                .count();

        double minimumPrice = 0;

        if(mouses > 10) {
            minimumPrice = itemsOrdered.stream()
                    .filter(item -> item.getEItemType() == EItemType.MOUSE)
                    .mapToDouble(EItem::getPrice).min()
                    .orElse(0.00);
        }
        return minimumPrice;
    }

    private double getMouseOrKeyBoardDiscount(List<EItem> itemsOrdered) {
        long mouses = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.MOUSE)
                .count();

        long numberProcessor = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.KEYBOARD)
                .count();

        double minimumPrice = 0;

        if(mouses == numberProcessor) {
            minimumPrice = itemsOrdered.stream()
                    .filter(item -> item.getEItemType() == EItemType.MOUSE ||
                            item.getEItemType() == EItemType.KEYBOARD)
                    .mapToDouble(EItem::getPrice).min()
                    .orElse(0.00);
        }
        return minimumPrice;
    }

    private double getOverallDiscount(double totalAmount) {
        return totalAmount > 1000 ? totalAmount * 0.1 : 0;
    }
}
