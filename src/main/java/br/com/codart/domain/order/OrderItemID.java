package br.com.codart.domain.order;

import br.com.codart.domain.product.ProductID;

import java.util.Objects;
import java.util.UUID;

public class OrderItemID {

    private final String value;

    private OrderItemID(final String anId) {
        this.value = Objects.requireNonNull(anId);
    }

    public static OrderItemID unique() {
        return from(UUID.randomUUID().toString());
    }

    public static OrderItemID from(final String anId) {
        return new OrderItemID(anId);
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        final OrderItemID that = (OrderItemID) aO;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
