package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.api.response.PagingResponseDTO;
import com.sprint.mission.discodeit.dto.api.response.PagingResponseDTO.OffsetPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class PageResponseMapper<T> {

  public OffsetPageResponse<T> fromSlice(Slice<T> slice) {
    return PagingResponseDTO.OffsetPageResponse.<T>builder()
        .content(slice.getContent())
        .number(slice.getNumber())
        .size(slice.getSize())
        .hasNext(slice.hasNext())
        .totalElements(null)
        .build();
  }

  public OffsetPageResponse<T> fromPage(Page<T> page) {
    return PagingResponseDTO.OffsetPageResponse.<T>builder()
        .content(page.getContent())
        .number(page.getNumber())
        .size(page.getSize())
        .hasNext(page.hasNext())
        .totalElements(page.getTotalElements())
        .build();
  }

}
