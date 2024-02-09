package br.com.codart.infrastructure.configuration.usecase;

import br.com.codart.application.usecase.category.findall.DefaultFindAllCategory;
import br.com.codart.application.usecase.category.findall.FindAllCategoryOutputMapper;
import br.com.codart.application.usecase.category.findall.FindAllCategoryUseCase;
import br.com.codart.application.usecase.category.remove.DefaultRemoveCategoryById;
import br.com.codart.application.usecase.category.remove.RemoveCategoryByIdUseCase;
import org.springframework.context.annotation.Bean;
import br.com.codart.domain.category.CategoryGateway;
import org.springframework.context.annotation.Configuration;
import br.com.codart.application.usecase.category.find.DefaultFindCategoryById;
import br.com.codart.application.usecase.category.find.FindCategoryByIdUseCase;
import br.com.codart.application.usecase.category.update.DefaultUpdateCategory;
import br.com.codart.application.usecase.category.update.UpdateCategoryUseCase;
import br.com.codart.application.usecase.category.create.CreateCategoryUseCase;
import br.com.codart.application.usecase.category.create.DefaultCreateCategory;
import br.com.codart.application.usecase.category.create.CreateCategoryInputMapper;
import br.com.codart.application.usecase.category.update.UpdateCategoryInputMapper;
import br.com.codart.application.usecase.category.changestatus.ChangeCategoryStatusUseCase;
import br.com.codart.application.usecase.category.changestatus.DefaultChangeCategoryStatus;

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

    @Bean
    public ChangeCategoryStatusUseCase changeCategoryStatusUseCase() {
        return new DefaultChangeCategoryStatus(
                categoryGateway
        );
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategory(
                categoryGateway,
                new UpdateCategoryInputMapper()
        );
    }

    @Bean
    public FindCategoryByIdUseCase findCategoryByIdUseCase() {
        return new DefaultFindCategoryById(categoryGateway);
    }

    @Bean
    public RemoveCategoryByIdUseCase removeCategoryByIdUseCase() {
        return new DefaultRemoveCategoryById(categoryGateway);
    }

    @Bean
    public FindAllCategoryUseCase findAllCategoriesUseCase() {
        return new DefaultFindAllCategory(
                categoryGateway,
                new FindAllCategoryOutputMapper()
        );
    }

}
