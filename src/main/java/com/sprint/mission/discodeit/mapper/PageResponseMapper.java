package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.Page.PageResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public class PageResponseMapper {

    public <T> PageResponse<T> fromSlice (Slice<T> slice) {
        return new PageResponse<>(
                slice.getContent(),
                slice.getContent().get(slice.getContent().size() - 1),
                slice.getSize(),
                slice.hasNext(),
                slice.stream().count()
        );
    }
    public <T> PageResponse<T> fromPage(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.hasContent(),
                page.getTotalElements()
        );
    }


}
