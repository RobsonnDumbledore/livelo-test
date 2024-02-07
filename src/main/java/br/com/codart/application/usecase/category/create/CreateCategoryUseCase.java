package br.com.codart.application.usecase.category.create;

import java.util.Set;
import br.com.codart.application.UnitUseCase;
import br.com.codart.application.usecase.ValidateLimit;

public abstract class CreateCategoryUseCase extends UnitUseCase<Set<CreateCategoryInput>> implements ValidateLimit {
}
