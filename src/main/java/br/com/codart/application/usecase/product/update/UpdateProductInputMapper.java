package br.com.codart.application.usecase.product.update;

import br.com.codart.domain.product.Name;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.product.Product;
import br.com.codart.domain.product.ProductID;
import br.com.codart.application.usecase.InputMapper;

public class UpdateProductInputMapper implements InputMapper<UpdateProductInput, Product> {


    @Override
    public Product toDomain(UpdateProductInput input) {
        return Product.with(
                ProductID.from(input.id()),
                Name.of(input.name()),
                Price.of(input.price()),
                input.active(),
                BrandID.from(input.brandId()),
                null
        );
    }
}
