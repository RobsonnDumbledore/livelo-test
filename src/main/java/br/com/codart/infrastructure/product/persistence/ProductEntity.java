package br.com.codart.infrastructure.product.persistence;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.product.Product;
import br.com.codart.domain.product.ProductID;
import br.com.codart.domain.product.Name;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    private String id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double price;

    public ProductEntity() {
    }

    public ProductEntity(String id, String productName, Double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    public ProductEntity(String id) {
        this.id = id;
    }

    public static ProductEntity fromDomain(Product product) {
        return new ProductEntity(
                product.getId().getValue(),
                product.getProductName().getValue(),
                product.getPrice().getValue()
        );
    }

    public static Product toDomain(ProductEntity productEntity) {
        return Product.with(
                ProductID.from(productEntity.getId()),
                Name.of(productEntity.getProductName()),
                Price.of(productEntity.getPrice())
        );
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ProductEntity that)) return false;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}

