package br.com.codart.infrastructure.product;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import br.com.codart.domain.product.Product;
import org.springframework.stereotype.Component;
import br.com.codart.domain.product.ProductGateway;
import br.com.codart.infrastructure.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.infrastructure.product.persistence.ProductEntity;
import static br.com.codart.infrastructure.utils.CollectionUtils.mapTo;
import br.com.codart.infrastructure.product.persistence.ProductRepository;

@Component
public class ProductPostgresGateway implements ProductGateway {

    private final ProductRepository productRepository;

    @Autowired
    public ProductPostgresGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> createProducts(List<Product> products) {

        final var entities = products.stream()
                .map(ProductEntity::fromDomain)
                .toList();

        List<ProductEntity> savedEntities = Collections.emptyList();

        if (CollectionUtils.isNotEmpty(entities)) {
            savedEntities = productRepository.saveAll(entities);
        }

        return mapTo(savedEntities, ProductEntity::toDomain);
    }

    @Override
    public Optional<Product> findProductById(String productId) {

        return productRepository.findById(productId)
                .map(ProductEntity::toDomain);
    }
}
