////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EShopBillTest {

    private EShopBill bill = new EShopBill();
    private User user = new User("TEST", "Test", "Testing", "test@test.com", Date.from(Instant.EPOCH));

    @Before
    public void before() {
        bill = new EShopBill();
    }

    @Test
    public void EmptyOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 0.00;
    }

    @Test
    public void NonEmptyOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();
        EItem item1 = new EItem("Logitech xxx", EItemType.MOUSE, 50.00);
        items.add(item1);
        items.add(item1);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 100.00;
    }

    @Test
    public void LessFiveProcessorOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();
        EItem item1 = new EItem("Amd xxx", EItemType.PROCESSOR, 10.00);
        EItem item2 = new EItem("Amd xxx", EItemType.PROCESSOR, 20.00);
        EItem item3 = new EItem("Amd xxx", EItemType.PROCESSOR, 30.00);
        EItem item4 = new EItem("Amd xxx", EItemType.PROCESSOR, 40.00);
        EItem item5 = new EItem("Amd xxx", EItemType.PROCESSOR, 50.00);
        EItem item6 = new EItem("Logitech xxx", EItemType.MOUSE, 60.00);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 210.00;
    }

    @Test
    public void MoreFiveProcessorOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();
        EItem item1 = new EItem("Amd xxx", EItemType.PROCESSOR, 10.00);
        EItem item2 = new EItem("Amd xxx", EItemType.PROCESSOR, 20.00);
        EItem item3 = new EItem("Amd xxx", EItemType.PROCESSOR, 30.00);
        EItem item4 = new EItem("Amd xxx", EItemType.PROCESSOR, 40.00);
        EItem item5 = new EItem("Amd xxx", EItemType.PROCESSOR, 50.00);
        EItem item6 = new EItem("Amd xxx", EItemType.PROCESSOR, 60.00);
        EItem item7 = new EItem("Logitech xxx", EItemType.MOUSE, 70.00);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        items.add(item7);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 275.00;
    }

    @Test
    public void LessTenMouseOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();
        EItem item1 = new EItem("Logitech xxx", EItemType.MOUSE, 5.00);
        EItem item2 = new EItem("Logitech xxx", EItemType.MOUSE, 6.00);
        EItem item3 = new EItem("Logitech xxx", EItemType.MOUSE, 7.00);
        EItem item4 = new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 130.00);
        EItem item5 = new EItem("Intel i5 xxx", EItemType.PROCESSOR, 73.41);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 221.41;
    }

    @Test
    public void MoreTenProcessorOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();

        EItem item1 = new EItem("Logitech xxx", EItemType.MOUSE, 5.00);
        EItem item2 = new EItem("Logitech xxx", EItemType.MOUSE, 6.00);
        EItem item3 = new EItem("Logitech xxx", EItemType.MOUSE, 7.00);
        EItem item4 = new EItem("Logitech xxx", EItemType.MOUSE, 5.00);
        EItem item5 = new EItem("Logitech xxx", EItemType.MOUSE, 6.00);
        EItem item6 = new EItem("Logitech xxx", EItemType.MOUSE, 7.00);
        EItem item7 = new EItem("Logitech xxx", EItemType.MOUSE, 5.00);
        EItem item8 = new EItem("Logitech xxx", EItemType.MOUSE, 6.00);
        EItem item9 = new EItem("Logitech xxx", EItemType.MOUSE, 7.00);
        EItem item10 = new EItem("Logitech xxx", EItemType.MOUSE, 5.00);
        EItem item11 = new EItem("Logitech xxx", EItemType.MOUSE, 6.00);
        EItem item12 = new EItem("Logitech xxx", EItemType.MOUSE, 7.00);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.add(item6);
        items.add(item7);
        items.add(item8);
        items.add(item9);
        items.add(item10);
        items.add(item11);
        items.add(item12);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 67.00;
    }
}
