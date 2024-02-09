package br.com.codart.infrastructure.resources.rest;

import java.util.Set;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.codart.infrastructure.category.mapper.CategoryMapper;
import br.com.codart.infrastructure.resources.rest.docs.CategoryAPI;
import br.com.codart.infrastructure.category.presenters.CategoryPresenter;
import br.com.codart.infrastructure.category.models.UpdateCategoryRequest;
import br.com.codart.infrastructure.category.models.FindAllCategoryResponse;
import br.com.codart.infrastructure.category.models.FindCategoryByIdResponse;
import br.com.codart.infrastructure.category.models.CreateCategoryRequestSet;
import br.com.codart.application.usecase.category.update.UpdateCategoryUseCase;
import br.com.codart.application.usecase.category.create.CreateCategoryUseCase;
import br.com.codart.application.usecase.category.find.FindCategoryByIdUseCase;
import br.com.codart.application.usecase.category.findall.FindAllCategoryInput;
import br.com.codart.infrastructure.category.models.ChangeCategoryStatusRequest;
import br.com.codart.application.usecase.category.findall.FindAllCategoryUseCase;
import br.com.codart.application.usecase.category.remove.RemoveCategoryByIdUseCase;
import static br.com.codart.infrastructure.category.mapper.CategoryMapper.toUseCase;
import br.com.codart.application.usecase.category.changestatus.ChangeCategoryStatusInput;
import br.com.codart.application.usecase.category.changestatus.ChangeCategoryStatusUseCase;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final FindAllCategoryUseCase findAllCategoryUseCase;
    private final FindCategoryByIdUseCase findCategoryByIdUseCase;
    private final RemoveCategoryByIdUseCase removeCategoryByIdUseCase;
    private final ChangeCategoryStatusUseCase changeCategoryStatusUseCase;

    @Autowired
    public CategoryResource(
            CreateCategoryUseCase createCategoryUseCase,
            UpdateCategoryUseCase updateCategoryUseCase,
            FindAllCategoryUseCase findAllCategoryUseCase,
            FindCategoryByIdUseCase findCategoryByIdUseCase,
            RemoveCategoryByIdUseCase removeCategoryByIdUseCase,
            ChangeCategoryStatusUseCase changeCategoryStatusUseCase
    ) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.findAllCategoryUseCase = findAllCategoryUseCase;
        this.findCategoryByIdUseCase = findCategoryByIdUseCase;
        this.removeCategoryByIdUseCase = removeCategoryByIdUseCase;
        this.changeCategoryStatusUseCase = changeCategoryStatusUseCase;
    }

    @Override
    @PostMapping("/v1")
    public ResponseEntity<Void> createCategories(CreateCategoryRequestSet createCategoriesRequest) {

        final var categories = toUseCase(createCategoriesRequest);
        createCategoryUseCase.execute(categories);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/v1")
    public ResponseEntity<Void> changeCategoryStatus(ChangeCategoryStatusRequest changeCategoryStatusRequest) {

        final var input = new ChangeCategoryStatusInput(
                changeCategoryStatusRequest.active(),
                changeCategoryStatusRequest.categoryIds()
        );

        changeCategoryStatusUseCase.execute(input);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/v1")
    public ResponseEntity<Void> updateCategory(UpdateCategoryRequest updateCategoryRequest) {

        final var request = CategoryMapper.toUseCase(updateCategoryRequest);
        updateCategoryUseCase.execute(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/{categoryId}")
    public ResponseEntity<FindCategoryByIdResponse> findCategoryById(String categoryId) {
        final var response = CategoryPresenter.presenter(findCategoryByIdUseCase.execute(categoryId));
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/v1")
    public ResponseEntity<Void> removeCategoryById(Set<String> categoryIds) {
        removeCategoryByIdUseCase.execute(categoryIds);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/v1")
    public ResponseEntity<Pagination<FindAllCategoryResponse>> findAllCategories(
            String categoryName,
            int page,
            int size,
            String sort,
            String direction
    ) {

        final var input = new FindAllCategoryInput(
                categoryName,
                SearchQuery.with(page, size, sort, direction)
        );

        final var response = findAllCategoryUseCase.execute(input)
                .map(CategoryPresenter::presenter);

        return ResponseEntity.ok(response);
    }
}
