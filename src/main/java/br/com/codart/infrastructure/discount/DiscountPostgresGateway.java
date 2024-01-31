package br.com.codart.infrastructure.discount;

import java.math.BigDecimal;
import br.com.codart.domain.payment.PaymentType;
import org.springframework.stereotype.Component;
import br.com.codart.domain.discount.DiscountGateway;
import br.com.codart.domain.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.infrastructure.discount.persistence.DiscountRepository;

@Component
public class DiscountPostgresGateway implements DiscountGateway {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountPostgresGateway(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public BigDecimal findDiscountByPaymentName(PaymentType paymentType) {
        return discountRepository.findDiscountByPaymentName(paymentType.name())
                .orElseThrow(() -> new BusinessException("No discount found for payment type: " + paymentType));
    }
}
