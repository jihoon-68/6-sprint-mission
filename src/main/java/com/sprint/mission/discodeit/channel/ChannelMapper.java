package com.sprint.mission.discodeit.channel;

import com.sprint.mission.discodeit.channel.ChannelDto.Response.PrivateResponse;
import com.sprint.mission.discodeit.channel.ChannelDto.Response.PublicResponse;
import com.sprint.mission.discodeit.channel.ChannelDto.Response.PublicResponseWithLastMessageAt;
import com.sprint.mission.discodeit.channel.domain.Channel;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public final class ChannelMapper {

    private ChannelMapper() {
    }

    public static Channel ofPublic(String channelName, String description) {
        return Channel.ofPublic(channelName, description);
    }

    public static Channel ofPrivate(Set<UUID> joinedUserIds) {
        return Channel.ofPrivate(joinedUserIds);
    }

    public static PublicResponse toPublicResponse(Channel channel) {
        return new PublicResponse(
                channel.getId(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                channel.getChannelName(),
                channel.getDescription()
        );
    }

    public static PublicResponseWithLastMessageAt toPublicResponseWithLastMessageAt(
            Channel channel,
            Instant lastMessageAt
    ) {
        return new PublicResponseWithLastMessageAt(
                channel.getId(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                channel.getChannelName(),
                channel.getDescription(),
                lastMessageAt
        );
    }

    public static PrivateResponse toPrivateResponse(Channel channel) {
        return new PrivateResponse(
                channel.getId(),
                channel.getCreatedAt(),
                channel.getJoinedUserIds()
        );
    }
}
