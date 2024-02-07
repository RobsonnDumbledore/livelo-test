package br.com.codart.infrastructure.product.mapper;

import java.util.List;

import br.com.codart.application.usecase.product.update.UpdateProductInput;
import br.com.codart.infrastructure.product.models.CreateProductRequest;
import br.com.codart.application.usecase.product.create.CreateProductInput;
import br.com.codart.infrastructure.product.models.CreateProductRequestList;
import br.com.codart.infrastructure.product.models.UpdateProductRequest;


public interface ProductMapper {

    static CreateProductInput toUseCase(CreateProductRequest createProductRequest) {

        return CreateProductInput.of(
                createProductRequest.name(),
                createProductRequest.price(),
                createProductRequest.brandId(),
                createProductRequest.categoryIds()
        );
    }

    static List<CreateProductInput> toUseCase(CreateProductRequestList createProductsRequest) {

        return createProductsRequest.products()
                .stream()
                .map(ProductMapper::toUseCase)
                .toList();
    }

    static UpdateProductInput toUseCase(UpdateProductRequest updateProductRequest) {

        return UpdateProductInput.of(
                updateProductRequest.id(),
                updateProductRequest.name(),
                updateProductRequest.price(),
                updateProductRequest.active(),
                updateProductRequest.brandId(),
                updateProductRequest.categoryIds()
        );
    }

}
