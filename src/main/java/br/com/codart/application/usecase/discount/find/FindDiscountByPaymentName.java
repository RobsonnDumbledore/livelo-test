package br.com.codart.application.usecase.discount.find;

import java.math.BigDecimal;
import br.com.codart.domain.payment.PaymentType;
import br.com.codart.domain.discount.DiscountGateway;

public class FindDiscountByPaymentName extends FindDiscountByPaymentNameUseCase {

    private final DiscountGateway discountGateway;

    public FindDiscountByPaymentName(DiscountGateway discountGateway) {
        this.discountGateway = discountGateway;
    }


    @Override
    public BigDecimal execute(String input) {
        return discountGateway.findDiscountByPaymentName(PaymentType.valueOf(input));
    }
}
