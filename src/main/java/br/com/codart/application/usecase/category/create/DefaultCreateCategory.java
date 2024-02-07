package br.com.codart.application.usecase.category.create;


import java.util.Set;
import br.com.codart.domain.category.CategoryGateway;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class DefaultCreateCategory extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;
    private final CreateCategoryInputMapper mapper;

    public DefaultCreateCategory(
            CategoryGateway categoryGateway,
            CreateCategoryInputMapper mapper
    ) {
        this.categoryGateway = categoryGateway;
        this.mapper = mapper;
    }

    @Override
    public void execute(Set<CreateCategoryInput> input) {

        validateLimit(input, 10);

        final var categories = mapTo(input, mapper::toDomain);

        categoryGateway.createCategory(categories);

    }
}
