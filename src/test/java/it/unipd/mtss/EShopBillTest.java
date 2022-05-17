////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EShopBillTest {
    private EShopBill bill = new EShopBill();
    private final User userOver18 = new User("TEST", "Test", "Testing", "test@test.com", LocalDate.of(2000, 2, 26));
    private final User userUnder18 = new User("TEST", "Test", "Testing", "test@test.com", LocalDate.of(2005, 2, 26));
    @Before
    public void before() {
        bill = new EShopBill();
    }

    @Test(expected = BillException.class)
    public void EmptyOrderTest() throws BillException {
        // Arrange
        List<EItem> itemList = new LinkedList<>();

        // Act
        bill.getOrderPrice(itemList, userOver18);

        // Assert
        fail();
    }

    @Test
    public void NonEmptyOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem item1 = new EItem("Logitech xxx", EItemType.MOUSE, 50.00);
        items.add(item1);
        items.add(item1);

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 100.00;
    }

    @Test
    public void LessThanFiveProcessorDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 10.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 20.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 30.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 40.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 50.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 60.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 210.00;
    }

    @Test
    public void MoreThanFiveProcessorDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 10.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 20.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 30.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 40.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 50.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 60.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 70.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 275.00;
    }

    @Test
    public void MoreThanFiveEqualProcessorDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        for(int i = 0; i < 6; i++) {
            items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 60.00));
        }

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 360 - 30;
    }

    @Test
    public void LessThanTenMouseOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 5.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 6.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 7.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 130.00));
        items.add(new EItem("Intel i5 xxx", EItemType.PROCESSOR, 73.41));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 221.41;
    }

    @Test
    public void MoreThanTenMouseOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 5.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 6.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 7.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 5.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 6.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 7.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 5.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 6.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 7.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 5.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 6.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 7.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 67.00;
    }

    @Test
    public void NumberMouseEqualsKeyboardGreaterThanZeroDiscountTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 10.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 20.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 20.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 20.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 20.00));
        items.add(new EItem("MSI xxx", EItemType.KEYBOARD, 30.00));
        items.add(new EItem("MSI xxx", EItemType.KEYBOARD, 50.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 160.00;
    }

    @Test
    public void NumberMouseEqualsKeyboardEqualZeroDiscountTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 300.00;
    }

    @Test
    public void NumberMouseKeyboardAllEqualsTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            items.add(new EItem("Logitech keyboard xxx", EItemType.KEYBOARD, 15.00));
            items.add(new EItem("Super Logitech Mouse xxx", EItemType.MOUSE, 15.00));
        }

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 90 - 15;
    }

    @Test
    public void NumberMouseDiffersKeyboardDiscountTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 10.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 20.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.KEYBOARD, 30.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.KEYBOARD, 40.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.KEYBOARD, 50.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 450.00;
    }

    @Test
    public void LessThanOneThousandTotalOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 340.00));
        items.add(new EItem("Intel i9 10900k", EItemType.PROCESSOR, 449.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 789.00;
    }

    @Test
    public void MoreThanOneThousandTotalOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 640.00));
        items.add(new EItem("Intel i9 10900k", EItemType.PROCESSOR, 449.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 980.10;
    }

    @Test
    public void ProcessorAndTotalAmountDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem specificProcessor = new EItem("Intel i5 5280", EItemType.PROCESSOR, 150.00);
        EItem item1 = new EItem("Logitech M185", EItemType.MOUSE, 15.00);

        items.add(item1);
        items.add(specificProcessor);

        for (int i = 0; i < 5; i++)
            items.add(new EItem("Intel i9 10900k", EItemType.PROCESSOR, 449.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 2410 - 241 - 75;
    }

    @Test
    public void ProcessorAndNoTotalAmountDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem specificProcessor = new EItem("Intel Pentium", EItemType.PROCESSOR, 90.00);

        items.add(specificProcessor);
        for (int i = 0; i < 6; i++)
            items.add(new EItem("Intel i2 1024", EItemType.PROCESSOR, 150.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 990 - 45;
    }

    @Test
    public void MouseAndTotalAmountDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem processor = new EItem("Intel i9 10900k", EItemType.PROCESSOR, 849.00);

        items.add(processor);
        for (int i = 0; i < 15; i++)
            items.add(new EItem("Logitech M185", EItemType.MOUSE, 15.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 1074 - 107.4 - 15;
    }

    @Test
    public void MouseAndNoTotalAmountDiscountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();

        for (int i = 0; i < 20; i++)
            items.add(new EItem("Logitech M185", EItemType.MOUSE, 15.00));

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 300 - 15;
    }

    @Test(expected = BillException.class)
    public void Over30ItemsOrderTest() throws BillException {
        // Arrange
        List<EItem> itemList = new LinkedList<>();

        for (int i = 0; i < 31; i++) {
            itemList.add(new EItem("Asus Sabertooth xxx", EItemType.KEYBOARD, 50.00));
        }

        // Act
        bill.getOrderPrice(itemList, userOver18);

        // Assert
        fail();
    }

    @Test
    public void LessThanTenTotalAmountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericMouse = new EItem("Logitech M18", EItemType.MOUSE, 7.00);
        items.add(genericMouse);

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 7 + 2;
    }

    @Test
    public void EqualToTenTotalAmountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericMouse = new EItem("Logitech M18", EItemType.MOUSE, 10.00);
        items.add(genericMouse);

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 10;
    }

    @Test
    public void overThanTenTotalAmountOrderTest() throws BillException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericMouse = new EItem("Logitech M18", EItemType.MOUSE, 15.00);
        items.add(genericMouse);

        // Act
        double total = bill.getOrderPrice(items, userOver18);

        // Assert
        assert total == 15;
    }

    @Test
    public void giveAwayWithOnlyOver18UsersOutOfTimeTest() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));
        listOrders.add(new Order(LocalTime.of(17,59,59), userOver18, listEItem, bill.getOrderPrice(listEItem,userOver18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 0;
    }

    @Test
    public void giveAwayWithOnlyOver18UsersInTimeTest() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));
        listOrders.add(new Order(LocalTime.of(18,59,59), userOver18, listEItem, bill.getOrderPrice(listEItem,userOver18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 0;
    }

    @Test
    public void giveAwayWithOnlyUnder18UsersOutOfTimeTest() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));
        listOrders.add(new Order(LocalTime.of(17,59,59), userUnder18, listEItem, bill.getOrderPrice(listEItem,userUnder18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 0;
    }

    @Test
    public void giveAwayWithOnlyUnder18UsersInTimeTest() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));
        listOrders.add(new Order(LocalTime.of(18,59,59), userUnder18, listEItem, bill.getOrderPrice(listEItem,userUnder18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 1;
    }

    @Test
    public void giveAwayWithDuplicateUnder18UsersInTimeTest() throws BillException {
        // Arrange
        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();
        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));
        listOrders.add(new Order(LocalTime.of(18,59,59), userUnder18, listEItem, bill.getOrderPrice(listEItem,userUnder18)));
        listOrders.add(new Order(LocalTime.of(18,59,58), userUnder18, listEItem, bill.getOrderPrice(listEItem,userUnder18)));
        listOrders.add(new Order(LocalTime.of(19,59,58), userUnder18, listEItem, bill.getOrderPrice(listEItem,userUnder18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 1;
    }

    @Test
    public void giveAwayWithMixedUserLessThanTenUnder18Test() throws BillException {
        // Arrange
        User anotherUserUnder18 = new User("TEST", "Test", "Testing", "test@test.com", LocalDate.of(2006, 2, 26));
        User anotherUserOver18 = new User("TEST", "Test", "Testing", "test@test.com", LocalDate.of(2000, 1, 4));

        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();

        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));

        listOrders.add(new Order(LocalTime.of(18,59,59), userUnder18, listEItem, bill.getOrderPrice(listEItem,userUnder18)));
        listOrders.add(new Order(LocalTime.of(18,59,58), anotherUserUnder18, listEItem, bill.getOrderPrice(listEItem,anotherUserUnder18)));

        listOrders.add(new Order(LocalTime.of(18,59,59), userOver18, listEItem, bill.getOrderPrice(listEItem,userOver18)));
        listOrders.add(new Order(LocalTime.of(18,59,59), anotherUserOver18, listEItem, bill.getOrderPrice(listEItem,anotherUserOver18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 2;
    }

    @Test
    public void giveAwayWithMixedUserMoreThanTenUnder18Test() throws BillException {
        // Arrange
        User anotherUserOver18 = new User("TEST", "Test", "Testing", "test@test.com", LocalDate.of(2000, 1, 4));

        List<Order> listOrders = new ArrayList<>();
        List<EItem> listEItem = new LinkedList<>();

        listEItem.add(new EItem("Logitech M18", EItemType.MOUSE, 15.00));

        for(int i = 1; i <= 11; i++) {
            User anotherUserUnder18 = new User("TEST", "Test", "Testing", "test@test.com", LocalDate.of(2006, 4, i));
            listOrders.add(new Order(LocalTime.of(18,59,59), anotherUserUnder18, listEItem, bill.getOrderPrice(listEItem,anotherUserUnder18)));
        }

        listOrders.add(new Order(LocalTime.of(18,59,59), userOver18, listEItem, bill.getOrderPrice(listEItem,userOver18)));

        // Act
        int giftedOrders = bill.getUnder18FreeOrders(listOrders).size();

        // Assert
        assert giftedOrders == 10;
    }
}