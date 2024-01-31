package br.com.codart.application.usecase.order.create;

import java.util.List;

public record OrderInput(
        List<OrderItemInput> orderItems,
        String paymentType
) {}
