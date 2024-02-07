package br.com.codart.infrastructure.product.models;

import java.util.Set;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest(

        @NotBlank(message = "name is required")
        String name,

        @NotNull(message = "price is required")
        Double price,

        Boolean active,

        @NotNull(message = "brandId is required")
        String brandId,

        Set<String> categoryIds
) {
}
