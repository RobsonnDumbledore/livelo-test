package br.com.codart.unit.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.exceptions.BusinessException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceTest {

    @Test
    @DisplayName(
            """
                Given I have a valid value for price
                When I call the constructor of Price passing this value as a parameter
                Then it should create an instance of Price with the passed value.
            """
    )
    public void createPrice() {

        Price price = Price.of(15120.45);

        assertEquals(15120.45, price.getValue());
        assertEquals("quinze mil cento e vinte reais e quarenta e cinco centavos", price.getPriceInWords());
    }

    @Test
    @DisplayName(
            """
                Given I have an invalid value for price
                When I call the constructor of Price passing this value as a parameter
                Then it should throw an exception.
            """
    )
    public void validatePrice() {

        final var expectedMessage = "price cannot be negative";
        final var actualMessage = assertThrows(BusinessException.class, () -> Price.of(-50));

        assertEquals(expectedMessage, actualMessage.getMessage());
    }

}
