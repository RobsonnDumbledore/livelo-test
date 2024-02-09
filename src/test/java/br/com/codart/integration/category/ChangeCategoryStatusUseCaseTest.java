package br.com.codart.integration.category;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.infrastructure.category.persistence.CategoryRepository;
import br.com.codart.application.usecase.category.changestatus.ChangeCategoryStatusInput;
import br.com.codart.application.usecase.category.changestatus.ChangeCategoryStatusUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class ChangeCategoryStatusUseCaseTest {

    private final CategoryRepository categoryRepository;
    private final ChangeCategoryStatusUseCase changeCategoryStatusUseCase;


    @Autowired
    public ChangeCategoryStatusUseCaseTest(
            CategoryRepository categoryRepository,
            ChangeCategoryStatusUseCase changeCategoryStatusUseCase
    ) {
        this.categoryRepository = categoryRepository;
        this.changeCategoryStatusUseCase = changeCategoryStatusUseCase;
    }


    @Test
    @DisplayName(
            """
                Given a category with active status
                When the status change service is called
                So this category has its status change
            """
    )
    public void changeCategoryStatusTest() {

        final var input = new ChangeCategoryStatusInput(false, List.of("178c979d-53d2-4b9c-86a4-3529c87c933b"));

        categoryRepository.findById("178c979d-53d2-4b9c-86a4-3529c87c933b").ifPresent(
                category1 -> Assertions.assertTrue(category1.isActive())
        );

        changeCategoryStatusUseCase.execute(input);

        categoryRepository.findById("178c979d-53d2-4b9c-86a4-3529c87c933b").ifPresent(
                category1 -> Assertions.assertFalse(category1.isActive())
        );

    }

}
