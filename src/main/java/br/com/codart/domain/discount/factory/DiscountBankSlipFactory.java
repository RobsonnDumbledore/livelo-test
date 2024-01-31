package br.com.codart.domain.discount.factory;

import br.com.codart.domain.discount.Discount;
import br.com.codart.domain.discount.DiscountBankSlip;

import java.math.BigDecimal;

public class DiscountBankSlipFactory implements DiscountFactory {
    @Override
    public Discount getDiscount(BigDecimal discountPercentage) {
        return new DiscountBankSlip(discountPercentage);
    }
}
