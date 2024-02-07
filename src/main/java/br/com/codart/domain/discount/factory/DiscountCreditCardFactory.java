package br.com.codart.domain.discount.factory;

import java.math.BigDecimal;
import br.com.codart.domain.discount.Discount;
import br.com.codart.domain.discount.DiscountCreditCard;

public class DiscountCreditCardFactory implements DiscountFactory {

    @Override
    public Discount getDiscount(BigDecimal discountPercentage) {
        return new DiscountCreditCard(discountPercentage);
    }
}
