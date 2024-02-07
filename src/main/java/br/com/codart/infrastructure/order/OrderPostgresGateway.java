package br.com.codart.infrastructure.order;

import br.com.codart.domain.order.Order;
import br.com.codart.domain.order.OrderGateway;
import br.com.codart.domain.order.OrderID;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.infrastructure.order.persistence.OrderEntity;
import br.com.codart.infrastructure.order.persistence.OrderRepository;

@Component
public class OrderPostgresGateway implements OrderGateway {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderPostgresGateway(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderID createOrder(Order order) {

        final var orderEntity = OrderEntity.fromDomain(order);

        final var savedOrder = orderRepository.save(orderEntity);

        return OrderID.from(savedOrder.getOrderId());

    }
}
