package br.com.codart.application.usecase.product.create;

import java.util.List;
import br.com.codart.application.UseCase;
import br.com.codart.application.usecase.ValidateLimit;

public abstract class CreateProductUseCase extends UseCase<List<CreateProductInput>, List<String>> implements ValidateLimit {
}
