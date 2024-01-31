package br.com.codart.application.usecase.product.create;

import java.util.List;
import br.com.codart.domain.product.ProductGateway;

public class DefaultCreateProduct extends CreateProductUseCase {

    private final ProductGateway productGateway;
    private final CreateProductInputMapper createProductInputMapper;

    public DefaultCreateProduct(ProductGateway productGateway, CreateProductInputMapper createProductInputMapper) {
        this.productGateway = productGateway;
        this.createProductInputMapper = createProductInputMapper;
    }

    @Override
    public List<String> execute(List<CreateProductInput> input) {

        final var products = input.stream()
                .map(createProductInputMapper::toDomain)
                .toList();

        return productGateway.createProducts(products)
                .stream()
                .map(product -> product.getId().getValue())
                .toList();
    }
}

