package br.com.codart.application.usecase;

import java.util.Collection;
import java.util.List;
import br.com.codart.domain.exceptions.LimitExceededException;

public interface ValidateLimit {
    default void validateLimit(final Collection<?> items, final int limit) {
        if (items.size() > limit) {
            throw new LimitExceededException(limit);
        }
    }
}
