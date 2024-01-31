package br.com.codart.domain.discount;

import java.math.BigDecimal;
import br.com.codart.domain.payment.PaymentType;

public interface DiscountGateway {

   BigDecimal findDiscountByPaymentName(PaymentType paymentType);

}
