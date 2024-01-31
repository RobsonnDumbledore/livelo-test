package br.com.codart.domain.discount.strategy;

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import br.com.codart.domain.discount.Discount;
import br.com.codart.domain.payment.PaymentType;
import br.com.codart.domain.discount.factory.DiscountFactory;
import br.com.codart.domain.discount.factory.DiscountBankSlipFactory;
import br.com.codart.domain.discount.factory.DiscountCreditCardFactory;
import br.com.codart.domain.discount.factory.DiscountBankTransferFactory;

public class DiscountStrategyRegistry {

    private static final Map<PaymentType, DiscountFactory> registry = new HashMap<>();

    static {
        registry.put(PaymentType.CREDIT_CARD, new DiscountCreditCardFactory());
        registry.put(PaymentType.BANK_SLIP, new DiscountBankSlipFactory());
        registry.put(PaymentType.BANK_TRANSFER, new DiscountBankTransferFactory());
    }

    public static Discount getDiscount(PaymentType paymentType, BigDecimal discountRate) {
        DiscountFactory factory = registry.get(paymentType);

        if (factory != null) {
            return factory.getDiscount(discountRate);
        }
        throw new IllegalArgumentException("No discount strategy found for payment type: " + paymentType);
    }
}
