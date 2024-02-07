package br.com.codart.integration.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import br.com.codart.domain.utils.SearchQuery;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.product.findall.FindAllProductInput;
import br.com.codart.application.usecase.product.findall.FindAllProductUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class FindAllProductUseCaseTest {

    private final FindAllProductUseCase findAllProductUseCase;

    @Autowired
    public FindAllProductUseCaseTest(FindAllProductUseCase findAllProductUseCase) {
        this.findAllProductUseCase = findAllProductUseCase;
    }

    @Test
    @DisplayName(
            """
                Given that a list of products already registered
                When the search service is called
                And both the category, brand and the product itself have an active status
                So all products must be listed
            """
    )
    public void findAllProductTest() {

        final var expectedTotalElements = 2;

        final var input = new FindAllProductInput(
                "",
                SearchQuery.with(0, 10, "", "price", "ASC")
        );

        final var output = assertDoesNotThrow(() -> findAllProductUseCase.execute(input));

        assertEquals(expectedTotalElements, output.totalElements());
    }

}
