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
    public void EmptyBasketTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new LinkedList<>();

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 0.00;
    }

    @Test
    public void NonEmptyBasketTest() throws TotalAmountExceededException {
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
}
