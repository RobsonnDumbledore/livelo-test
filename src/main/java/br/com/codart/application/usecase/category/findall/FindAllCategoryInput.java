package br.com.codart.application.usecase.category.findall;

import br.com.codart.domain.utils.SearchQuery;

public record FindAllCategoryInput(
        String categoryName,
        SearchQuery searchQuery
) {
}
