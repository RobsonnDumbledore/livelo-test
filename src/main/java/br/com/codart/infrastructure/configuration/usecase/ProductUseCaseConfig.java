package br.com.codart.infrastructure.configuration.usecase;

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
        return new DefaultFindProductById(productGateway);
    }
}
