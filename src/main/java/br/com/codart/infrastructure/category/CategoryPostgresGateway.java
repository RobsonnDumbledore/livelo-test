package br.com.codart.infrastructure.category;

import java.util.Map;
import java.util.Set;
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
    private final GenericBatchUpdateService batchUpdateService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryPostgresGateway.class);

    public CategoryPostgresGateway(
            CategoryRepository categoryRepository,
            GenericBatchUpdateService batchUpdateService
    ) {
        this.categoryRepository = categoryRepository;
        this.batchUpdateService = batchUpdateService;
    }


    @Override
    public void createCategory(Set<Category> categories) {

        logger.info("Starting creation of categories. Number of categories to be created: {}", categories.size());

        final var categoriesEntity = mapTo(categories, CategoryEntity::fromDomain);

        if (isNotEmpty(categoriesEntity)) {

            this.categoryRepository.saveAll(categoriesEntity);
        }
    }

    @Override
    public void changeCategoryStatus(Map<CategoryID, Boolean> categoryStatus) {

        logger.info("Starting status change for {} categories.", categoryStatus.size());

        Map<String, Boolean> convertedCategoryStatus = categoryStatus.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getValue(),
                        Map.Entry::getValue
                ));

        final var query = "UPDATE category SET active = :isActive WHERE id = :categoryId";
        this.batchUpdateService.executeBatchUpdate(query, convertedCategoryStatus, "categoryId", CategoryEntity.class);
    }

    @Override
    public void updateCategory(Category category) {

        logger.info("Updating category with ID: {}", category.getId());

        final var categoryEntity = CategoryEntity.fromDomain(category);
        this.categoryRepository.save(categoryEntity);
    }

    @Override
    public void deleteCategory(Set<CategoryID> categoryIds) {

        logger.info("Remove categories for : {}", categoryIds.size());

        if (isNotEmpty(categoryIds)) {
            final var ids = mapTo(categoryIds, CategoryID::getValue);
            this.categoryRepository.deleteByIdIn(ids);
        }

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
    public Category findCategoryById(CategoryID categoryId) {

        logger.info("Searching for category with ID: {}", categoryId);

       final var categoryEntity = this.categoryRepository.findById(categoryId.getValue())
                .orElseThrow(() -> new NotFoundException("Category not found for ID: " + categoryId.getValue()));

        return CategoryEntity.toDomain(categoryEntity);
    }
}
