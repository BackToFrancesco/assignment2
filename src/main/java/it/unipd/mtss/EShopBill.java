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
        double mouseDiscount = getMouseDiscount(itemsOrdered);
        double mouseOrKeyboardDiscount =
                getMouseOrKeyBoardDiscount(itemsOrdered);

        return totalAmount - processorDiscount - mouseDiscount -
                mouseOrKeyboardDiscount;
    }

    private double getProcessorDiscount(List<EItem> itemsOrdered)
            throws TotalAmountExceededException {
         long numberProcessor = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                 .count();
        double minimumPrice = 0;
         if(numberProcessor > 5) {
             minimumPrice = itemsOrdered.stream()
                     .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                     .mapToDouble(item -> item.getPrice()).min()
                     .orElse(0.00);
         }
         return 0.5*minimumPrice;
    }

    private double getMouseDiscount(List<EItem> itemsOrdered)
            throws TotalAmountExceededException {
        long mouses = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.MOUSE)
                .count();

        double minimumPrice = 0;

        if(mouses > 10) {
            minimumPrice = itemsOrdered.stream()
                    .filter(item -> item.getEItemType() == EItemType.MOUSE)
                    .mapToDouble(item -> item.getPrice()).min()
                    .orElse(0.00);
        }
        return minimumPrice;
    }

    private double getMouseOrKeyBoardDiscount(List<EItem> itemsOrdered)
            throws TotalAmountExceededException {
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
                    .mapToDouble(item -> item.getPrice()).min()
                    .orElse(0.00);
        }
        return minimumPrice;
    }
}
