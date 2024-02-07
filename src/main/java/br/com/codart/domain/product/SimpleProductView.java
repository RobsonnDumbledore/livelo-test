package br.com.codart.domain.product;

public record SimpleProductView(
        String id,
        String name,
        Double price,
        Boolean active,
        String brandId
) {

}
