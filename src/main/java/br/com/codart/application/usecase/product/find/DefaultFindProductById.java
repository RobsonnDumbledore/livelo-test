package br.com.codart.application.usecase.product.find;

import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.exceptions.NotFoundException;

public class DefaultFindProductById extends FindProductByIdUseCase {

    private final ProductGateway productGateway;
    private final FindProductByIdOutputMapper mapper;

    public DefaultFindProductById(ProductGateway productGateway, FindProductByIdOutputMapper mapper) {
        this.productGateway = productGateway;
        this.mapper = mapper;
    }

    @Override
    public FindProductByIdOutput execute(String input) {

        final var product = productGateway.findProductById(input)
                .orElseThrow( () -> new NotFoundException("product not found for id: " + input));

        return mapper.toOutput(product);

    }
}
