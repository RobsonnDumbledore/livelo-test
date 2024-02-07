package br.com.codart.integration.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import br.com.codart.domain.utils.SearchQuery;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandInput;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class FindProductByBrandUseCaseTest {

    private final FindProductByBrandUseCase findProductByBrandUseCase;

    @Autowired
    public FindProductByBrandUseCaseTest(FindProductByBrandUseCase findProductByBrandUseCase) {
        this.findProductByBrandUseCase = findProductByBrandUseCase;
    }


    @Test
    @DisplayName(
            """
                Given that a list of products already registered
                When the brand search use case is called
                And both the brand, category and the product itself have an active status
                So these products must be listed
            """)
    public void findProductByBrandUseCaseTest() {

        final var input = new FindProductByBrandInput(
                "Brand A",
                SearchQuery.with(0, 10, "", "price", "ASC")
        );

        final var output = assertDoesNotThrow(() -> findProductByBrandUseCase.execute(input));

        output.content().stream().findFirst().ifPresent(product -> {
                    assertNotNull(product.id());
                    assertEquals("Laptop", product.name());
                    assertTrue(product.active());
                    assertEquals(1200.00, product.price());
                }
        );

        assertEquals(1, output.totalElements());
        assertEquals(1, output.content().size());

    }

}
