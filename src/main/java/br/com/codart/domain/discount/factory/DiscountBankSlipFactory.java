package br.com.codart.domain.discount.factory;

import java.math.BigDecimal;
import br.com.codart.domain.discount.Discount;
import br.com.codart.domain.discount.DiscountBankSlip;

public class DiscountBankSlipFactory implements DiscountFactory {
    @Override
    public Discount getDiscount(BigDecimal discountPercentage) {
        return new DiscountBankSlip(discountPercentage);
    }
}
