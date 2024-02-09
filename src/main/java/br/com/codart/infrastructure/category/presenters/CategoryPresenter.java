package br.com.codart.infrastructure.category.presenters;

import br.com.codart.application.usecase.category.find.FindCategoryByIdOutput;
import br.com.codart.application.usecase.category.findall.FindAllCategoryOutput;
import br.com.codart.application.usecase.product.findall.FindAllProductOutput;
import br.com.codart.infrastructure.category.models.FindAllCategoryResponse;
import br.com.codart.infrastructure.category.models.FindCategoryByIdResponse;
import br.com.codart.infrastructure.product.models.FindAllProductResponse;

public interface CategoryPresenter {

    static FindCategoryByIdResponse presenter(FindCategoryByIdOutput findCategoryByIdOutput) {
        return new FindCategoryByIdResponse(
                findCategoryByIdOutput.id(),
                findCategoryByIdOutput.name(),
                findCategoryByIdOutput.active()
        );
    }

    static FindAllCategoryResponse presenter(FindAllCategoryOutput findAllCategoryOutput) {
        return new FindAllCategoryResponse(
                findAllCategoryOutput.id(),
                findAllCategoryOutput.name(),
                findAllCategoryOutput.active()
        );
    }

}
