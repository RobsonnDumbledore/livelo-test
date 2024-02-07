package br.com.codart.domain.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static <IN, OUT> Set<OUT> mapTo(final Set<IN> list, final Function<IN, OUT> mapper) {
        if (list == null) {
            return null;
        }

        return list.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static <IN, OUT> List<OUT> mapTo(final List<IN> list, final Function<IN, OUT> mapper) {
        if (list == null) {
            return null;
        }

        return list.stream()
                .map(mapper)
                .toList();
    }

    public static <T> Set<T> nullIfEmpty(final Set<T> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values;
    }

    public static boolean isEmpty(final Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T> Stream<T> stream(final Collection<T> collection) {
        return isEmpty(collection) ? Stream.<T>empty() : collection.<T>stream();
    }

}
