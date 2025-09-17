package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.PrivateChannelDto;
import com.sprint.mission.discodeit.dto.PublicChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.Instant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChannelMapper {

    default Channel fromPublicChannel(PublicChannelDto.Request request) {
        return Channel.ofPublic(request.channelName(), request.description());
    }

    default Channel fromPrivateChannel(PrivateChannelDto.Request request) {
        return Channel.ofPrivate(request.joinedUserIds());
    }

    PublicChannelDto.Response toPublicChannelResponse(Channel channel);

    PrivateChannelDto.Response toPrivateChannelResponse(Channel channel);

    PublicChannelDto.MessageInfoResponse toResponse(Channel channel, Instant lastMessageAt);
}
