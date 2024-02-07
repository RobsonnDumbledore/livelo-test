package br.com.codart.infrastructure.configuration.usecase;

import br.com.codart.application.usecase.category.create.CreateCategoryInputMapper;
import br.com.codart.application.usecase.category.create.CreateCategoryUseCase;
import br.com.codart.application.usecase.category.create.DefaultCreateCategory;
import br.com.codart.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategory(
                categoryGateway,
                new CreateCategoryInputMapper()
        );
    }

}
