package br.com.codart.infrastructure.brand.persistence;

import java.util.Set;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import br.com.codart.domain.brand.Brand;
import br.com.codart.domain.brand.BrandID;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import br.com.codart.infrastructure.product.persistence.ProductEntity;

@Entity
@Table(name = "brand")
public class BrandEntity {

    @Id
    @Column(name = "brand_id")
    private String id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    public BrandEntity() {
    }

    public BrandEntity(String id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public static BrandEntity fromDomain(Brand brand) {
        return new BrandEntity(
                brand.getValue(),
                brand.getValue(),
                brand.isActive()
        );
    }

    public static Brand toDomain(BrandEntity brandEntity) {
        return Brand.with(
                BrandID.from(brandEntity.getId()),
                brandEntity.getName(),
                brandEntity.isActive()
        );
    }

    public static BrandEntity fromDomain(BrandID brandID) {
        return new BrandEntity(brandID.getValue());
    }

    public BrandEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BrandEntity that)) return false;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
