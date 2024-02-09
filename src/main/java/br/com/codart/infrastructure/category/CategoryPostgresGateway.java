package br.com.codart.infrastructure.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import br.com.codart.domain.product.ProductID;
import br.com.codart.infrastructure.product.persistence.ProductEntity;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.category.Category;
import br.com.codart.domain.utils.SearchQuery;
import br.com.codart.domain.category.CategoryID;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.PageRequest;
import br.com.codart.domain.category.CategoryGateway;
import br.com.codart.domain.exceptions.NotFoundException;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;
import br.com.codart.infrastructure.utils.GenericBatchUpdateService;
import static br.com.codart.domain.utils.CollectionUtils.isNotEmpty;
import br.com.codart.infrastructure.category.persistence.CategoryEntity;
import br.com.codart.infrastructure.category.persistence.CategoryRepository;

@Component
public class CategoryPostgresGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryPostgresGateway.class);

    public CategoryPostgresGateway(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void createCategory(Set<Category> categories) {

        logger.info("Starting creation of categories. Number of categories to be created: {}", categories.size());

        final Set<CategoryEntity> categoriesEntity = mapTo(categories, category -> CategoryEntity.fromDomain(category, true));

        if (isNotEmpty(categoriesEntity)) {
            this.categoryRepository.saveAll(categoriesEntity);
        }
    }

    @Override
    public void changeCategoryStatus(Boolean isActive, List<CategoryID> categoryIds) {

        logger.info("Starting status change for {} categories.", categoryIds.size());
        final var categoriesToUpdate = mapTo(categoryIds, CategoryID::getValue);

        this.categoryRepository.updateCategoryStatus(isActive, categoriesToUpdate);
    }

    @Override
    public void updateCategory(Category category) {

        logger.info("Updating category with ID: {}", category.getId());

        final var categoryEntity = CategoryEntity.fromDomain(category, false);
        this.categoryRepository.save(categoryEntity);
    }

    @Override
    @Transactional
    public void deleteCategory(Set<CategoryID> categoryIds) {

        logger.info("Remove categories for : {}", categoryIds.size());

        if (isNotEmpty(categoryIds)) {
            final var ids = mapTo(categoryIds, CategoryID::getValue);
            this.categoryRepository.deleteByIdIn(ids);
        }

    }

    @Override
    public List<Category> findAllCategoryById(List<CategoryID> categoryIds) {

        final var productEntities = categoryRepository.findAllByIdIn(mapTo(categoryIds, CategoryID::getValue));

        return productEntities.stream()
                .map(CategoryEntity::toDomain)
                .toList();
    }

    @Override
    public Pagination<Category> findAllCategories(String categoryName, SearchQuery searchQuery) {

        final var page = PageRequest.of(
                searchQuery.page(),
                searchQuery.perPage(),
                Sort.by(Sort.Direction.fromString(searchQuery.direction()), searchQuery.sort())
        );

        final var pageResult = this.categoryRepository.findAllCategories(categoryName, page);

        return new Pagination<>(
                pageResult.map(CategoryEntity::toDomain).toList(),
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    @Override
    public Optional<Category> findCategoryById(CategoryID categoryId) {

        logger.info("Searching for category with ID: {}", categoryId);

        return categoryRepository.findById(categoryId.getValue())
                .map(CategoryEntity::toDomain);
    }
}
