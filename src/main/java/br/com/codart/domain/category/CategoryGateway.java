package br.com.codart.domain.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import br.com.codart.domain.product.ProductID;
import br.com.codart.domain.product.SimpleProductView;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;

public interface CategoryGateway {

    void createCategory(Set<Category> categories);
    void changeCategoryStatus(Boolean isActive, List<CategoryID> categoryIds);
    void updateCategory(Category category);
    void deleteCategory(Set<CategoryID> categoryIds);
    List<Category> findAllCategoryById(List<CategoryID> categoryIds);
    Pagination<Category> findAllCategories(String categoryName, SearchQuery searchQuery);
    Optional<Category> findCategoryById(CategoryID categoryId);

}
