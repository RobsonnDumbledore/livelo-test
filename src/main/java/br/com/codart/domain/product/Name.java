package br.com.codart.domain.product;

import br.com.codart.domain.exceptions.BusinessException;

public class Name {

    private final String value;

    private Name(String value) {
        validateName(value);
        this.value = value;
    }

    public static Name of(String value) {
        return new Name(value);
    }

    private void validateName(String value) {

        if (value == null || value.isEmpty()) {
            throw new BusinessException("the product name cannot be null or empty");
        }
    }

    public String getValue() {
        return value;
    }
}
