package br.com.codart.domain.product;

import java.util.Set;
import java.util.HashSet;
import br.com.codart.domain.brand.BrandID;
import br.com.codart.domain.category.CategoryID;
import br.com.codart.domain.exceptions.BusinessException;
import static br.com.codart.domain.utils.CollectionUtils.isNotEmpty;

public class Product {

    private final ProductID id;
    private final Name name;
    private final Price price;
    private final Boolean active;
    private final BrandID brandID;
    private final Set<CategoryID> categories;

    private Product(
            ProductID id,
            Name name,
            Price price,
            Boolean active,
            BrandID brandID,
            Set<CategoryID> categories
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active != null ? active : true;
        this.brandID = brandID;
        this.categories = categories != null ? categories : new HashSet<>();
        validateProduct();
    }

    public static Product newProduct(
            final Name name,
            final Price price,
            final BrandID brandID
    ) {

        final var id = ProductID.unique();

        return new Product(
                id,
                name,
                price,
                true,
                brandID,
                new HashSet<>()
        );
    }

    public static Product newProduct(
            final Name name,
            final Price price,
            final BrandID brandID,
            final Set<CategoryID> categories
    ) {

        final var id = ProductID.unique();

        return new Product(
                id,
                name,
                price,
                true,
                brandID,
                categories != null? categories : new HashSet<>()
        );
    }

    public static Product with(
            final ProductID id,
            final Name name,
            final Price price,
            final Boolean active,
            final BrandID brandID,
            final Set<CategoryID> categories
    ) {
        return new Product(
                id,
                name,
                price,
                active != null? active : true,
                brandID,
                categories
        );
    }

    public Product addCategories(final Set<CategoryID> categories) {

        if (isNotEmpty(categories)) {

            this.categories.addAll(categories);
        }

        return this;
    }

    public Product removeCategories() {

        this.categories.clear();

        return this;
    }

    private void validateProduct() {
        if (this.name == null
                || this.price == null
                || this.brandID == null
        ) {
            throw new BusinessException("the product name and price cannot be null");
        }
    }

    public ProductID getId() {
        return id;
    }

    public Name getProductName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Boolean getActive() {
        return active;
    }

    public BrandID getBrandID() {
        return brandID;
    }

    public Set<CategoryID> getCategories() {
        return categories;
    }
}
