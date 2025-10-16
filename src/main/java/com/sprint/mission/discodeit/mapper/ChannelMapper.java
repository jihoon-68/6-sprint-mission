package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.Channel.ChannelDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring",
        uses = {UserMapper.class}
)
public abstract class ChannelMapper {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ReadStatusRepository readStatusRepository;


    //단건 변환
    public ChannelDto toDto(Channel channel) {
        List<UserDto> users = readStatusRepository.findAll().stream()
                .filter(readStatus -> readStatus.getChannel().getId().equals(channel.getId()))
                .map(ReadStatus::getUser)
                .map(UserMapper.INSTANCE::toDto)
                .toList();

        Instant message = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
                .map(Message::getCreatedAt)
                .orElse(null);


        return toDtoWithAllParameters(channel, users, message);
    }

    //다건 변환
    //디비에서 불러오는 데이터가 많은 경우에는 OutOfMemoryError 가 발생 할수 았자만 지금은 대이터가 작아서
    //디비 호출을 줄이는데 사용함
    public List<ChannelDto> toDtoList(List<Channel> channels) {
        if (channels == null || channels.isEmpty()) {
            return List.of();
        }

        List<UUID> channelIds = channels.stream()
                .map(Channel::getId)
                .toList();

        Map<UUID, List<UserDto>> participantsMap = toParticipantsMap(channelIds);
        Map<UUID, Instant> lastMessageAtMap = toLastMessageAtMap(channelIds);

        return channels.stream()
                .map(channel -> {
                    List<UserDto> users = participantsMap.getOrDefault(channel.getId(), List.of());
                    Instant lastMessage = lastMessageAtMap.get(channel.getId());
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

    private Map<UUID, List<UserDto>> toParticipantsMap(List<UUID> channelIds) {
        return readStatusRepository.findByChannelIdIn(channelIds).stream()
                .collect(Collectors.groupingBy(
                        readStatus -> readStatus.getChannel().getId(),
                        Collectors.mapping(rs -> UserMapper.INSTANCE.toDto(rs.getUser()), Collectors.toList())
                ));
    }

    private Map<UUID, Instant> toLastMessageAtMap(List<UUID> channelIds) {
        return messageRepository.findByChannelIdInOrderByCreatedAtDesc(channelIds).stream()
                .collect(Collectors.toMap(message -> message.getChannel().getId(), Message::getCreatedAt));
    }

}
