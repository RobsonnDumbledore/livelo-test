package br.com.codart.infrastructure.product.persistence;

import java.util.Set;

import br.com.codart.domain.product.SimpleProductView;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import java.util.stream.Collectors;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import br.com.codart.domain.product.Name;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.domain.product.Price;
import br.com.codart.domain.product.Product;
import br.com.codart.domain.product.ProductID;
import br.com.codart.domain.category.CategoryID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;
import br.com.codart.infrastructure.brand.persistence.BrandEntity;
import static br.com.codart.domain.utils.CollectionUtils.isNotEmpty;
import br.com.codart.infrastructure.category.persistence.CategoryEntity;

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

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories;

    public ProductEntity() {
    }

    public ProductEntity(
            String id,
            String productName,
            Double price,
            Boolean active,
            BrandEntity brand,
            Set<CategoryEntity> categories
    ) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.active = active;
        this.brand = brand;
        this.categories = categories;
    }

    public ProductEntity(String id) {
        this.id = id;
    }

    public ProductEntity(String id, String productName, Double price, Boolean active) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.active = active;
    }

    public static ProductEntity fromDomain(Product product) {

        final var categories = mapTo(product.getCategories(), CategoryEntity::fromDomain);

        return new ProductEntity(
                product.getId().getValue(),
                product.getProductName().getValue(),
                product.getPrice().getValue(),
                product.getActive(),
                BrandEntity.fromDomain(product.getBrandID()),
                categories
        );
    }

    public static Product toDomain(ProductEntity productEntity) {

        Set<CategoryID> categories = null;

        if(isNotEmpty(productEntity.getCategories())) {

            categories = productEntity.getCategories().stream()
                    .map(CategoryEntity::getId)
                    .map(CategoryID::from)
                    .collect(Collectors.toSet());
        }

        return Product.with(
                ProductID.from(productEntity.getId()),
                Name.of(productEntity.getProductName()),
                Price.of(productEntity.getPrice()),
                productEntity.getActive(),
                BrandID.from(productEntity.getBrand().getId()),
                categories
        );
    }

    public static SimpleProductView toSimpleDomain(ProductEntity productEntity) {
        return new SimpleProductView(
                productEntity.getId(),
                productEntity.getProductName(),
                productEntity.getPrice(),
                productEntity.getActive(),
                productEntity.getBrand().getId()
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

    public Boolean getActive() {
        return active;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public BrandEntity getBrand() {
        return brand;
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

