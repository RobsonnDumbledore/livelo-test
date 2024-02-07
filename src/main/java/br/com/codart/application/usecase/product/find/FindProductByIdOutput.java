package br.com.codart.application.usecase.product.find;

import java.util.Set;

public record FindProductByIdOutput(
        String id,
        String name,
        Double price,
        Boolean active,
        String brandId,
        Set<String> categories
) {
}

