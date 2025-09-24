package com.sprint.mission.discodeit.channel;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public final class ChannelDto {

    private ChannelDto() {
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Request.PublicRequest.class, name = "PUBLIC"),
            @JsonSubTypes.Type(value = Request.PrivateRequest.class, name = "PRIVATE")
    })
    public sealed interface Request {

        record PublicRequest(@NotBlank String channelName, @NotNull String description) implements Request {
        }

        record PrivateRequest(@NotNull @Size(min = 2) Set<@NotNull UUID> joinedUserIds) implements Request {
        }
    }

    public sealed interface Response {

        record PublicResponse(
                UUID id,
                Instant createdAt,
                Instant updatedAt,
                String channelName,
                String description
        ) implements Response {
        }

        record PublicResponseWithLastMessageAt(
                UUID id,
                Instant createdAt,
                Instant updatedAt,
                String channelName,
                String description,
                Instant lastMessageAt
        ) implements Response {
        }

        record PrivateResponse(
                UUID id,
                Instant createdAt,
                Set<UUID> joinedUserIds
        ) implements Response {
        }
    }
}
