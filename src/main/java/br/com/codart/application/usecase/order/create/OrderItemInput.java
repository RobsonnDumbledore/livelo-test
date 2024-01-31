package br.com.codart.application.usecase.order.create;

public record OrderItemInput(
    int quantity,
    String productId,
    double unitPrice
) {
}
