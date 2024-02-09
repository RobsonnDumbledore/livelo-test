package br.com.codart.application.usecase.category.findall;

import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.category.CategoryGateway;

public class DefaultFindAllCategory extends FindAllCategoryUseCase {

    private final CategoryGateway categoryGateway;
    private final FindAllCategoryOutputMapper mapper;

    public DefaultFindAllCategory(
            CategoryGateway categoryGateway,
            FindAllCategoryOutputMapper mapper
    ) {
        this.mapper = mapper;
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Pagination<FindAllCategoryOutput> execute(FindAllCategoryInput input) {
        return categoryGateway.findAllCategories(
                input.categoryName(),
                input.searchQuery()
        ).map(mapper::toOutput);
    }
}
