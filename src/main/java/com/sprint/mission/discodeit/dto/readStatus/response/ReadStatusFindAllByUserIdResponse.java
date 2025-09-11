package com.sprint.mission.discodeit.dto.readStatus.response;

import com.sprint.mission.discodeit.dto.readStatus.model.ReadStatusDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReadStatusFindAllByUserIdResponse {
    List<ReadStatusDto> readStatusDtoList;
}
