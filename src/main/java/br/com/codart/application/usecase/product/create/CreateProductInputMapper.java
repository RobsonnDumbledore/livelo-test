package br.com.codart.application.usecase.product.create;

import br.com.codart.domain.product.Price;
import br.com.codart.domain.product.Product;
import br.com.codart.domain.product.Name;
import br.com.codart.application.usecase.InputMapper;

public class CreateProductInputMapper implements InputMapper<CreateProductInput, Product> {

    @Override
    public Product toDomain(CreateProductInput input) {
        return Product.newProduct(
                Name.of(input.name()),
                Price.of(input.price())
        );
    }
}

