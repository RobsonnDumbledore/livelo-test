package br.com.codart.infrastructure.configuration.usecase;

import org.springframework.context.annotation.Bean;
import br.com.codart.domain.discount.DiscountGateway;
import org.springframework.context.annotation.Configuration;
import br.com.codart.application.usecase.discount.find.FindDiscountByPaymentName;
import br.com.codart.application.usecase.discount.find.FindDiscountByPaymentNameUseCase;

@Configuration
public class DiscountUseCaseConfig {

    private final DiscountGateway discountGateway;

    public DiscountUseCaseConfig(DiscountGateway discountGateway) {
        this.discountGateway = discountGateway;
    }

    @Bean
    public FindDiscountByPaymentNameUseCase findDiscountByPaymentNameUseCase() {
        return new FindDiscountByPaymentName(discountGateway);
    }
}
