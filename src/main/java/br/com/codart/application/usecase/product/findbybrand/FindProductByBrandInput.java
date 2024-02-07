package br.com.codart.application.usecase.product.findbybrand;

import br.com.codart.domain.utils.SearchQuery;

public record FindProductByBrandInput(
        String brand,
        SearchQuery searchQuery
) {
}
