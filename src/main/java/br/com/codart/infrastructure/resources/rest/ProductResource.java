package br.com.codart.infrastructure.resources.rest;

import java.util.List;
import br.com.codart.domain.utils.Pagination;
import br.com.codart.domain.utils.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.codart.infrastructure.product.mapper.ProductMapper;
import br.com.codart.infrastructure.resources.rest.docs.ProductAPI;
import br.com.codart.infrastructure.product.models.UpdateProductRequest;
import br.com.codart.infrastructure.product.presenters.ProductPresenter;
import br.com.codart.infrastructure.product.models.FindAllProductResponse;
import br.com.codart.infrastructure.product.models.FindProductByIdResponse;
import br.com.codart.infrastructure.product.models.CreateProductRequestList;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.infrastructure.product.models.RemoveProductByIdResponse;
import br.com.codart.application.usecase.product.update.UpdateProductUseCase;
import br.com.codart.application.usecase.product.findall.FindAllProductInput;
import br.com.codart.infrastructure.product.models.ChangeProductStatusRequest;
import br.com.codart.infrastructure.product.models.FindProductByBrandResponse;
import br.com.codart.application.usecase.product.findall.FindAllProductUseCase;
import br.com.codart.infrastructure.product.models.FindProductByCategoryResponse;
import br.com.codart.application.usecase.product.remove.RemoveProductByIdUseCase;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandInput;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandUseCase;
import br.com.codart.application.usecase.product.changestatus.ChangeProductStatusInput;
import br.com.codart.application.usecase.product.changestatus.ChangeStatusProductUseCase;
import br.com.codart.application.usecase.product.findbycategory.FindProductByCategoryInput;
import br.com.codart.application.usecase.product.findbycategory.FindProductByCategoryUseCase;

@RestController
@RequestMapping("/api/products")
public class ProductResource implements ProductAPI {

    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final FindAllProductUseCase findAllProductUseCase;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final RemoveProductByIdUseCase removeProductByIdUseCase;
    private final FindProductByBrandUseCase findProductByBrandUseCase;
    private final ChangeStatusProductUseCase changeStatusProductUseCase;
    private final FindProductByCategoryUseCase findProductByCategoryUseCase;

    @Autowired
    public ProductResource(
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            FindAllProductUseCase findAllProductUseCase,
            FindProductByIdUseCase findProductByIdUseCase,
            RemoveProductByIdUseCase removeProductByIdUseCase,
            FindProductByBrandUseCase findProductByBrandUseCase,
            ChangeStatusProductUseCase changeStatusProductUseCase,
            FindProductByCategoryUseCase findProductByCategoryUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.findAllProductUseCase = findAllProductUseCase;
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.removeProductByIdUseCase = removeProductByIdUseCase;
        this.findProductByBrandUseCase = findProductByBrandUseCase;
        this.changeStatusProductUseCase = changeStatusProductUseCase;
        this.findProductByCategoryUseCase = findProductByCategoryUseCase;
    }

    @Override
    @PostMapping("/v1")
    public ResponseEntity<Void> createProducts(@Validated CreateProductRequestList createProductsRequest) {

        createProductUseCase.execute(ProductMapper.toUseCase(createProductsRequest));

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/{productId}")
    public ResponseEntity<FindProductByIdResponse> findProductById(String productId) {

        final var product = ProductPresenter.presenter(findProductByIdUseCase.execute(productId));

        return ResponseEntity.ok(product);
    }

    @Override
    @DeleteMapping("/v1")
    public ResponseEntity<RemoveProductByIdResponse> removeProductById(List<String> productIds) {
        final var response = removeProductByIdUseCase.execute(productIds);
        return ResponseEntity.ok(ProductPresenter.presenter(response));
    }

    @Override
    @PutMapping("/v1")
    public ResponseEntity<Void> updateProduct(UpdateProductRequest updateProductRequest) {

        final var request = ProductMapper.toUseCase(updateProductRequest);
        updateProductUseCase.execute(request);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/v1/category")
    public ResponseEntity<Pagination<FindProductByCategoryResponse>> findProductByCategory(
            String category,
            int page,
            int size,
            String sort,
            String direction
    ) {

        final var input = new FindProductByCategoryInput(
                category,
                SearchQuery.with(page, size, sort, direction)
        );

        final var response = findProductByCategoryUseCase.execute(input)
                .map(ProductPresenter::presenter);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/v1/brand")
    public ResponseEntity<Pagination<FindProductByBrandResponse>> findProductByBrand(
            String brand,
            int page,
            int size,
            String sort,
            String direction
    ) {

        final var input = new FindProductByBrandInput(
                brand,
                SearchQuery.with(page, size, sort, direction)
        );

        final var response = findProductByBrandUseCase.execute(input)
                .map(ProductPresenter::presenter);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/v1")
    public ResponseEntity<Pagination<FindAllProductResponse>> findAllProducts(
            String productName,
            int page,
            int size,
            String sort,
            String direction
    ) {
        final var input = new FindAllProductInput(
                productName,
                SearchQuery.with(page, size, sort, direction)
        );

        final var response = findAllProductUseCase.execute(input)
                .map(ProductPresenter::presenter);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @PatchMapping("/v1")
    public ResponseEntity<Void> changeProductStatus(ChangeProductStatusRequest changeProductStatusRequest) {

        final var input = new ChangeProductStatusInput(
                changeProductStatusRequest.active(),
                changeProductStatusRequest.productIds()
        );

        changeStatusProductUseCase.execute(input);

        return ResponseEntity.noContent().build();
    }

}
