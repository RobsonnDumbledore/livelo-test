package br.com.codart.domain.discount.factory;

import br.com.codart.domain.discount.Discount;
import br.com.codart.domain.discount.DiscountCreditCard;

import java.math.BigDecimal;

public class DiscountCreditCardFactory  implements DiscountFactory {

    @Override
    public Discount getDiscount(BigDecimal discountPercentage) {
        return new DiscountCreditCard(discountPercentage);
    }
}
