package br.com.codart.domain.discount.factory;

import java.math.BigDecimal;
import br.com.codart.domain.discount.Discount;

public interface DiscountFactory {
    Discount getDiscount(BigDecimal discountPercentage);
}
