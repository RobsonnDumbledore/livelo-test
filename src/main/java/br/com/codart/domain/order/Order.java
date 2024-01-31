package br.com.codart.domain.order;

import java.math.BigDecimal;
import java.util.List;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.discount.Discount;
import br.com.codart.domain.payment.PaymentType;
import br.com.codart.domain.exceptions.BusinessException;
import br.com.codart.domain.discount.strategy.DiscountStrategyRegistry;

public class Order {

    private final OrderID id;
    private final double total;
    private final List<OrderItem> items;
    private final PaymentType paymentType;
    private final BigDecimal percentageDiscount;

    private Order(
            OrderID id,
            List<OrderItem> items,
            PaymentType paymentType,
            BigDecimal percentageDiscount
    ) {

        validateItems(items);
        this.id = id;
        this.items = items;
        this.paymentType = paymentType;
        this.total = calculateTotalWithDiscounts(percentageDiscount);
        this.percentageDiscount = percentageDiscount;
    }

    public static Order newOrder(
            final List<OrderItem> items,
            final PaymentType paymentType,
            final BigDecimal percentageDiscount
    ) {
        return new Order(
                OrderID.unique(),
                items,
                paymentType,
                percentageDiscount
        );
    }

    public static Order with(
            final OrderID id,
            final List<OrderItem> items,
            final PaymentType paymentType,
            final BigDecimal percentageDiscount
    ) {

        return new Order(
                id,
                items,
                paymentType,
                percentageDiscount
        );
    }

    private double calculateTotalWithDiscounts(BigDecimal discountRate) {

        double total = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();

        Discount paymentDiscount = DiscountStrategyRegistry.getDiscount(paymentType, discountRate);
        Price totalAsPrice = Price.of(total);
        return paymentDiscount.applyDiscount(totalAsPrice).getValue();
    }

    private void validateItems(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException("cannot create an order without items");
        }
    }

    public double getTotal() {
        return total;
    }

    public OrderID getId() {
        return id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}
