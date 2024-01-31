package br.com.codart.unit.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.product.Name;
import br.com.codart.domain.exceptions.BusinessException;

public class NameTest {

    @Test
    @DisplayName(
            """
                Given I have a product name valid
                When I try to create a new Name using these details
                Then the Name product creation should success
            """
    )
    public void createNameSuccess() {


        final var actualName = Assertions.assertDoesNotThrow(
                () -> Name.of("Eco-friendly Water Bottle")
        );

        Assertions.assertEquals("Eco-friendly Water Bottle", actualName.getValue());
    }

    @Test
    @DisplayName(
            """
                Given I have a product name set to empty
                When I try to create a new Name using these details
                Then the Name product creation should fail
                And an exception indicating "the product name cannot be null or empty" should be thrown
            """
    )
    public void validateProductNameEmpty() {

        final var expectedMessageError = "the product name cannot be null or empty";

        final var actualMessageError = Assertions.assertThrows(
                BusinessException.class,
                () -> Name.of("")
        );

        Assertions.assertEquals(expectedMessageError, actualMessageError.getMessage());
    }

    @Test
    @DisplayName(
            """
                Given I have a product name set to null and a valid price 20.00
                When I try to create a new product using these details
                Then the product creation should fail
                And an exception indicating "the product name cannot be null or empty" should be thrown
            """
    )
    public void validateProductNameNull() {

        final var expectedMessageError = "the product name cannot be null or empty";

        final var actualMessageError = Assertions.assertThrows(
                BusinessException.class,
                () -> Name.of(null)
        );

        Assertions.assertEquals(expectedMessageError, actualMessageError.getMessage());
    }

}
