package br.com.codart.infrastructure.product.models;

import java.util.Set;

public record FindProductByIdResponse(
        String id,
        String name,
        Double price,
        Boolean active,
        String brandId,
        Set<String> categories
) {
}
