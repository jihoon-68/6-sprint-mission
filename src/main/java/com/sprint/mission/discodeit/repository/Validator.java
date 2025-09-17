package com.sprint.mission.discodeit.repository;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
interface Validator<T> {

    T validate(Map<?, ? extends T> data, T t);

    static <T> Validator<T> identity() {
        return (data, t) -> t;
    }

    static <T, U, X extends RuntimeException> Validator<T> uniqueKey(
            Function<? super T, ? extends U> keyExtractor,
            Supplier<? extends X> exceptionSupplier
    ) {
        return (data, t) -> {
            boolean exists = data.values()
                    .stream()
                    .anyMatch(value -> keyExtractor.apply(value).equals(keyExtractor.apply(t)));
            if (exists) {
                throw exceptionSupplier.get();
            }
            return t;
        };
    }

    static <T, U, V, X extends RuntimeException> Validator<T> uniqueKeys(
            Function<? super T, ? extends U> keyExtractor1,
            Function<? super T, ? extends V> keyExtractor2,
            Supplier<? extends X> exceptionSupplier
    ) {
        return (data, t) -> {
            boolean exists = data.values()
                    .stream()
                    .map(value -> "%s:%s".formatted(keyExtractor1.apply(value), keyExtractor2.apply(value)))
                    .anyMatch(key -> key.equals("%s:%s".formatted(keyExtractor1.apply(t), keyExtractor2.apply(t))));
            if (exists) {
                throw exceptionSupplier.get();
            }
            return t;
        };
    }

    default Validator<T> and(Validator<T> after) {
        return (data, t) -> after.validate(data, this.validate(data, t));
    }
}
