package com.sprint.mission.discodeit.dto.message.reqeust;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageUpdateRequest {
    String content;
    UUID id;
}
