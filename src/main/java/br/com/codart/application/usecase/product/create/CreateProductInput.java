package br.com.codart.application.usecase.product.create;

public record CreateProductInput(
        String name,
        Double price
) {
}
