package br.com.codart.application.usecase.product.create;

import br.com.codart.domain.product.Name;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.product.Product;
import br.com.codart.domain.category.CategoryID;
import br.com.codart.application.usecase.InputMapper;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class CreateProductInputMapper implements InputMapper<CreateProductInput, Product> {

    @Override
    public Product toDomain(CreateProductInput input) {
        return Product.newProduct(
                Name.of(input.name()),
                Price.of(input.price()),
                BrandID.from(input.brandId()),
                mapTo(input.categoryIds(), CategoryID::from)
        );
    }
}

