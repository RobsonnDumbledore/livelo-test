package br.com.codart.infrastructure.category.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.codart.application.usecase.category.update.UpdateCategoryInput;
import br.com.codart.infrastructure.category.models.CreateCategoryRequest;
import br.com.codart.application.usecase.category.create.CreateCategoryInput;
import br.com.codart.infrastructure.category.models.CreateCategoryRequestSet;
import br.com.codart.infrastructure.category.models.UpdateCategoryRequest;

public interface CategoryMapper {

    static CreateCategoryInput toUseCase(CreateCategoryRequest createCategoryRequest) {

        return new CreateCategoryInput(
                createCategoryRequest.name()
        );
    }

    static Set<CreateCategoryInput> toUseCase(CreateCategoryRequestSet createCategoryRequestSet) {

        return createCategoryRequestSet.categories().stream()
                .map(CategoryMapper::toUseCase)
                .collect(Collectors.toSet());

    }

    static UpdateCategoryInput toUseCase(UpdateCategoryRequest updateCategoryRequest) {

        return new UpdateCategoryInput(
                updateCategoryRequest.id(),
                updateCategoryRequest.name(),
                updateCategoryRequest.active()
        );
    }

}
