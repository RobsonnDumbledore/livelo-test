package br.com.codart.infrastructure.product.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UpdateProductRequest(

        @NotBlank(message = "id is required")
        String id,

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
