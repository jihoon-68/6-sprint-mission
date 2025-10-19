package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.Channel.ChannelDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Mapper(componentModel = "spring")
public abstract class ChannelMapper {

    //단건 변환
    public ChannelDto toDto(Channel channel, List<UserDto> userDto, Instant lastMessageAt) {
        return toDtoWithAllParameters(channel, userDto, lastMessageAt);
    }

    //다건 변환
    //디비에서 불러오는 데이터가 많은 경우에는 OutOfMemoryError 가 발생 할수 았자만 지금은 대이터가 작아서
    //디비 호출을 줄이는데 사용함
    public List<ChannelDto> toDtoList(List<Channel> channels, Map<UUID, List<UserDto>> privateChannelDtoUser, Map<UUID, Instant> lastMessageAt) {
        if (channels == null || channels.isEmpty()) {
            return List.of();
        }

        return channels.stream()
                .map(channel -> {
                    List<UserDto> users = privateChannelDtoUser.getOrDefault(channel.getId(), List.of());
                    Instant lastMessage = lastMessageAt.get(channel.getId());
                    return toDtoWithAllParameters(channel, users, lastMessage);
                })
                .toList();

    }

    @Mapping(target = "id", expression = "java(channel.getId())")
    @Mapping(target = "type", expression = "java(channel.getType())")
    @Mapping(target = "name", expression = "java(channel.getName())")
    @Mapping(target = "description", expression = "java(channel.getDescription())")
    @Mapping(source = "participants", target = "participants")
    @Mapping(source = "lastMessageAt", target = "lastMessageAt")
    protected abstract ChannelDto toDtoWithAllParameters(Channel channel, List<UserDto> participants, Instant lastMessageAt);

}
