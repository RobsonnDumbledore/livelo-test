package br.com.codart.unit.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import br.com.codart.domain.product.Name;
import org.junit.jupiter.api.DisplayName;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.product.Product;
import br.com.codart.domain.exceptions.BusinessException;

public class ProductTest {

    @Test
    @DisplayName(
            """
                Given I have valid product name "Eco-friendly Water Bottle" and price 15.99
                When I create a new product using these details
                Then the product should be successfully created with the given name and price
                And a unique product ID should be generated for the product
            """
    )
    public void createProduct() {

        Product product = Product.newProduct(
                Name.of("Eco-friendly Water Bottle"),
                Price.of(15.99)
        );

        Assertions.assertEquals("Eco-friendly Water Bottle", product.getProductName().getValue());
        Assertions.assertEquals(15.99, product.getPrice().getValue());

    }

    @Test
    @DisplayName(
            """
                Given I am ready to create a new product
                When I attempt to create a product with a null name and a valid price (e.g., 20.00)
                Then the product creation should fail
                And a BusinessException with the message "the product name and price cannot be null" should be thrown
            """
    )
    public void validateProductNameNull() {

        final var expectedMessageError = "the product name and price cannot be null";

       final var actualMessageError = Assertions.assertThrows(BusinessException.class, () -> Product.newProduct(
                null,
                Price.of(15.99))
       );

       Assertions.assertEquals(expectedMessageError, actualMessageError.getMessage());

    }

    @Test
    @DisplayName(
            """
                Given I am ready to create a new product
                When I attempt to create a product with a valid name (e.g., "Eco-friendly Water Bottle") and a null price
                Then the product creation should fail
                And a BusinessException with the message "Product price cannot be null" should be thrown
            """
    )
    public void validateProductPriceNull() {

        final var expectedMessageError = "the product name and price cannot be null";

        final var actualMessageError = Assertions.assertThrows(BusinessException.class, () -> Product.newProduct(
                Name.of("Eco-friendly Water Bottle"),
                null)
        );

        Assertions.assertEquals(expectedMessageError, actualMessageError.getMessage());

    }

}
