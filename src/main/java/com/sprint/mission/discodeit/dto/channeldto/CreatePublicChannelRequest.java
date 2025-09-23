package com.sprint.mission.discodeit.dto.channeldto;

import jakarta.validation.constraints.NotBlank;

public record CreatePublicChannelRequest(
    @NotBlank String name,
    @NotBlank String description
) {

}
