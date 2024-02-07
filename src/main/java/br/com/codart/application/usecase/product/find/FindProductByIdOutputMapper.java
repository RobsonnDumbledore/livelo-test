package br.com.codart.application.usecase.product.find;

import br.com.codart.domain.product.Product;
import br.com.codart.domain.category.CategoryID;
import br.com.codart.application.usecase.OutputMapper;
import static br.com.codart.domain.utils.CollectionUtils.mapTo;

public class FindProductByIdOutputMapper implements OutputMapper<Product, FindProductByIdOutput> {

    @Override
    public FindProductByIdOutput toOutput(Product domain) {
        return new FindProductByIdOutput(
                domain.getId().getValue(),
                domain.getProductName().getValue(),
                domain.getPrice().getValue(),
                domain.getActive(),
                domain.getBrandID().getValue(),
                mapTo(domain.getCategories(), CategoryID::getValue)
        );
    }
}
