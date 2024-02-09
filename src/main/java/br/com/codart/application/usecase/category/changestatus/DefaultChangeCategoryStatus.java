package br.com.codart.application.usecase.category.changestatus;

import br.com.codart.domain.category.Category;
import br.com.codart.domain.category.CategoryID;
import br.com.codart.domain.category.CategoryGateway;
import br.com.codart.domain.exceptions.NotFoundException;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class DefaultChangeCategoryStatus extends ChangeCategoryStatusUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultChangeCategoryStatus(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(ChangeCategoryStatusInput input) {

        validateLimit(input.categoryIds(), 10);

        final var categoryIds = mapTo(input.categoryIds(), CategoryID::from);
        final var foundCategories = categoryGateway.findAllCategoryById(categoryIds);

        if (foundCategories.isEmpty()) {
            throw new NotFoundException("No categories found");
        }

        final var updatedCategories = foundCategories.stream()
                .map(Category::getId)
                .toList();

        categoryGateway.changeCategoryStatus(input.active(), updatedCategories);

    }
}
