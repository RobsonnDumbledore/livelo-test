package br.com.codart.application.usecase.product.remove;

import java.util.List;

import br.com.codart.domain.product.Product;
import br.com.codart.domain.product.ProductID;
import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.product.SimpleProductView;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;
import static br.com.codart.domain.utils.CollectionUtils.isNotEmpty;

public class DefaultRemoveProductById extends RemoveProductByIdUseCase {

    private final ProductGateway productGateway;

    public DefaultRemoveProductById(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public RemoveProductByIdOutput execute(List<String> input) {

        validateLimit(input, 10);

        final var productIds = mapTo(input, ProductID::from);
        final var foundProducts = productGateway.findAllProductById(productIds);

        List<String> foundProductIds = foundProducts.stream()
                .map(SimpleProductView::id)
                .toList();

        List<String> notFoundIds = input.stream()
                .filter(id -> !foundProductIds.contains(id))
                .toList();

        if (isNotEmpty(foundProducts)) {
            productGateway.removeProduct(mapTo(foundProductIds, ProductID::from));
        }

        return RemoveProductByIdOutput.of(foundProductIds, notFoundIds);
    }

}
