package br.com.codart.application.usecase.product.create;

import java.util.List;
import br.com.codart.domain.product.ProductGateway;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class DefaultCreateProduct extends CreateProductUseCase {

    private final ProductGateway productGateway;
    private final CreateProductInputMapper createProductInputMapper;

    public DefaultCreateProduct(
            ProductGateway productGateway,
            CreateProductInputMapper createProductInputMapper
    ) {
        this.productGateway = productGateway;
        this.createProductInputMapper = createProductInputMapper;
    }

    @Override
    public List<String> execute(List<CreateProductInput> input) {

        validateLimit(input, 10);
        final var products = mapTo(input, createProductInputMapper::toDomain);

        return mapTo(productGateway.createProducts(products), product -> product.getId().getValue());
    }
}

