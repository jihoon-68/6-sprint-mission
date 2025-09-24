package com.sprint.mission.discodeit.dto.messagedto;

import jakarta.validation.constraints.NotBlank;

public record UpdateMessage(
        @NotBlank String newContent
) {
}
