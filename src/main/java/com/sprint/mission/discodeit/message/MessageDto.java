package com.sprint.mission.discodeit.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class MessageDto {

    private MessageDto() {
    }

    public record Request(
            @NotBlank String content,
            @NotNull UUID channelId,
            @NotNull UUID authorId,
            @Nullable List<@NotBlank String> messageAttachmentsBase64
    ) {
        public Request {
            if (messageAttachmentsBase64 == null) {
                messageAttachmentsBase64 = Collections.emptyList();
            }
        }
    }

    public record Response(
            UUID id,
            Instant createdAt,
            Instant updatedAt,
            String content,
            UUID channelId,
            UUID authorId
    ) {
    }
}
