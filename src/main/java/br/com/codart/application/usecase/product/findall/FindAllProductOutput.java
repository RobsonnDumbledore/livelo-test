package br.com.codart.application.usecase.product.findall;

public record FindAllProductOutput(
        String id,
        String name,
        Double price,
        Boolean active
) {
}
