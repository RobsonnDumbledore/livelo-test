package br.com.codart.application.usecase.product.findall;

import br.com.codart.application.usecase.OutputMapper;
import br.com.codart.domain.product.SimpleProductView;

public class FindAllProductOutputMapper implements OutputMapper<SimpleProductView, FindAllProductOutput> {
    @Override
    public FindAllProductOutput toOutput(SimpleProductView domain) {
        return new FindAllProductOutput(
                domain.id(),
                domain.name(),
                domain.price(),
                domain.active()
        );
    }
}
