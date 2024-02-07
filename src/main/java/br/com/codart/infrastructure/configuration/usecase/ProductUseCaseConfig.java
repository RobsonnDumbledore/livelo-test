package br.com.codart.infrastructure.configuration.usecase;

import br.com.codart.application.usecase.product.changestatus.ChangeStatusProductUseCase;
import br.com.codart.application.usecase.product.changestatus.DefaultChangeStatusProduct;
import br.com.codart.application.usecase.product.find.FindProductByIdOutputMapper;
import br.com.codart.application.usecase.product.findall.DefaultFindAllProduct;
import br.com.codart.application.usecase.product.findall.FindAllProductOutputMapper;
import br.com.codart.application.usecase.product.findall.FindAllProductUseCase;
import br.com.codart.application.usecase.product.findbybrand.DefaultFindProductByBrand;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandOutputMapper;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandUseCase;
import br.com.codart.application.usecase.product.findbycategory.DefaultFindProductByCategory;
import br.com.codart.application.usecase.product.findbycategory.FindProductByCategoryOutputMapper;
import br.com.codart.application.usecase.product.findbycategory.FindProductByCategoryUseCase;
import br.com.codart.application.usecase.product.remove.DefaultRemoveProductById;
import br.com.codart.application.usecase.product.remove.RemoveProductByIdUseCase;
import br.com.codart.application.usecase.product.update.DefaultUpdateProduct;
import br.com.codart.application.usecase.product.update.UpdateProductInputMapper;
import br.com.codart.application.usecase.product.update.UpdateProductUseCase;
import br.com.codart.domain.product.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.codart.application.usecase.product.create.CreateProductUseCase;
import br.com.codart.application.usecase.product.create.DefaultCreateProduct;
import br.com.codart.application.usecase.product.find.DefaultFindProductById;
import br.com.codart.application.usecase.product.find.FindProductByIdUseCase;
import br.com.codart.application.usecase.product.create.CreateProductInputMapper;

@Configuration
public class ProductUseCaseConfig {

    private final ProductGateway productGateway;

    public ProductUseCaseConfig(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new DefaultCreateProduct(
                productGateway,
                new CreateProductInputMapper()
        );
    }

    @Bean
    public FindProductByIdUseCase findProductByIdUseCase() {
        return new DefaultFindProductById(
                productGateway,
                new FindProductByIdOutputMapper()
        );
    }

    @Bean
    public RemoveProductByIdUseCase removeProductByIdUseCase() {
        return new DefaultRemoveProductById(productGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new DefaultUpdateProduct(
                productGateway,
                new UpdateProductInputMapper()
        );
    }

    @Bean
    public FindProductByCategoryUseCase findProductByCategoryUseCase() {
        return new DefaultFindProductByCategory(
                productGateway,
                new FindProductByCategoryOutputMapper()
        );
    }

    @Bean
    public FindProductByBrandUseCase findProductByBrandUseCase() {
        return new DefaultFindProductByBrand(
                productGateway,
                new FindProductByBrandOutputMapper()
        );
    }

    @Bean
    public FindAllProductUseCase findAllProductUseCase() {
        return new DefaultFindAllProduct(
                productGateway,
                new FindAllProductOutputMapper()
        );
    }

    @Bean
    public ChangeStatusProductUseCase changeStatusProductUseCase() {
        return new DefaultChangeStatusProduct(
                productGateway
        );
    }

}
