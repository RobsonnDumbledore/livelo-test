package br.com.codart.infrastructure.category.models;

public record FindCategoryByIdResponse(
        String id,
        String name,
        Boolean active
) {
}
