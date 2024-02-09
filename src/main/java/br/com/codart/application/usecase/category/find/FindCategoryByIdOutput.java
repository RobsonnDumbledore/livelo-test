package br.com.codart.application.usecase.category.find;

public record FindCategoryByIdOutput(
        String id,
        String name,
        Boolean active
) {
}
