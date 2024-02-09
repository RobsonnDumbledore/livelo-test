package br.com.codart.application.usecase.category.find;

import br.com.codart.domain.category.CategoryID;
import br.com.codart.domain.category.CategoryGateway;
import br.com.codart.domain.exceptions.NotFoundException;

public class DefaultFindCategoryById extends FindCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultFindCategoryById(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public FindCategoryByIdOutput execute(String input) {

        final var category = categoryGateway.findCategoryById(CategoryID.from(input))
                .orElseThrow(() -> new NotFoundException("category not found for id: " + input));

        return new FindCategoryByIdOutput(
                category.getId().getValue(),
                category.getName(),
                category.isActive()
        );
    }
}
