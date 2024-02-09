package br.com.codart.application.usecase.category.changestatus;

import java.util.List;

public record ChangeCategoryStatusInput(
        Boolean active,
        List<String> categoryIds
) {
}
