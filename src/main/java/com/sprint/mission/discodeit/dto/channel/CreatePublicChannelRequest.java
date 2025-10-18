package com.sprint.mission.discodeit.dto.channel;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreatePublicChannelRequest(
    @NotEmpty(message = "{channel.name.notempty}")
    @Size(max = 100)
    String name,
    @NotEmpty(message = "{channel.description.notempty}")
    @Size(max = 500)
    String description
) {

}
