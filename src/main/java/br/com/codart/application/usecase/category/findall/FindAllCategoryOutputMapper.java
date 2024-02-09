package br.com.codart.application.usecase.category.findall;

import br.com.codart.domain.category.Category;
import br.com.codart.application.usecase.OutputMapper;

public class FindAllCategoryOutputMapper implements OutputMapper<Category, FindAllCategoryOutput> {

    @Override
    public FindAllCategoryOutput toOutput(Category domain) {
        return new FindAllCategoryOutput(
                domain.getId().getValue(),
                domain.getName(),
                domain.isActive()
        );
    }
}
