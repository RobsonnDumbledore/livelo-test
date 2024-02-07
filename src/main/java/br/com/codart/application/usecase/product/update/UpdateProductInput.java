package br.com.codart.application.usecase.product.update;

import java.util.Set;

public record UpdateProductInput(
        String id,
        String name,
        Double price,
        Boolean active,
        String brandId,
        Set<String> categoryIds
) {

    public static UpdateProductInput of(
            String id,
            String name,
            Double price,
            Boolean active,
            String brandId,
            Set<String> categoryIds) {

        return new UpdateProductInput(
                id,
                name,
                price,
                active,
                brandId,
                categoryIds
        );

    }

}
