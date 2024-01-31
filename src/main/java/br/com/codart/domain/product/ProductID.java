package br.com.codart.domain.product;

import java.util.Objects;
import java.util.UUID;

public class ProductID {

    private final String value;

    private ProductID(final String anId) {
        this.value = Objects.requireNonNull(anId);
    }

    public static ProductID unique() {
        return from(UUID.randomUUID().toString());
    }

    public static ProductID from(final String anId) {
        return new ProductID(anId);
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        final ProductID that = (ProductID) aO;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
