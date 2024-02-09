package br.com.codart.integration.category;

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
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.application.usecase.category.find.FindCategoryByIdUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class FindCategoryByIdUseCaseTest {

    private final FindCategoryByIdUseCase findCategoryByIdUseCase;

    @Autowired
    public FindCategoryByIdUseCaseTest(FindCategoryByIdUseCase findCategoryByIdUseCase) {
        this.findCategoryByIdUseCase = findCategoryByIdUseCase;
    }

    @Test
    @DisplayName(
            """
                Given a previously created category
                When the id lookup use case is called
                So this category is successfully recovered
            """
    )
    public void findCategoryByIdTest() {

        final var categoryId = "178c979d-53d2-4b9c-86a4-3529c87c933b";

        final var actualCategory = assertDoesNotThrow(() -> findCategoryByIdUseCase.execute(categoryId));

        assertEquals(actualCategory.active(), true);
        assertEquals(actualCategory.id(), categoryId);
        assertEquals(actualCategory.name(), "Electronics");

    }

    @Test
    @DisplayName(
            """
                Given no category exists with the specified ID
                When the id lookup use case is called with that ID
                Then a "CategoryNotFoundException" should be thrown indicating the category was not found
            """
    )
    public void validateFindCategoryByIdTest() {

        final var categoryId = "178c979d-53d2-4b9c-86a4-3529c87c933d";
        final var expectedMessageError = "category not found for id: ".concat(categoryId);

        final var actualMessageError = assertThrows(NotFoundException.class, () -> findCategoryByIdUseCase.execute(categoryId));

        assertEquals(expectedMessageError, actualMessageError.getMessage());

    }
}
