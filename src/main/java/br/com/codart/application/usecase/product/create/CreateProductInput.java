package br.com.codart.application.usecase.product.create;

import java.util.Set;

public record CreateProductInput(
        String name,
        Double price,
        String brandId,
        Set<String> categoryIds
) {
    public static CreateProductInput of(
            String name,
            Double price,
            String brandId
    ) {
        return new CreateProductInput(
                name,
                price,
                brandId,
                Set.of()
        );
    }

    public static CreateProductInput of(
            String name,
            Double price,
            String brandId,
            Set<String> categoryIds
    ) {
        return new CreateProductInput(
                name,
                price,
                brandId,
                categoryIds
        );
    }

}
