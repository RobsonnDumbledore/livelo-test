package br.com.codart.domain.product;

import br.com.codart.domain.exceptions.BusinessException;
import br.com.codart.domain.utils.NumberToWordsConverter;

public class Price {

    private final double value;

    private Price(double value) {
        this.value = value;
    }

    public static Price of(double value) {
        validateValue(value);
        return new Price(value);
    }

    private static void validateValue(double value) {
        if (value < 0) {
            throw new BusinessException("price cannot be negative");
        }
    }

    public String getPriceInWords() {
        return NumberToWordsConverter.convertCurrencyToWords(value);
    }

    public double getValue() {
        return value;
    }

}
