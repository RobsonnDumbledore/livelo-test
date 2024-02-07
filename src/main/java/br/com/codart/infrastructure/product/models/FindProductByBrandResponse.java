package br.com.codart.infrastructure.product.models;

public record FindProductByBrandResponse(
        String id,
        String name,
        Double price,
        Boolean active
) {
}
