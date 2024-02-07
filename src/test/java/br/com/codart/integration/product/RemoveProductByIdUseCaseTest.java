package br.com.codart.integration.product;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.PostgresContainerConfig;
import br.com.codart.domain.product.ProductID;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import br.com.codart.domain.exceptions.NotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.domain.exceptions.LimitExceededException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.application.usecase.product.remove.RemoveProductByIdUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class RemoveProductByIdUseCaseTest {

    private final CreateProductUseCase createProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final RemoveProductByIdUseCase removeProductByIdUseCase;

    @Autowired
    public RemoveProductByIdUseCaseTest(
            CreateProductUseCase createProductUseCase,
            FindProductByIdUseCase findProductByIdUseCase,
            RemoveProductByIdUseCase removeProductByIdUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.removeProductByIdUseCase = removeProductByIdUseCase;
    }


    @Test
    @DisplayName(
            """
                Given some previously registered products
                When product removal use case is called
                Then these products must be removed
            """
    )
    public void removeProducts() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var expectedProduct01 = new CreateProductInput("Eco-friendly Water Bottle", 15.99, brandId.getValue(), Set.of());
        final var actualProductIds = assertDoesNotThrow(() -> createProductUseCase.execute(List.of(expectedProduct01)));

        assertNotNull(findProductByIdUseCase.execute(actualProductIds.get(0)), "product must be found");

        final var output = assertDoesNotThrow(() -> removeProductByIdUseCase.execute(actualProductIds));

        assertEquals(actualProductIds.size(), output.found().size(), "Should contain the IDs of removed");

        assertThrows(NotFoundException.class, () -> findProductByIdUseCase.execute(actualProductIds.get(0)), "product must not be found");
    }

    @Test
    @DisplayName(
            """
                Given a list of product IDs to remove, including both existing and non-existing IDs
                When the remove operation is executed
                Then the system should remove the products with the IDs that exist
                And the system should return a summary including both the IDs of the products removed and the IDs of the products not found
            """
    )
    public void removeProducts_mixedFoundAndNotFoundIds() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");
        final var expectedProduct01 = new CreateProductInput("Eco-friendly Water Bottle", 98.75, brandId.getValue(), Set.of());
        final var expectedProduct02 = new CreateProductInput("Cheese", 200.0, brandId.getValue(), Set.of());

        var createdProductIds = new ArrayList<>(assertDoesNotThrow(() -> createProductUseCase.execute(List.of(expectedProduct01, expectedProduct02))));

        String uniqueProductId = ProductID.unique().getValue();
        createdProductIds.add(uniqueProductId);

        final var output = assertDoesNotThrow(() -> removeProductByIdUseCase.execute(createdProductIds));

        List<String> expectedFoundIds = createdProductIds.subList(0, createdProductIds.size() - 1);

        Assertions.assertTrue(output.found().containsAll(expectedFoundIds),
                "Should contain the IDs of removed products");

        Assertions.assertTrue(output.notFound().contains(uniqueProductId),
                "Should contain the ID of the non-existing product");

        Assertions.assertEquals(expectedFoundIds.size(), output.found().size(), "The number of found products should match the expected");
        Assertions.assertEquals(1, output.notFound().size(), "The number of not found products should match the expected");
    }

    @Test
    @DisplayName(
            """
                Given I have a list of product IDs to remove
                And the maximum limit of items allowed per operation is 10
                When I attempt to remove 11 product IDs
                Then the operation should be rejected
                And a LimitExceededException should be thrown
                And a message indicating the limit of 10 items per operation is exceeded should be displayed
            """
    )
    public void removeProducts_validate_limit() {

        final var brandId = BrandID.from("1384754d-d642-4389-8d81-6e35bb90591a");

        final var group1 = productGroup02(brandId);
        final var group2 = productGroup01(brandId);

        final var expectedMessageError = "Limit of 10 items per operation exceeded.";


        final var productIdGroup1 = assertDoesNotThrow(() -> createProductUseCase.execute(group1));
        final var productIdGroup2 = assertDoesNotThrow(() -> createProductUseCase.execute(group2));

        List<String> actualProductIds = Stream.concat(
                productIdGroup1.stream(),
                productIdGroup2.stream()
        ).toList();

        assertEquals(11, actualProductIds.size(), "Should contain 11 products");

        final var output = assertThrows(LimitExceededException.class, () -> removeProductByIdUseCase.execute(actualProductIds));

        assertEquals(expectedMessageError, output.getMessage());

    }

    @NotNull
    private static List<CreateProductInput> productGroup02(BrandID brandId) {
        final var expectedProduct01 = new CreateProductInput("Eco-friendly Water Bottle", 15.99, brandId.getValue(), Set.of());
        final var expectedProduct02 = new CreateProductInput("Wireless Bluetooth Headphones", 59.95, brandId.getValue(), Set.of());
        final var expectedProduct03 = new CreateProductInput("Organic Cotton T-Shirt", 24.50, brandId.getValue(), Set.of());
        final var expectedProduct04 = new CreateProductInput("Portable Charger Power Bank", 19.90, brandId.getValue(), Set.of());
        final var expectedProduct05 = new CreateProductInput("Smartwatch Fitness Tracker", 219.99, brandId.getValue(), Set.of());

        return List.of(
                expectedProduct01,
                expectedProduct02,
                expectedProduct03,
                expectedProduct04,
                expectedProduct05
        );
    }

    @NotNull
    private static List<CreateProductInput> productGroup01(BrandID brandId) {
        final var expectedProduct06 = new CreateProductInput("Insulated Stainless Steel Travel Mug", 22.45, brandId.getValue(), Set.of());
        final var expectedProduct07 = new CreateProductInput("LED Desk Lamp with USB Charging Port", 33.75, brandId.getValue(), Set.of());
        final var expectedProduct08 = new CreateProductInput("Bamboo Cutting Board Set", 27.99, brandId.getValue(), Set.of());
        final var expectedProduct09 = new CreateProductInput("Reusable Silicone Food Bags", 15.60, brandId.getValue(), Set.of());
        final var expectedProduct10 = new CreateProductInput("Compact Folding Umbrella", 18.25, brandId.getValue(), Set.of());
        final var expectedProduct11 = new CreateProductInput("Smartphone 5G", 1245.55, brandId.getValue(), Set.of());

        return List.of(
                expectedProduct06,
                expectedProduct07,
                expectedProduct08,
                expectedProduct09,
                expectedProduct10,
                expectedProduct11
        );
    }

}
