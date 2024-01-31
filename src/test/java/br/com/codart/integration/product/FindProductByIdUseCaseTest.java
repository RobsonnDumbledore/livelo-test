package br.com.codart.integration.product;

import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import org.springframework.context.annotation.Import;
import br.com.codart.domain.exceptions.NotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;

@SpringBootTest
@Import(PostgresContainerConfig.class)
public class FindProductByIdUseCaseTest {

    private final FindProductByIdUseCase findProductByIdUseCase;
    private final CreateProductUseCase createProductUseCase;


    @Autowired
    public FindProductByIdUseCaseTest(
            FindProductByIdUseCase findProductByIdUseCase,
            CreateProductUseCase createProductUseCase
    ) {
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.createProductUseCase = createProductUseCase;
    }

    @Test
    @DisplayName(
            """
                Given there are products stored in the system
                When I search for a product by its unique ID
                Then the system should return the product"
            """
    )
    public void findProductByIdUseCaseTest() {

        final var expectedProduct01 = new CreateProductInput("Eco-friendly Water Bottle", 15.99);
        final var expectedProductId = createProductUseCase.execute(List.of(expectedProduct01)).get(0);

        final var actualProduct = findProductByIdUseCase.execute(expectedProductId);

        assertEquals(expectedProductId, actualProduct.id());
    }

    @Test
    @DisplayName(
            """
                Given there are products stored in the system
                When I search for a product by a ID that does not exist in the system, for example, "123e4567-e89b-12d3-a456-426614174000"
                Then the system should return a message indicating that no product was found with that ID
            """
    )
    public void validateFindProductByIdUseCaseTest() {

        final var productId = UUID.randomUUID().toString();
        final var expectedMessageError = "product not found for id: ".concat(productId);

        final var actualMessageError = assertThrows(
                NotFoundException.class, () -> findProductByIdUseCase.execute(productId)
        );

        assertEquals(expectedMessageError, actualMessageError.getMessage());
    }

}
