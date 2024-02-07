package br.com.codart.integration.order;

import java.util.Set;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.PostgresContainerConfig;
import br.com.codart.domain.category.CategoryID;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.application.usecase.order.create.OrderInput;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.order.create.OrderItemInput;
import br.com.codart.infrastructure.order.persistence.OrderRepository;
import br.com.codart.application.usecase.order.create.CreateOrderUseCase;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class CreateOrderUseCaseTest {

    private final OrderRepository orderRepository;
    private final CreateOrderUseCase createOrderUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;

    @Autowired
    public CreateOrderUseCaseTest(
            OrderRepository orderRepository,
            CreateOrderUseCase createOrderUseCase,
            CreateProductUseCase createProductUseCase,
            FindProductByIdUseCase findProductByIdUseCase
    ) {
        this.orderRepository = orderRepository;
        this.createOrderUseCase = createOrderUseCase;
        this.createProductUseCase = createProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
    }

    @Test
    @DisplayName(
            """
                Given I have a list of valid order items and the payment type is CREDIT_CARD
                When I invoke the create order use case with these items and BANK_SLIP as payment type
                Then an order should be successfully created
                And the order should contain the specified items and the payment type CREDIT_CARD
                And a unique order ID should be generated and returned
                And the total of the order should be correctly calculated with the appropriate discount for CREDIT_CARD
            """
    )
    public void createOrderWithPayment_CREDIT_CARD() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var categoryId = CategoryID.from("0a86a5ea-4b0b-4580-9abb-0a754460e6a5");
        final var product = CreateProductInput.of("Eco-friendly Water Bottle", 15.99, brandId.getValue(), Set.of(categoryId.getValue()));
        final var outputProductId = createProductUseCase.execute(List.of(product));

        final var expectedProduct = findProductByIdUseCase.execute(outputProductId.get(0));

        final var orderItemInput = new OrderItemInput(1, expectedProduct.id(), expectedProduct.price());
        final var orderInput = new OrderInput(List.of(orderItemInput), "CREDIT_CARD");

        final var orderId = assertDoesNotThrow(() -> createOrderUseCase.execute(orderInput));

        orderRepository.findById(orderId).ifPresent(order -> {
            Assertions.assertEquals("CREDIT_CARD", order.getPaymentType(), "Payment type mismatch");
            Assertions.assertNotNull(order.getOrderId(), "Order ID should not be null");
            Assertions.assertEquals(15.19, order.getTotal(), "Total should not be zero");
        });
    }

    @Test
    @DisplayName(
            """
                Given I have a list of valid order items and the payment type is BANK_SLIP
                When I invoke the create order use case with these items and BANK_SLIP as payment type
                Then an order should be successfully created
                And the order should contain the specified items and the payment type BANK_SLIP
                And a unique order ID should be generated and returned
                And the total of the order should be correctly calculated with the appropriate discount for BANK_SLIP
            """
    )
    public void createOrderWithPayment_BANK_SLIP() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var categoryId = CategoryID.from("0a86a5ea-4b0b-4580-9abb-0a754460e6a5");
        final var product = CreateProductInput.of("Eco-friendly Water Bottle", 250.45, brandId.getValue(), Set.of(categoryId.getValue()));
        final var expectedProductId = createProductUseCase.execute(List.of(product));
        final var expectedProduct = findProductByIdUseCase.execute(expectedProductId.get(0));
        final var orderItemInput = new OrderItemInput(1, expectedProduct.id(), expectedProduct.price());
        final var orderInput = new OrderInput(List.of(orderItemInput), "BANK_SLIP");

       final var orderId = assertDoesNotThrow(() -> createOrderUseCase.execute(orderInput));

        orderRepository.findById(orderId).ifPresent(order -> {
            Assertions.assertEquals("BANK_SLIP", order.getPaymentType(), "Payment type mismatch");
            Assertions.assertNotNull(order.getOrderId(), "Order ID should not be null");
            Assertions.assertEquals(225.41, order.getTotal(), "Total should not be zero");
        });
    }

    @Test
    @DisplayName(
            """
                Given I have a list of valid order items and the payment type is BANK_TRANSFER
                When I invoke the create order use case with these items and BANK_TRANSFER as payment type
                Then an order should be successfully created
                And the order should contain the specified items and the payment type BANK_TRANSFER
                And a unique order ID should be generated and returned
                And the total of the order should be correctly calculated
            """
    )
    public void createOrderWithPayment_BANK_TRANSFER() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var categoryId = CategoryID.from("0a86a5ea-4b0b-4580-9abb-0a754460e6a5");
        final var expectedProductId = createProductUseCase
                .execute(List.of(new CreateProductInput("Eco-friendly Water Bottle", 4518.00, brandId.getValue(), Set.of(categoryId.getValue()))));
        final var expectedProduct = findProductByIdUseCase.execute(expectedProductId.get(0));
        final var orderItemInput = new OrderItemInput(1, expectedProduct.id(), expectedProduct.price());
        final var orderInput = new OrderInput(List.of(orderItemInput), "BANK_TRANSFER");

        final var orderId = assertDoesNotThrow(() -> createOrderUseCase.execute(orderInput));

        orderRepository.findById(orderId).ifPresent(order -> {
            Assertions.assertEquals("BANK_TRANSFER", order.getPaymentType(), "Payment type mismatch");
            Assertions.assertNotNull(order.getOrderId(), "Order ID should not be null");
            Assertions.assertEquals(4179.15, order.getTotal(), "Total should not be zero");
        });
    }

}
