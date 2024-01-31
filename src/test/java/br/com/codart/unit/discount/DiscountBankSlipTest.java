package br.com.codart.unit.discount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.discount.DiscountBankSlip;

import java.math.BigDecimal;

public class DiscountBankSlipTest {

    @Test
    @DisplayName(
            """
                Given I have an original price of 15.99
                And a discount percentage of 3%
                When I apply this discount to the original price
                Then the final price should be calculated with a 3% reduction
                And the final price should be rounded to two decimal places
            """
    )
    public void testDiscountBankSlip() {

        final var expectedValue = 15.51;

        var discount = new DiscountBankSlip(BigDecimal.valueOf(3));
        var actualValue = discount.applyDiscount(Price.of(15.99)).getValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

}
