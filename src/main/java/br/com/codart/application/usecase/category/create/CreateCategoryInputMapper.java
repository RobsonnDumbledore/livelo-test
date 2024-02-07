package br.com.codart.application.usecase.category.create;

import br.com.codart.domain.category.Category;
import br.com.codart.application.usecase.InputMapper;

public class CreateCategoryInputMapper implements InputMapper<CreateCategoryInput, Category> {

    @Override
    public Category toDomain(CreateCategoryInput input) {
        return Category.newCategory(input.name());
    }
}
