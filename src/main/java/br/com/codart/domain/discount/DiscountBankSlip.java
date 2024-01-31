package br.com.codart.domain.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import br.com.codart.domain.product.Price;

public class DiscountBankSlip extends Discount {

    private final BigDecimal discountPercentage;

    public DiscountBankSlip(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage.divide(BigDecimal.valueOf(100));
    }

    @Override
    public Price applyDiscount(Price originalPrice) {
        BigDecimal original = BigDecimal.valueOf(originalPrice.getValue());
        BigDecimal discountAmount = original.multiply(discountPercentage);
        BigDecimal discountedPrice = original.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

        return Price.of(discountedPrice.doubleValue());
    }
}

