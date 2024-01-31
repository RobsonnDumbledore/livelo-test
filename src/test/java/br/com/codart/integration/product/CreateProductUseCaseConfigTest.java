package br.com.codart.integration.product;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import org.springframework.context.annotation.Import;
import br.com.codart.domain.exceptions.BusinessException;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.infrastructure.product.persistence.ProductRepository;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;

@SpringBootTest
@Import(PostgresContainerConfig.class)
public class CreateProductUseCaseConfigTest {

    private final CreateProductUseCase createProductUseCase;
    private final ProductRepository productRepository;


    @Autowired
    public CreateProductUseCaseConfigTest(
            CreateProductUseCase createProductUseCase,
            ProductRepository productRepository
    ) {
        this.createProductUseCase = createProductUseCase;
        this.productRepository = productRepository;
    }


    @Test
    @DisplayName(
            """
                Given the system has no existing product named "Eco-friendly Water Bottle"
                When the create product use case is invoked with the product name "Eco-friendly Water Bottle" and price 15.99
                Then a new product should be successfully created
                And the new product ID should be generated and returned
            """
    )
    public void createProductUseCaseTest() {

        final var expectedProduct01 = new CreateProductInput("Eco-friendly Water Bottle", 15.99);

        final var actualProductIds = assertDoesNotThrow(() -> createProductUseCase.execute(List.of(expectedProduct01)));

        assertFalse(actualProductIds.isEmpty());

        productRepository.findById(actualProductIds.get(0)).ifPresent(product -> {
            assertEquals("Eco-friendly Water Bottle", product.getProductName());
            assertEquals(15.99, product.getPrice());
        });
    }

    @Test
    @DisplayName(
            """
                Given valid product information is provided except for the price which is negative, e.g., -5.00
                When the create product use case is invoked with this information
                Then the product creation should fail
                And an exception stating "price cannot be negative" should be thrown
            """
    )
    public void validateProductUseCaseInvalidPrice() {

        final var expectedMessageError = "price cannot be negative";
        final var expectedProduct01 = new CreateProductInput("Toothpaste", -5.00);

        final var actualMessageError = assertThrows(
                BusinessException.class, () -> createProductUseCase.execute(List.of(expectedProduct01))
        );

        assertEquals(expectedMessageError, actualMessageError.getMessage());
    }

}
