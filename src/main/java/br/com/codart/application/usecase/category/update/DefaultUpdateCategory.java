package br.com.codart.application.usecase.category.update;

import br.com.codart.domain.category.CategoryID;
import br.com.codart.domain.category.CategoryGateway;
import br.com.codart.domain.exceptions.NotFoundException;

public class DefaultUpdateCategory extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;
    private final UpdateCategoryInputMapper updateCategoryInputMapper;

    public DefaultUpdateCategory(
            CategoryGateway categoryGateway,
            UpdateCategoryInputMapper updateCategoryInputMapper
    ) {
        this.categoryGateway = categoryGateway;
        this.updateCategoryInputMapper = updateCategoryInputMapper;
    }

    @Override
    public void execute(UpdateCategoryInput input) {

        categoryGateway.findCategoryById(CategoryID.from(input.id()))
                .orElseThrow( () -> new NotFoundException("category not found for id: " + input.id()));

        var productToUpdate = updateCategoryInputMapper.toDomain(input);

        categoryGateway.updateCategory(productToUpdate);

    }
}
