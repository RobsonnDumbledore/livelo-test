package br.com.codart.integration.product;

import java.util.Set;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.PostgresContainerConfig;
import br.com.codart.domain.category.CategoryID;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.application.usecase.product.update.UpdateProductInput;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;
import br.com.codart.application.usecase.product.update.UpdateProductUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class UpdateProductUseCaseTest {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;

    @Autowired
    public UpdateProductUseCaseTest(
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            FindProductByIdUseCase findProductByIdUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
    }

    @Test
    @DisplayName(
            """
                Given an existing product with specific name, price, active status, brandId, and a list of categoryId
                And a request to update the product's name to "New Product Name", price to "New Price", active status to false, brandId to "New BrandId", and add a new categoryId to the list
                When the update product use case is executed
                Then the product's information should be updated with the new name, price, active status, brandId, and the new categoryId should be added to the list
                And the updated product information should be persisted in the database
            """
    )
    public void testUpdateProduct() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var categoryId = CategoryID.from("178c979d-53d2-4b9c-86a4-3529c87c933b");
        final var expectedProduct01 = new CreateProductInput("Eco-friendly Water Bottle", 15.99, brandId.getValue(), Set.of(categoryId.getValue()));
        final var actualProductIds = assertDoesNotThrow(() -> createProductUseCase.execute(List.of(expectedProduct01)));

        final var input = new UpdateProductInput(
                actualProductIds.get(0),
                "New Product Name",
                25.0,
                true,
                brandId.getValue(),
                Set.of(categoryId.getValue())
        );

        updateProductUseCase.execute(input);

        final var productUpdated = assertDoesNotThrow(() -> findProductByIdUseCase.execute(actualProductIds.get(0)));

        assertEquals("New Product Name", productUpdated.name());
        assertEquals(25.0, productUpdated.price());
        assertEquals(true, productUpdated.active());
        assertEquals(brandId.getValue(), productUpdated.brandId());
        assertEquals(Set.of(categoryId.getValue()), productUpdated.categories());

    }

}
