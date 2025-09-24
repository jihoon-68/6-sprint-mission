package com.sprint.mission.discodeit.common.persistence;

import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface Validator<T> {

    T validate(Map<?, ? extends T> data, T t);

    static <T> Validator<T> identity() {
        return (_, t) -> t;
    }

    static <T, U, X extends RuntimeException> Validator<T> uniqueKey(
            Function<? super T, ? extends U> keyExtractor,
            Function<? super T, ? extends X> exceptionSupplier
    ) {
        return (data, t) -> {
            boolean exists = data.values()
                    .stream()
                    .anyMatch(value -> keyExtractor.apply(value).equals(keyExtractor.apply(t)));
            if (exists) {
                throw exceptionSupplier.apply(t);
            }
            return t;
        };
    }

    default Validator<T> and(Validator<T> after) {
        return (data, t) -> after.validate(data, this.validate(data, t));
    }
}
