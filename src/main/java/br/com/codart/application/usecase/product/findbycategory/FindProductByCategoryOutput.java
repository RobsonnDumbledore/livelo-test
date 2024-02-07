package br.com.codart.application.usecase.product.findbycategory;

public record FindProductByCategoryOutput(
        String id,
        String name,
        Double price,
        Boolean active
) {
}
