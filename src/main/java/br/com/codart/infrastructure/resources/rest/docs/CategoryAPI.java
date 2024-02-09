package br.com.codart.infrastructure.resources.rest.docs;

import br.com.codart.domain.utils.Pagination;
import br.com.codart.infrastructure.category.models.ChangeCategoryStatusRequest;
import br.com.codart.infrastructure.category.models.CreateCategoryRequestSet;
import br.com.codart.infrastructure.category.models.FindAllCategoryResponse;
import br.com.codart.infrastructure.category.models.FindCategoryByIdResponse;
import br.com.codart.infrastructure.category.models.UpdateCategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Tag(name = "categories")
public interface CategoryAPI {

    @Operation(summary = "create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> createCategories(@RequestBody CreateCategoryRequestSet createCategoriesRequest);

    @Operation(summary = "change category status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category status changed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> changeCategoryStatus(@RequestBody ChangeCategoryStatusRequest changeCategoryStatusRequest);

    @Operation(summary = "update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest);

    @Operation(summary = "find category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<FindCategoryByIdResponse> findCategoryById(@PathVariable("categoryId") String categoryId);

    @Operation(
            summary = "remove category by id",
            description =
                    """
                        example:
                        api/categories/v1?categoryIds=id01,id02,id03...
                        limit of 10 items per request
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category removed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> removeCategoryById(@RequestParam("categoryIds") Set<String> categoryIds);

    @Operation(summary = "find all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindAllCategoryResponse>> findAllCategories(

            @RequestParam(name = "categoryName", required = false, defaultValue = "") String categoryName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") String direction
    );


}
