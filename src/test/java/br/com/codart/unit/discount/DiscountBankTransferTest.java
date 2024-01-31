package br.com.codart.unit.discount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.discount.DiscountBankTransfer;

import java.math.BigDecimal;

public class DiscountBankTransferTest {

    @Test
    @DisplayName(
            """
                Given I have an original price of 1248.75
                And a discount percentage of 18%
                When I apply this discount to the original price
                Then the final price should be calculated with a 18% reduction
                And the final price should be rounded to two decimal places
            """
    )
    public void testDiscountBankSlip() {

        final var expectedValue = 1023.98;

        var discount = new DiscountBankTransfer(BigDecimal.valueOf(18));
        var actualValue = discount.applyDiscount(Price.of(1248.75)).getValue();

        Assertions.assertEquals(expectedValue, actualValue);
    }

}
