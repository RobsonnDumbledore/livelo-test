package br.com.codart.application.usecase.product.changestatus;

import java.util.List;

public record ChangeProductStatusOutput(List<String> notExecuted, List<String> notFound) {

    public static ChangeProductStatusOutput of(List<String> notExecuted, List<String> notFound) {
        return new ChangeProductStatusOutput(notExecuted, notFound);
    }
}
