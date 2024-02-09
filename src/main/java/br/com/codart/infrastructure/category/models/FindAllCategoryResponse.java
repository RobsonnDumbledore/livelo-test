package br.com.codart.infrastructure.category.models;

public record FindAllCategoryResponse(
        String id,
        String name,
        Boolean active
) {
}
