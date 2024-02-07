package br.com.codart.application.usecase.product.findbybrand;

import br.com.codart.application.usecase.OutputMapper;
import br.com.codart.domain.product.SimpleProductView;

public class FindProductByBrandOutputMapper implements OutputMapper<SimpleProductView, FindProductByBrandOutput> {

    @Override
    public FindProductByBrandOutput toOutput(SimpleProductView domain) {
        return new FindProductByBrandOutput(
                domain.id(),
                domain.name(),
                domain.price(),
                domain.active()
        );
    }
}
