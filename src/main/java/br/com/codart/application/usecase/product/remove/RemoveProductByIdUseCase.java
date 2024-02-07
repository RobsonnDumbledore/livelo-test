package br.com.codart.application.usecase.product.remove;

import java.util.List;

import br.com.codart.application.UseCase;
import br.com.codart.application.usecase.ValidateLimit;

public abstract class RemoveProductByIdUseCase extends UseCase<List<String>, RemoveProductByIdOutput> implements ValidateLimit {
}
