package com.sprint.mission.discodeit.dto.messagedto;

import jakarta.validation.constraints.NotBlank;

public record UpdateMessageRequest(
    @NotBlank String newContent
) {

}
