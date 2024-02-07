package br.com.codart.infrastructure.product.models;

public record FindProductByCategoryResponse(
        String id,
        String name,
        Double price,
        Boolean active
) {
}
