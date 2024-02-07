package br.com.codart.domain.product;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;

public interface ProductGateway {

    List<Product> createProducts(List<Product> products);
    Optional<Product> findProductById(String productId);

    void updateProduct(Product product);
    void removeProduct(List<ProductID> productIds);
    void changeProductStatus(Boolean isActive, List<ProductID> productIds);

    List<SimpleProductView> findAllProductById(List<ProductID> productIds);
    Pagination<SimpleProductView> findAllProducts(String productName, SearchQuery searchQuery);
    Pagination<SimpleProductView> findProductByCategory(String category, SearchQuery searchQuery);
    Pagination<SimpleProductView> findProductByBrand(String brand, SearchQuery searchQuery);

}
