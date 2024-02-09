package br.com.codart.infrastructure.category.models;

import java.util.List;

public record ChangeCategoryStatusRequest(
        Boolean active,
        List<String> categoryIds
) {
}
