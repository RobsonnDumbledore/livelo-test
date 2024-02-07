package br.com.codart.application.usecase.product.findbycategory;

import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.product.ProductGateway;

public class DefaultFindProductByCategory extends FindProductByCategoryUseCase {

    private final ProductGateway productGateway;
    private final FindProductByCategoryOutputMapper mapper;

    public DefaultFindProductByCategory(
            ProductGateway productGateway,
            FindProductByCategoryOutputMapper mapper
    ) {
        this.mapper = mapper;
        this.productGateway = productGateway;
    }

    @Override
    public Pagination<FindProductByCategoryOutput> execute(FindProductByCategoryInput input) {

        return productGateway.findProductByCategory(
                input.category(),
                input.searchQuery()
        ).map(mapper::toOutput);
    }
}
