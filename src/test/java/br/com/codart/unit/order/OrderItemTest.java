package br.com.codart.unit.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.product.ProductID;

public class OrderItemTest {


    @Test
    @DisplayName(
            """
                Given I have a valid product ID, a quantity, and a unit price
                When I create a new order item using these details
                Then the order item should be successfully created with the provided product ID, quantity, and unit price
                And a unique order item ID should be generated for the order item
            """
    )
    void shouldCreateOrderItem() {

        ProductID productId = ProductID.unique();
        int quantity = 10;
        double unitPrice = 100.0;

        OrderItem orderItem = OrderItem.newOrderItem(productId, quantity, unitPrice);

        Assertions.assertEquals(orderItem.getProductId(), productId);
        Assertions.assertEquals(orderItem.getQuantity(), quantity);
        Assertions.assertEquals(orderItem.getUnitPrice(), unitPrice);
    }

}
