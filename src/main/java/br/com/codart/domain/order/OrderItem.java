package br.com.codart.domain.order;

import br.com.codart.domain.product.ProductID;

public class OrderItem {

    private final OrderItemID id;
    private final int quantity;
    private final ProductID productId;
    private final double unitPrice;

    private OrderItem(OrderItemID id, ProductID productId, int quantity, double unitPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static OrderItem newOrderItem(
            ProductID productId,
            int quantity,
            double unitPrice
    ) {

        final var id = OrderItemID.unique();

        return new OrderItem(
                id,
                productId,
                quantity,
                unitPrice
        );
    }

    public static OrderItem with(
            OrderItemID id,
            ProductID productId,
            int quantity,
            double unitPrice
    ){

        return new OrderItem(
                id,
                productId,
                quantity,
                unitPrice
        );
    }

    public ProductID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public OrderItemID getId() {
        return id;
    }
}
