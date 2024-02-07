package br.com.codart.application.usecase.product.remove;

import java.util.List;

public record RemoveProductByIdOutput(List<String> found, List<String> notFound) {

    public static RemoveProductByIdOutput found(List<String> found) {
        return new RemoveProductByIdOutput(found, List.of());
    }

    public static RemoveProductByIdOutput notFound(List<String> notFound) {
        return new RemoveProductByIdOutput(List.of(), notFound);
    }

    public static RemoveProductByIdOutput of(List<String> found, List<String> notFound) {
        return new RemoveProductByIdOutput(found, notFound);
    }
}

