package br.com.codart.application.usecase.product.findall;

import br.com.codart.domain.utils.SearchQuery;

public record FindAllProductInput(
        String productName,
        SearchQuery searchQuery
) {
}
