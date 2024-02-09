package br.com.codart.integration.category;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import br.com.codart.domain.exceptions.NotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.category.find.FindCategoryByIdUseCase;
import br.com.codart.application.usecase.category.remove.RemoveCategoryByIdUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class RemoveCategoryByIdUseCaseTest {

    private final FindCategoryByIdUseCase findCategoryByIdUseCase;
    private final RemoveCategoryByIdUseCase removeCategoryByIdUseCase;

    @Autowired
    public RemoveCategoryByIdUseCaseTest(
            FindCategoryByIdUseCase findCategoryByIdUseCase,
            RemoveCategoryByIdUseCase removeCategoryByIdUseCase
    ) {
        this.findCategoryByIdUseCase = findCategoryByIdUseCase;
        this.removeCategoryByIdUseCase = removeCategoryByIdUseCase;
    }


    @Test
    @DisplayName(
            """
                Given categories some previously registered
                When the category removal service for called
                So these categories should be removed
            """)
    public void removeCategoryByIdTest() {

        final var expectedMessageError = "category not found for id: 178c979d-53d2-4b9c-86a4-3529c87c933b";
        final var categoryId = "178c979d-53d2-4b9c-86a4-3529c87c933b";
        final var expectedCategory = findCategoryByIdUseCase.execute(categoryId);

        assertNotNull(expectedCategory);

        assertDoesNotThrow(() -> removeCategoryByIdUseCase.execute(Set.of(categoryId)));

        final var actualMessageError = assertThrows(NotFoundException.class, () -> findCategoryByIdUseCase.execute(categoryId));

        assertEquals(expectedMessageError, actualMessageError.getMessage());

    }

}
