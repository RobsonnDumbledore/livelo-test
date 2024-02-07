package br.com.codart.application.usecase.product.findbybrand;

public record FindProductByBrandOutput(
        String id,
        String name,
        Double price,
        Boolean active
) {
}
