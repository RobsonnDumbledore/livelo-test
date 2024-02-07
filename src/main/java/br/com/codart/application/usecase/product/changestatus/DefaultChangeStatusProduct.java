package br.com.codart.application.usecase.product.changestatus;

import br.com.codart.domain.product.ProductID;
import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.product.SimpleProductView;
import br.com.codart.domain.exceptions.NotFoundException;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class DefaultChangeStatusProduct extends ChangeStatusProductUseCase {

    private final ProductGateway productGateway;

    public DefaultChangeStatusProduct(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(ChangeProductStatusInput input) {

        validateLimit(input.productIds(), 10);

        final var productIds = mapTo(input.productIds(), ProductID::from);
        final var foundProducts = productGateway.findAllProductById(productIds);

        if (foundProducts.isEmpty()) {
            throw new NotFoundException("No products found");
        }

        final var updatedProducts = foundProducts.stream()
                .map(SimpleProductView::id)
                .map(ProductID::from)
                .toList();

        productGateway.changeProductStatus(input.active(), updatedProducts);
    }
}
