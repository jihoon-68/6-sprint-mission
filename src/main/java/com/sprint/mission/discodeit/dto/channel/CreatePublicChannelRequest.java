package com.sprint.mission.discodeit.dto.channel;

import jakarta.validation.constraints.NotBlank;

public record CreatePublicChannelRequest(
    @NotBlank(message = "{channel.name.notblank}")
    String name,
    @NotBlank(message = "{channel.description.notblank}")
    String description
) {

}
