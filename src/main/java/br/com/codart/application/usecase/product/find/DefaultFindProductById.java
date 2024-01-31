package br.com.codart.application.usecase.product.find;

import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.exceptions.NotFoundException;

public class DefaultFindProductById extends FindProductByIdUseCase {

    private final ProductGateway productGateway;

    public DefaultFindProductById(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public FindProductByIdOutput execute(String input) {

        final var product = productGateway.findProductById(input)
                .orElseThrow( () -> new NotFoundException("product not found for id: " + input));

        return new FindProductByIdOutput(
                product.getId().getValue(),
                product.getProductName().getValue(),
                product.getPrice().getValue()
        );

    }
}
