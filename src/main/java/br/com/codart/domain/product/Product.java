package br.com.codart.domain.product;

import br.com.codart.domain.exceptions.BusinessException;

public class Product {

    private final ProductID id;
    private final Name name;
    private final Price price;

    private Product(
            ProductID id,
            Name name,
            Price price
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        validateProduct();
    }

    public static Product newProduct(
            final Name name,
            final Price price
    ) {

        final var id = ProductID.unique();

        return new Product(
                id,
                name,
                price
        );
    }

    public static Product with(
            final ProductID id,
            final Name name,
            final Price price
    ) {
        return new Product(
                id,
                name,
                price
        );
    }

    private void validateProduct() {
        if(this.name == null || this.price == null) {
            throw new BusinessException("the product name and price cannot be null");
        }
    }

    public ProductID getId() {
        return id;
    }

    public Name getProductName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

}
