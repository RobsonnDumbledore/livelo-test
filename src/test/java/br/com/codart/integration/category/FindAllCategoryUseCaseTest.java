package br.com.codart.integration.category;

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
import br.com.codart.application.usecase.category.findall.FindAllCategoryInput;
import br.com.codart.application.usecase.category.findall.FindAllCategoryUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class FindAllCategoryUseCaseTest {

    private final FindAllCategoryUseCase findAllCategoryUseCase;

    @Autowired
    public FindAllCategoryUseCaseTest(FindAllCategoryUseCase findAllCategoryUseCase) {
        this.findAllCategoryUseCase = findAllCategoryUseCase;
    }

    @Test
    @DisplayName(
            """
                Given that a list of categories already registered
                When the category search service is called
                And these categories are active
                Then these categories must be listed
            """
    )
    public void findAllCategoriesTest() {

        final var expectedTotalElements = 3;

        final var input = new FindAllCategoryInput(
                "",
                SearchQuery.with(0, 10, "", "name", "ASC")
        );

        final var output = assertDoesNotThrow(() -> findAllCategoryUseCase.execute(input));

        assertEquals(expectedTotalElements, output.totalElements());

    }

}
