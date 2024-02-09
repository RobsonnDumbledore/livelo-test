package br.com.codart.infrastructure.category.models;

public record UpdateCategoryRequest(
        String id,
        String name,
        Boolean active
) {
}
