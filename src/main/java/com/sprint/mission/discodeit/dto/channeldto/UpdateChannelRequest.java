package com.sprint.mission.discodeit.dto.channeldto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UpdateChannelRequest(
    @NotBlank String newName,
    @NotBlank String newDescription
) {

}
