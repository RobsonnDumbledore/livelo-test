package br.com.codart.application.usecase.category.update;

public record UpdateCategoryInput(
        String id,
        String name,
        Boolean active
) {
}
