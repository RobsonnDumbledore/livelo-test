package br.com.codart.application.usecase.product.findall;

import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.utils.Pagination;

public class DefaultFindAllProduct extends FindAllProductUseCase {

    private final ProductGateway productGateway;
    private final FindAllProductOutputMapper mapper;

    public DefaultFindAllProduct(
            ProductGateway productGateway,
            FindAllProductOutputMapper mapper
    ) {
        this.mapper = mapper;
        this.productGateway = productGateway;
    }

    @Override
    public Pagination<FindAllProductOutput> execute(FindAllProductInput input) {
        return productGateway.findAllProducts(
                input.productName(),
                input.searchQuery()
        ).map(mapper::toOutput);
    }
}
