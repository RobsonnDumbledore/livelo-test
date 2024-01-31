package br.com.codart.unit.order;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import br.com.codart.domain.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.payment.PaymentType;
import br.com.codart.domain.product.ProductID;

public class OrderTest {

    @Test
    @DisplayName(
            """
                Given I have a list of valid OrderItems and a valid PaymentType
                When I create a new order using these items and payment type
                Then the order should be successfully created with the provided items and payment type
                And the total of the order should be correctly calculated with any applicable discounts
            """
    )
    public void createOrder() {

        final var expectedTotal = 900.0;
        final var order = Order.newOrder(
                List.of(OrderItem.newOrderItem(ProductID.unique(), 10, 100.0)),
                PaymentType.CREDIT_CARD,
                BigDecimal.TEN
        );

        final var actualTotal = order.getTotal();

        Assertions.assertEquals(order.getPaymentType(), PaymentType.CREDIT_CARD);
        Assertions.assertEquals(order.getItems().size(), 1);
        Assertions.assertEquals(expectedTotal, actualTotal);
    }

}
