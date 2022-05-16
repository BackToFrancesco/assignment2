////////////////////////////////////////////////////////////////////
// Alessandro Cavaliere 1224440
// Filippo Brugnolaro 1217321
////////////////////////////////////////////////////////////////////
package it.unipd.mtss;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EShopBillTest {
    private EShopBill bill = new EShopBill();
    private final User user = new User("TEST", "Test", "Testing", "test@test.com", Date.from(Instant.EPOCH));

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
        List<EItem> items = new ArrayList<>();
        EItem item1 = new EItem("Logitech xxx", EItemType.MOUSE, 50.00);
        items.add(item1);
        items.add(item1);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 100.00;
    }

    @Test
    public void LessThanFiveProcessorDiscountOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 10.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 20.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 30.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 40.00));
        items.add(new EItem("Amd xxx", EItemType.PROCESSOR, 50.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 60.00));

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 210.00;
    }

    @Test
    public void MoreThanFiveProcessorDiscountOrderTest() throws TotalAmountExceededException {
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
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 275.00;
    }

    @Test
    public void LessThanTenMouseOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 5.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 6.00));
        items.add(new EItem("Logitech xxx", EItemType.MOUSE, 7.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 130.00));
        items.add(new EItem("Intel i5 xxx", EItemType.PROCESSOR, 73.41));

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 221.41;
    }

    @Test
    public void MoreThanTenMouseOrderTest() throws TotalAmountExceededException {
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
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 67.00;
    }

    @Test
    public void NumberMouseEqualsKeyboardGreaterThanZeroDiscountTest() throws TotalAmountExceededException {
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
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 160.00;
    }

    @Test
    public void NumberMouseEqualsKeyboardEqualZeroDiscountTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 100.00));

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 300.00;
    }

    @Test
    public void NumberMouseDiffersKeyboardDiscountTest() throws TotalAmountExceededException {
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
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 450.00;
    }

    @Test
    public void LessThanOneThousandTotalOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 340.00));
        items.add(new EItem("Intel i9 10900k", EItemType.PROCESSOR, 449.00));

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 789.00;
    }

    @Test
    public void MoreThanOneThousandTotalOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        items.add(new EItem("Asus Sabertooth xxx", EItemType.MOTHERBOARD, 640.00));
        items.add(new EItem("Intel i9 10900k", EItemType.PROCESSOR, 449.00));

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 980.10;
    }

    @Test
    public void ProcessorAndTotalAmountDiscountOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericProcessor = new EItem("Intel i9 10900k", EItemType.PROCESSOR, 449.00);
        EItem specificProcessor = new EItem("Intel i5 5280", EItemType.PROCESSOR, 150.00);
        EItem item1 = new EItem("Logitech M185", EItemType.MOUSE, 15.00);

        items.add(item1);
        items.add(specificProcessor);
        for (int i = 0; i < 5; i++)
            items.add(genericProcessor);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 2410 - 241 - 75;
    }

    @Test
    public void ProcessorAndNoTotalAmountDiscountOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericProcessor = new EItem("Intel i2 1024", EItemType.PROCESSOR, 150.00);
        EItem specificProcessor = new EItem("Intel Pentium", EItemType.PROCESSOR, 90.00);

        items.add(specificProcessor);
        for (int i = 0; i < 6; i++)
            items.add(genericProcessor);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 990 - 45;
    }

    @Test
    public void MouseAndTotalAmountDiscountOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericMouse = new EItem("Logitech M185", EItemType.MOUSE, 15.00);
        EItem processor = new EItem("Intel i9 10900k", EItemType.PROCESSOR, 849.00);

        items.add(processor);
        for (int i = 0; i < 15; i++)
            items.add(genericMouse);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 1074 - 107.4 - 15;
    }

    @Test
    public void MouseAndNoTotalAmountDiscountOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> items = new ArrayList<>();
        EItem genericMouse = new EItem("Logitech M185", EItemType.MOUSE, 15.00);

        for (int i = 0; i < 20; i++)
            items.add(genericMouse);

        // Act
        double total = bill.getOrderPrice(items, user);

        // Assert
        assert total == 300 - 15;
    }

    @Test(expected = TotalAmountExceededException.class)
    public void Over30ItemsOrderTest() throws TotalAmountExceededException {
        // Arrange
        List<EItem> itemList = new LinkedList<>();
        EItem item = new EItem("Asus Sabertooth xxx", EItemType.KEYBOARD, 50.00);

        for (int i = 0; i < 31; i++) {
            itemList.add(item);
        }

        // Act
        bill.getOrderPrice(itemList, user);

        // Assert
        fail();

    }
}