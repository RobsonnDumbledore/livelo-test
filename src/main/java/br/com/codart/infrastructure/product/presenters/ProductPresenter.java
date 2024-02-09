package br.com.codart.infrastructure.product.presenters;

import br.com.codart.application.usecase.product.findall.FindAllProductOutput;
import br.com.codart.application.usecase.product.findbybrand.FindProductByBrandOutput;
import br.com.codart.application.usecase.product.findbycategory.FindProductByCategoryOutput;
import br.com.codart.infrastructure.product.models.FindAllProductResponse;
import br.com.codart.infrastructure.product.models.FindProductByBrandResponse;
import br.com.codart.infrastructure.product.models.FindProductByCategoryResponse;
import br.com.codart.infrastructure.product.models.FindProductByIdResponse;
import br.com.codart.application.usecase.product.find.FindProductByIdOutput;
import br.com.codart.infrastructure.product.models.RemoveProductByIdResponse;
import br.com.codart.application.usecase.product.remove.RemoveProductByIdOutput;

public interface ProductPresenter {

    static FindProductByIdResponse presenter(FindProductByIdOutput findProductByIdOutput) {
        return new FindProductByIdResponse(
                findProductByIdOutput.id(),
                findProductByIdOutput.name(),
                findProductByIdOutput.price(),
                findProductByIdOutput.active(),
                findProductByIdOutput.brandId(),
                findProductByIdOutput.categories()
        );
    }

    static RemoveProductByIdResponse presenter(RemoveProductByIdOutput removeProductByIdOutput) {
        return RemoveProductByIdResponse.of(
                removeProductByIdOutput.found(),
                removeProductByIdOutput.notFound()
        );
    }

    static FindProductByCategoryResponse presenter(FindProductByCategoryOutput findProductByCategoryOutput) {
        return new FindProductByCategoryResponse(
                findProductByCategoryOutput.id(),
                findProductByCategoryOutput.name(),
                findProductByCategoryOutput.price(),
                findProductByCategoryOutput.active()
        );
    }

    static FindProductByBrandResponse presenter(FindProductByBrandOutput findProductByCategoryOutput) {
        return new FindProductByBrandResponse(
                findProductByCategoryOutput.id(),
                findProductByCategoryOutput.name(),
                findProductByCategoryOutput.price(),
                findProductByCategoryOutput.active()
        );
    }

    static FindAllProductResponse presenter(FindAllProductOutput findAllProductOutput) {
        return new FindAllProductResponse(
                findAllProductOutput.id(),
                findAllProductOutput.name(),
                findAllProductOutput.price(),
                findAllProductOutput.active()
        );
    }

}
