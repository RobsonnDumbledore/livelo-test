package br.com.codart.domain.brand;

import java.util.UUID;
import java.util.Objects;

public class BrandID {

    private final String value;

    private BrandID(final String anId) {
        this.value = Objects.requireNonNull(anId);
    }

    public static BrandID unique() {
        return from(UUID.randomUUID().toString());
    }

    public static BrandID from(final String anId) {
        return new BrandID(anId);
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        final BrandID that = (BrandID) aO;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
