package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.UUID;

public record UserDto(
    UUID id,
    String Username,
    String email,
    BinaryContentDto profile,
    Boolean online
) {

}
