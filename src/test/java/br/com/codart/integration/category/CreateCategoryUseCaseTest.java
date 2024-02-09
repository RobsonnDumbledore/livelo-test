package br.com.codart.integration.category;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.PostgresContainerConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.codart.domain.exceptions.LimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.infrastructure.category.persistence.CategoryRepository;
import br.com.codart.application.usecase.category.create.CreateCategoryInput;
import br.com.codart.application.usecase.category.create.CreateCategoryUseCase;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Import(PostgresContainerConfig.class)
public class CreateCategoryUseCaseTest {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CreateCategoryUseCaseTest(
            CreateCategoryUseCase createCategoryUseCase,
            CategoryRepository categoryRepository
    ) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.categoryRepository = categoryRepository;
    }

    @Test
    @DisplayName(
            """
                Given that I have the necessary data to create a product
                When the product creation service is invoked with this data
                Then a new product must be created successfully
            """
    )
    public void createCategoryTest() {

        final var categories = Set.of(
                new CreateCategoryInput("Electronics"),
                new CreateCategoryInput("Fashion"),
                new CreateCategoryInput("Beauty"),
                new CreateCategoryInput("Home"),
                new CreateCategoryInput("Sports"),
                new CreateCategoryInput("Toys")
        );

        Assertions.assertDoesNotThrow(() -> createCategoryUseCase.execute(categories));

        final var actualCategories = categoryRepository.count();

        Assertions.assertEquals(9, actualCategories);

    }

    @Test
    @DisplayName(
            """
                Given the user has prepared a list of categories to create
                When the user attempts to create more than 10 categories in a single operation
                Then the system should not create any of the categories
                And the system should throw a "LimitExceededException"
            """
    )
    public void validateCategoryTest() {

        final var expectedMessageError = "Limit of 10 items per operation exceeded.";

        final var categories = Set.of(
                new CreateCategoryInput("Electronics"),
                new CreateCategoryInput("Fashion"),
                new CreateCategoryInput("Beauty"),
                new CreateCategoryInput("Home"),
                new CreateCategoryInput("Sports"),
                new CreateCategoryInput("Toys"),
                new CreateCategoryInput("Game"),
                new CreateCategoryInput("Books"),
                new CreateCategoryInput("Mathematics"),
                new CreateCategoryInput("Chemical"),
                new CreateCategoryInput("Tools")

        );

        final var actualMessageError = Assertions.assertThrows(
                LimitExceededException.class,
                () -> createCategoryUseCase.execute(categories)
        );

        Assertions.assertEquals(expectedMessageError, actualMessageError.getMessage());
    }

}
