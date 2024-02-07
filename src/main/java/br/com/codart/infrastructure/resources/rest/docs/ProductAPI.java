package br.com.codart.infrastructure.resources.rest.docs;

import java.util.List;
import br.com.codart.domain.utils.Pagination;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import br.com.codart.infrastructure.product.models.UpdateProductRequest;
import br.com.codart.infrastructure.product.models.FindAllProductResponse;
import br.com.codart.infrastructure.product.models.FindProductByIdResponse;
import br.com.codart.infrastructure.product.models.CreateProductRequestList;
import br.com.codart.infrastructure.product.models.RemoveProductByIdResponse;
import br.com.codart.infrastructure.product.models.ChangeProductStatusRequest;
import br.com.codart.infrastructure.product.models.FindProductByBrandResponse;
import br.com.codart.infrastructure.product.models.FindProductByCategoryResponse;

@Tag(name = "products")
public interface ProductAPI {

    @Operation(summary = "create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> createProducts(@RequestBody CreateProductRequestList createProductsRequest);

    @Operation(summary = "find product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<FindProductByIdResponse> findProductById(@PathVariable("productId") String productId);

    @Operation(
            summary = "remove product by id",
            description =
                    """
                        example:
                        api/products/v1?productIds=4,5
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<RemoveProductByIdResponse> removeProductById(@RequestParam List<String> productIds);


    @Operation(summary = "update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> updateProduct(@RequestBody UpdateProductRequest updateProductRequest);

    @Operation(summary = "find product by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindProductByCategoryResponse>> findProductByCategory(

            @RequestParam(name = "category") String category,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "price") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    );

    @Operation(summary = "find product by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindProductByBrandResponse>> findProductByBrand(

            @RequestParam(name = "brand") String brand,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "price") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    );

    @Operation(summary = "find all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Pagination<FindAllProductResponse>> findAllProducts(

            @RequestParam(name = "productName", required = false, defaultValue = "") String productName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "price") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    );

    @Operation(summary = "change product status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product status changed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> changeProductStatus(@RequestBody ChangeProductStatusRequest changeProductStatusRequest);

}
