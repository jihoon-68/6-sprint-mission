package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
public class PageResponseMapper<T> {

  public PageResponse<T> fromSlice(Slice<T> slice) {

    Object nextCursor = null;

    // GET /api/messages에서 쿼리로 가져오는 cursor 타입이 date-time인걸 보아 시간 기준으로 페이징하는 것 같음
    // slice의 다음 페이지의 최신순 첫번째의 시간을 가져와 nextCursor로 설정해야함
    // MessageRespository.findAllByChannel_IdAndCreatedAtBefore() 참고
    // 그런데 제너릭으로 가져와야하기 때문에 모르겠음

    return new PageResponse<>(
        slice.getContent(),
        nextCursor,
        slice.getSize(),
        slice.hasNext(),
        null
    );
  }

  public PageResponse<T> fromPage(Page<T> page) {
    return new PageResponse<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.hasNext(),
        page.getTotalElements()
    );
  }
}
