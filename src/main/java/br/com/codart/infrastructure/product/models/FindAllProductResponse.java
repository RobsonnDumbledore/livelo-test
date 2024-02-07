package br.com.codart.infrastructure.product.models;

public record FindAllProductResponse(
        String id,
        String name,
        Double price,
        Boolean active
) {
}
