package br.com.codart.application.usecase.order.create;

import br.com.codart.application.usecase.discount.find.FindDiscountByPaymentNameUseCase;
import br.com.codart.domain.order.Order;
import br.com.codart.domain.order.OrderGateway;
import br.com.codart.application.usecase.InputMapper;
import br.com.codart.domain.order.OrderID;
import br.com.codart.domain.order.OrderItem;
import br.com.codart.domain.payment.PaymentType;

import java.math.BigDecimal;
import java.util.List;

public class DefaultCreateOrder extends CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final InputMapper<OrderItemInput, OrderItem> orderItemInputMapper;
    private final FindDiscountByPaymentNameUseCase findDiscountByPaymentNameUseCase;

    public DefaultCreateOrder(
            OrderGateway orderGateway,
            InputMapper<OrderItemInput, OrderItem> orderItemInputMapper,
            FindDiscountByPaymentNameUseCase findDiscountByPaymentNameUseCase
    ) {

        this.orderGateway = orderGateway;
        this.orderItemInputMapper = orderItemInputMapper;
        this.findDiscountByPaymentNameUseCase = findDiscountByPaymentNameUseCase;
    }

    @Override
    public String execute(OrderInput input) {

        List<OrderItem> orderItems = input.orderItems().stream()
                .map(orderItemInputMapper::toDomain)
                .toList();

        PaymentType paymentType = PaymentType.valueOf(input.paymentType().toUpperCase());
        final var discount = findDiscountByPaymentNameUseCase.execute(input.paymentType());

        final var order = Order.newOrder(orderItems, paymentType, discount);

        final var orderId = orderGateway.createOrder(order);

       return orderId.getValue();
    }
}
