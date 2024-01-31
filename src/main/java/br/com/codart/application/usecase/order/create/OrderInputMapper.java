package br.com.codart.application.usecase.order.create;

import java.util.List;
import br.com.codart.domain.order.Order;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.payment.PaymentType;
import br.com.codart.application.usecase.InputMapper;

public class OrderInputMapper implements InputMapper<OrderInput, Order> {
    private final OrderItemInputMapper orderItemInputMapper = new OrderItemInputMapper();

    @Override
    public Order toDomain(OrderInput input) {
        List<OrderItem> orderItems = input.orderItems().stream()
                .map(orderItemInputMapper::toDomain)
                .toList();

        PaymentType paymentType = PaymentType.valueOf(input.paymentType().toUpperCase());

        return Order.newOrder(orderItems, paymentType, null);
    }
}


