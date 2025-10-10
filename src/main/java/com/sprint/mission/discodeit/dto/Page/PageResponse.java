package com.sprint.mission.discodeit.dto.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int number,
        int size,
        boolean hasNext,
        Long totalElements
) {
}
