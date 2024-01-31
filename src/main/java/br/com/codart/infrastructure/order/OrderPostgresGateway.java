package br.com.codart.infrastructure.order;

import br.com.codart.domain.order.Order;
import br.com.codart.domain.order.OrderGateway;
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
    public void createOrder(Order order) {

        final var orderEntity = OrderEntity.fromDomain(order);

        orderRepository.save(orderEntity);

    }
}
