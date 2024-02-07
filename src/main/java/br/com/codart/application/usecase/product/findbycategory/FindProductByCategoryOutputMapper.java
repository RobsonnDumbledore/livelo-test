package br.com.codart.application.usecase.product.findbycategory;

import br.com.codart.application.usecase.OutputMapper;
import br.com.codart.domain.product.SimpleProductView;

public class FindProductByCategoryOutputMapper implements OutputMapper<SimpleProductView, FindProductByCategoryOutput> {

    @Override
    public FindProductByCategoryOutput toOutput(SimpleProductView domain) {
        return new FindProductByCategoryOutput(
                domain.id(),
                domain.name(),
                domain.price(),
                domain.active()
        );
    }
}
