package br.com.codart.infrastructure.product.models;

import java.util.List;

public record RemoveProductByIdResponse(
        List<String> found,
        List<String> notFound
) {

    public static RemoveProductByIdResponse of(List<String> found, List<String> notFound) {
        return new RemoveProductByIdResponse(found, notFound);
    }
}
