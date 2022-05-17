////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import java.util.ArrayList;
import java.util.List;

public class EShopBill implements Bill {
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user)
            throws BillException {

        int totalItems = itemsOrdered.size();
        double totalAmount = itemsOrdered.stream()
                .mapToDouble(EItem::getPrice)
                .sum();

        if(totalItems == 0) {
            throw new BillException("No items on the order");
        }

        if(totalItems > 30) {
            throw new BillException("Limit of items exceeded");
        }

        getProcessorDiscount(itemsOrdered);
        getMouseDiscount(itemsOrdered);
        getMouseOrKeyBoardDiscount(itemsOrdered);


        double totalDiscount = getOverallDiscount(totalAmount);
        double commissionAmount = getCommissionAmount(totalAmount);

        double totalDiscountedAmount = itemsOrdered.stream()
                .mapToDouble(EItem::getPrice)
                .sum();

        return totalDiscountedAmount - totalDiscount + commissionAmount;
    }

    private void getProcessorDiscount(List<EItem> itemsOrdered) {
         long numberProcessor = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                 .count();

         if(numberProcessor > 5) {
             EItem minItem = itemsOrdered.stream()
                     .filter(item -> item.getEItemType() == EItemType.PROCESSOR)
                     .min((item1, item2) ->
                             item1.getPrice() >= item2.getPrice() ? 1 : -1)
                     .get();

             minItem.setPrice(minItem.getPrice() * 0.5);
         }
    }

    private void getMouseDiscount(List<EItem> itemsOrdered) {
        long mouses = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.MOUSE)
                .count();


        if(mouses > 10) {
            EItem minItem = itemsOrdered.stream()
                    .filter(item -> item.getEItemType() == EItemType.MOUSE)
                    .min((item1, item2) ->
                            item1.getPrice() >= item2.getPrice() ? 1 : -1)
                    .get();

            minItem.setPrice(0);
        }
    }

    private void getMouseOrKeyBoardDiscount(List<EItem> itemsOrdered) {
        long mouses = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.MOUSE)
                .count();

        long numberProcessor = itemsOrdered.stream()
                .filter(item -> item.getEItemType() == EItemType.KEYBOARD)
                .count();

        if(mouses == numberProcessor && mouses > 0) {
            EItem minItem = itemsOrdered.stream()
                    .filter(item -> item.getEItemType() == EItemType.MOUSE ||
                            item.getEItemType() == EItemType.KEYBOARD)
                    .min((item1, item2) ->
                            item1.getPrice() >= item2.getPrice() ? 1 : -1)
                    .get();

            minItem.setPrice(0);
        }
    }

    private double getOverallDiscount(double totalAmount) {
        return totalAmount > 1000 ? totalAmount * 0.1 : 0;
    }

    private double getCommissionAmount(double totalAmount) {
        return totalAmount < 10 ? 2 : 0;
    }

    public List<Order> getUnder18FreeOrders(List<Order> itemOrders) {

        List<User> userList = new ArrayList<>();
        List<Order> freeOrders = new ArrayList<>();

        ArrayList<Order> eligibleOrders = new ArrayList<>(itemOrders.stream()
                .filter(item -> !item.getUser().isOver18() &&
                        item.getOrderTime().isAfter(Order.startFreeOrderTime) &&
                        item.getOrderTime().isBefore(Order.endFreeOrderTime))
                .toList());

        while(freeOrders.size() < 10 && eligibleOrders.size() > 0) {
            int randomIndex = (int)(Math.random() * (eligibleOrders.size()));
            if(userList.stream().noneMatch(user ->
                            user == eligibleOrders.get(randomIndex).getUser())
            ) {
                userList.add(eligibleOrders.get(randomIndex).getUser());
                freeOrders.add(eligibleOrders.get(randomIndex));
            }
            eligibleOrders.remove(randomIndex);
        }
        return freeOrders;
    }
}
