package br.com.codart.application.usecase.product.findbybrand;

import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.product.ProductGateway;

public class DefaultFindProductByBrand extends FindProductByBrandUseCase {

    private final ProductGateway productGateway;
    private final FindProductByBrandOutputMapper mapper;

    public DefaultFindProductByBrand(
            ProductGateway productGateway,
            FindProductByBrandOutputMapper mapper
    ) {
        this.mapper = mapper;
        this.productGateway = productGateway;
    }

    @Override
    public Pagination<FindProductByBrandOutput> execute(FindProductByBrandInput input) {

        return productGateway.findProductByBrand(
                input.brand(),
                input.searchQuery()
        ).map(mapper::toOutput);
    }
}
