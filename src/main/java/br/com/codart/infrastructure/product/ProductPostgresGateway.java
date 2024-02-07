package br.com.codart.infrastructure.product;

import br.com.codart.domain.product.Product;
import br.com.codart.domain.product.ProductGateway;
import br.com.codart.domain.product.ProductID;
import br.com.codart.domain.product.SimpleProductView;
import br.com.codart.domain.utils.CollectionUtils;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;
import br.com.codart.infrastructure.product.persistence.ProductEntity;
import br.com.codart.infrastructure.product.persistence.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.codart.domain.utils.CollectionUtils.mapTo;

@Component
public class ProductPostgresGateway implements ProductGateway {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductPostgresGateway.class);

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

    @Override
    public List<SimpleProductView> findAllProductById(List<ProductID> productIds) {

        final var productEntities = productRepository.findAllByIdIn(mapTo(productIds, ProductID::getValue));

        return productEntities.stream()
                .map(ProductEntity::toSimpleDomain)
                .toList();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateProduct(Product product) {
        logger.info("Updating product with ID: {}", product.getId());

        findProductById(product.getId().getValue())
                .ifPresent(existingProduct -> this.productRepository.save(ProductEntity.fromDomain(product)));
    }

    @Override
    @Transactional
    public void changeProductStatus(Boolean isActive, List<ProductID> productIds) {
        logger.info("Starting status change for {} products.", productIds.size());

        final var productsToUpdate = mapTo(productIds, ProductID::getValue);
        this.productRepository.updateProductStatus(isActive, productsToUpdate);
    }

    @Override
    @Transactional
    public void removeProduct(List<ProductID> productIds) {
        logger.info("Starting remove products for {} products.", productIds.size());
        this.productRepository.deleteByIdIn(mapTo(productIds, ProductID::getValue));
    }


    @Override
    @Cacheable(cacheNames = "findAllProducts", key = "{#productName, #searchQuery}")
    public Pagination<SimpleProductView> findAllProducts(String productName, SearchQuery searchQuery) {

        logger.info("Finding all products");

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var pageResult = this.productRepository.findAllProducts(productName, page);

        return new Pagination<>(
                pageResult.map(ProductEntity::toSimpleDomain).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    @Cacheable(cacheNames = "findProductByCategory", key = "{#category, #searchQuery}")
    public Pagination<SimpleProductView> findProductByCategory(String category, SearchQuery searchQuery) {
        logger.info("Finding products by category '{}'", category);

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var pageResult = this.productRepository.findByCategory(category, page);

        return new Pagination<>(
                pageResult.map(ProductEntity::toSimpleDomain).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    @Cacheable(cacheNames = "findProductByBrand", key = "{#brand, #searchQuery}")
    public Pagination<SimpleProductView> findProductByBrand(String brand, SearchQuery searchQuery) {

        logger.info("Finding products by brand '{}'", brand);

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var pageResult = this.productRepository.findByBrand(brand, page);

        return new Pagination<>(
                pageResult.map(ProductEntity::toSimpleDomain).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }
}
