package br.com.codart.application.usecase.category.update;

import br.com.codart.application.usecase.InputMapper;
import br.com.codart.domain.category.Category;
import br.com.codart.domain.category.CategoryID;

public class UpdateCategoryInputMapper implements InputMapper<UpdateCategoryInput, Category> {

    @Override
    public Category toDomain(UpdateCategoryInput input) {
        return Category.with(
                CategoryID.from(input.id()),
                input.name(),
                input.active()
        );
    }
}
