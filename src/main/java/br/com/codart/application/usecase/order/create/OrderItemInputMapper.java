package br.com.codart.application.usecase.order.create;

import br.com.codart.application.usecase.InputMapper;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.product.ProductID;

public class OrderItemInputMapper implements InputMapper<OrderItemInput, OrderItem> {
    @Override
    public OrderItem toDomain(OrderItemInput input) {
        ProductID productId = ProductID.from(input.productId());
        return OrderItem.newOrderItem(productId, input.quantity(), input.unitPrice());
    }
}

