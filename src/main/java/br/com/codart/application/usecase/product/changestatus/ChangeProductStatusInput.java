package br.com.codart.application.usecase.product.changestatus;

import java.util.List;

public record ChangeProductStatusInput(
        Boolean active,
        List<String> productIds
) {
}
