package br.com.codart.domain.category;

import java.util.UUID;
import java.util.Objects;

public class CategoryID {

    private final String value;

    private CategoryID(final String anId) {
        this.value = Objects.requireNonNull(anId);
    }

    public static CategoryID unique() {
        return from(UUID.randomUUID().toString());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        final CategoryID that = (CategoryID) aO;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
