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
}
