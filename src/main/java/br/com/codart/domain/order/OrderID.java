package br.com.codart.domain.order;

import br.com.codart.domain.product.ProductID;

import java.util.Objects;
import java.util.UUID;

public class OrderID {

    private final String value;

    private OrderID(final String anId) {
        this.value = Objects.requireNonNull(anId);
    }

    public static OrderID unique() {
        return from(UUID.randomUUID().toString());
    }

    public static OrderID from(final String anId) {
        return new OrderID(anId);
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        final OrderID that = (OrderID) aO;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
