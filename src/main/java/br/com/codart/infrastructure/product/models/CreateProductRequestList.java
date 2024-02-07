package br.com.codart.infrastructure.product.models;

import java.util.List;
import jakarta.validation.Valid;

public record CreateProductRequestList(
        @Valid
        List<CreateProductRequest> products
) {

}
