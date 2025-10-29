package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public final class PageResponseMapper {

    private PageResponseMapper() {
    }

    public static <T, R> PagedResponse<R> fromPage(Page<T> page, java.util.function.Function<T, R> converter) {

        List<R> content = page.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());

        int number = page.getNumber();
        int size = page.getSize();
        boolean hasNext = page.hasNext();
        Long totalElements = page.getTotalElements();

        return PagedResponse.of(content, number, size, hasNext, totalElements);
    }

    public static <T, R> PagedResponse<R> fromSlice(Slice<T> slice, java.util.function.Function<T, R> converter) {

        List<R> content = slice.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());
        int number = slice.getNumber();
        int size = slice.getSize();
        boolean hasNext = slice.hasNext();

        return PagedResponse.of(content, number, size, hasNext);
    }
}