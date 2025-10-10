package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.user.UserResponseDto;
import com.sprint.mission.discodeit.entity.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "lastMessageAt", ignore = true)
    ChannelResponseDto toDto(Channel channel);

//    public static ChannelResponseDto toDto(Channel channel, List<UserResponseDto> participants, Instant lastMessageAt) {
//        return ChannelResponseDto.builder()
//                .id(channel.getId())
//                .type(channel.getType())
//                .name(channel.getName())
//                .description(channel.getDescription())
//                .participants(participants)
//                .lastMessageAt(lastMessageAt)
//                .build();
//    }
}
