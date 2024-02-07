package br.com.codart.domain.category;

import java.util.Map;
import java.util.Set;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;

public interface CategoryGateway {

    void createCategory(Set<Category> categories);
    void changeCategoryStatus(Map<CategoryID, Boolean> categoryStatus);
    void updateCategory(Category category);
    void deleteCategory(Set<CategoryID> categoryIds);
    Pagination<Category> findAllCategories(String categoryName, SearchQuery searchQuery);
    Category findCategoryById(CategoryID categoryId);

}
