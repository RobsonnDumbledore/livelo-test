package br.com.codart.application.usecase.product.findbycategory;

import br.com.codart.domain.utils.SearchQuery;

public record FindProductByCategoryInput(
        String category,
        SearchQuery searchQuery
) {
}
