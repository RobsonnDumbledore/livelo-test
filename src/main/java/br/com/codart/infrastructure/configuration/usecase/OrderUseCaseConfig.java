package br.com.codart.infrastructure.configuration.usecase;

import br.com.codart.domain.order.OrderGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.codart.application.usecase.order.create.CreateOrderUseCase;
import br.com.codart.application.usecase.order.create.DefaultCreateOrder;
import br.com.codart.application.usecase.order.create.OrderItemInputMapper;
import br.com.codart.application.usecase.discount.find.FindDiscountByPaymentNameUseCase;

@Configuration
public class OrderUseCaseConfig {

    private final OrderGateway orderGateway;
    private final FindDiscountByPaymentNameUseCase findDiscountByPaymentNameUseCase;

    public OrderUseCaseConfig(
            OrderGateway orderGateway,
            FindDiscountByPaymentNameUseCase findDiscountByPaymentNameUseCase
    ) {
        this.orderGateway = orderGateway;
        this.findDiscountByPaymentNameUseCase = findDiscountByPaymentNameUseCase;
    }

    @Bean
    public CreateOrderUseCase createCreateOrderUseCase() {
        return new DefaultCreateOrder(
                orderGateway,
                new OrderItemInputMapper(),
                findDiscountByPaymentNameUseCase
        );
    }
}
