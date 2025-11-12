package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record PrivateChannelCreateRequest(
    List<@NotNull(message = "참여자 ID는 null일 수 없습니다.") UUID> participantIds
) {

}
