package com.sprint.mission.discodeit.dto.message;

import jakarta.validation.constraints.NotBlank;

public record UpdateMessageRequest(
    @NotBlank String newContent
) {

}
