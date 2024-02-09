package br.com.codart.application.usecase.category.remove;

import java.util.Set;
import br.com.codart.domain.category.CategoryID;
import br.com.codart.domain.category.CategoryGateway;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class DefaultRemoveCategoryById extends RemoveCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultRemoveCategoryById(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(Set<String> input) {

        validateLimit(input, 10);

        final var categoryIds = mapTo(input, CategoryID::from);

        categoryGateway.deleteCategory(categoryIds);
    }
}
