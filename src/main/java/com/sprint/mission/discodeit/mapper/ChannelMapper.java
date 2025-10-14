package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.Channel.ChannelDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ChannelMapper {

    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserMapper userMapper;

    //단건 변환
    public ChannelDto toDto(Channel channel) {
        List<UserDto> users = readStatusRepository.findAll().stream()
                .filter(readStatus -> readStatus.getChannel().getId().equals(channel.getId()))
                .map(ReadStatus::getUser)
                .map(userMapper::toDto)
                .toList();

        Message message = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
                .orElse(null);

        Instant lastMessageAt = null;
        if (message != null) {
            lastMessageAt = message.getCreatedAt();
        }

        return new ChannelDto(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                users,
                lastMessageAt
        );
    }

    //다건 변환
    //디비에서 불러오는 데이터가 많은 경우에는 OutOfMemoryError 가 발생 할수 았자만 지금은 대이터가 작아서
    //디비 호출을 줄이는데 사용함
    public List<ChannelDto> toDtoList(List<Channel> channels) {
        List<ChannelDto> channelDtoList = new ArrayList<>();
        List<ReadStatus> readStatuses = readStatusRepository.findAll();
        for (Channel channel : channels) {
            List<User> users = readStatuses.stream()
                    .filter(readStatus -> readStatus.getChannel().getId().equals(channel.getId()))
                    .map(ReadStatus::getUser)
                    .toList();

            List<UserDto> userDtoList = userMapper.toDtoList(users);

            Message message = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channel.getId())
                    .orElse(null);

            Instant lastMessageAt = null;
            if (message != null) {
                lastMessageAt = message.getCreatedAt();
            }
            ChannelDto channelDto = new ChannelDto(channel.getId(),channel.getType(),channel.getName(),channel.getDescription(),userDtoList,lastMessageAt);
            channelDtoList.add(channelDto);
        }
        return channelDtoList;
    }
}
