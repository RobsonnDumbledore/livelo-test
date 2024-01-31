package br.com.codart.application.usecase.product.find;

public record FindProductByIdOutput(
        String id,
        String name,
        Double price
) {
}
