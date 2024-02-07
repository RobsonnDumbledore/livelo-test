package br.com.codart.application.usecase.product.update;

import br.com.codart.domain.category.CategoryID;
import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.exceptions.NotFoundException;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class DefaultUpdateProduct extends UpdateProductUseCase {

    private final ProductGateway productGateway;
    private final UpdateProductInputMapper updateProductInputMapper;

    public DefaultUpdateProduct(
            ProductGateway productGateway,
            UpdateProductInputMapper updateProductInputMapper
    ) {
        this.productGateway = productGateway;
        this.updateProductInputMapper = updateProductInputMapper;
    }

    @Override
    public void execute(UpdateProductInput input) {

        productGateway.findProductById(input.id())
                .orElseThrow( () -> new NotFoundException("product not found for id: " + input.id()));

        var productToUpdate = updateProductInputMapper.toDomain(input);

        productToUpdate.removeCategories();
        productToUpdate.addCategories(mapTo(input.categoryIds(), CategoryID::from));

        productGateway.updateProduct(productToUpdate);
    }
}
