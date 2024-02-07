package br.com.codart.infrastructure.product.models;

import java.util.List;

public record ChangeProductStatusRequest(
        Boolean active,
        List<String> productIds

) {
}
