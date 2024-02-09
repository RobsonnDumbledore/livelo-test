package br.com.codart.integration.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import br.com.codart.infrastructure.category.persistence.CategoryRepository;
import br.com.codart.application.usecase.category.update.UpdateCategoryInput;
import br.com.codart.application.usecase.category.update.UpdateCategoryUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class UpdateCategoryUseCaseTest {

    private final CategoryRepository categoryRepository;
    private final UpdateCategoryUseCase updateCategoryUseCase;


    @Autowired
    public UpdateCategoryUseCaseTest(
            CategoryRepository categoryRepository,
            UpdateCategoryUseCase updateCategoryUseCase
    ) {
        this.categoryRepository = categoryRepository;
        this.updateCategoryUseCase = updateCategoryUseCase;
    }

    @Test
    @DisplayName(
            """
                Given a previously registered category
                When the category update service is called
                So this category must be updated
            """
    )
    public void updateCategoryTest() {

        final var input = new UpdateCategoryInput(
                "178c979d-53d2-4b9c-86a4-3529c87c933b",
                "Electronics updated",
                true
        );

        assertDoesNotThrow(() -> updateCategoryUseCase.execute(input));

        categoryRepository.findById("178c979d-53d2-4b9c-86a4-3529c87c933b").ifPresent(category -> {
            Assertions.assertEquals("Electronics updated", category.getName());
            Assertions.assertTrue(category.isActive());
        });

    }

}
