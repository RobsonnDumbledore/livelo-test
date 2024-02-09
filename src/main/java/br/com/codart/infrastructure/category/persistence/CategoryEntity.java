package br.com.codart.infrastructure.category.persistence;

import java.util.Set;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.persistence.ManyToMany;
import br.com.codart.domain.category.Category;
import br.com.codart.domain.category.CategoryID;
import org.springframework.data.domain.Persistable;
import br.com.codart.infrastructure.product.persistence.ProductEntity;

@Entity
@Table(name = "category")
public class CategoryEntity implements Persistable<String> {

    @Id
    @Column(name = "category_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active = true;

    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> products;

    @Transient
    private boolean newCategory;

    public CategoryEntity() {
    }

    public CategoryEntity(String id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public CategoryEntity(String id) {
        this.id = id;
    }

    public CategoryEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return newCategory;
    }

    public static CategoryEntity fromDomain(Category category, boolean isNew) {
        CategoryEntity entity = new CategoryEntity(
                category.getId().getValue(),
                category.getName(),
                true
        );
        entity.setNewCategory(isNew);
        return entity;
    }

    public static CategoryEntity fromDomain(CategoryID categoryID) {
        return new CategoryEntity(categoryID.getValue());
    }

    public static Category toDomain(CategoryEntity categoryEntity) {

        return Category.with(
                CategoryID.from(categoryEntity.getId()),
                categoryEntity.getName(),
                categoryEntity.isActive()
        );
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setNewCategory(boolean newCategory) {
        this.newCategory = newCategory;
    }
}
