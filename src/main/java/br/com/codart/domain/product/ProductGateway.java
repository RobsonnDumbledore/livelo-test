package br.com.codart.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    List<Product> createProducts(List<Product> products);
    Optional<Product> findProductById(String productId);

}
