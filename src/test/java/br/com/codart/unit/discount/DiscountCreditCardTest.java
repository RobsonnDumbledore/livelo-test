package br.com.codart.unit.discount;

import br.com.codart.domain.discount.DiscountCreditCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.product.Price;

import java.math.BigDecimal;

public class DiscountCreditCardTest {

    @Test
    @DisplayName(
            """
                Given I have an original price of 12.85
                And a discount percentage of 30%
                When I apply this discount to the original price
                Then the final price should be calculated with a 30% reduction
                And the final price should be rounded to two decimal places
            """
    )
    public void testDiscountBankSlip() {

        final var expectedValue = 9.0;

        var discount = new DiscountCreditCard(BigDecimal.valueOf(30));
        var actualValue = discount.applyDiscount(Price.of(12.85)).getValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

}
