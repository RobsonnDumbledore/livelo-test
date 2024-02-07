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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;
import br.com.codart.application.usecase.product.changestatus.ChangeProductStatusInput;
import br.com.codart.application.usecase.product.changestatus.ChangeStatusProductUseCase;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class ChangeStatusProductUseCaseTest {

    private final CreateProductUseCase createProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final ChangeStatusProductUseCase changeStatusProductUseCase;

    @Autowired
    public ChangeStatusProductUseCaseTest(
            CreateProductUseCase createProductUseCase,
            FindProductByIdUseCase findProductByIdUseCase,
            ChangeStatusProductUseCase changeStatusProductUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.changeStatusProductUseCase = changeStatusProductUseCase;
    }

    @Test
    @DisplayName(
            """
                Given that a product with active status
                When the status change service is called
                So this product has its status changed
            """
    )
    public void changeStatusProductUseCaseTest() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var categoryId = CategoryID.from("178c979d-53d2-4b9c-86a4-3529c87c933b");
        final var expectedProduct01 = new CreateProductInput("Screwdriver", 8.99, brandId.getValue(), Set.of(categoryId.getValue()));

        final var actualProductIds = assertDoesNotThrow(() -> createProductUseCase.execute(List.of(expectedProduct01)));

        final var input = new ChangeProductStatusInput(false, List.of(actualProductIds.get(0)));

        assertDoesNotThrow(() -> changeStatusProductUseCase.execute(input));

        final var productUpdated = assertDoesNotThrow(() -> findProductByIdUseCase.execute(actualProductIds.get(0)));

        assertEquals("Screwdriver", productUpdated.name());
        assertEquals(8.99, productUpdated.price());
        assertEquals(false, productUpdated.active());
        assertEquals(brandId.getValue(), productUpdated.brandId());
        assertTrue(productUpdated.categories().contains(categoryId.getValue()));
        assertEquals(1, productUpdated.categories().size());

    }

}
