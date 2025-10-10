package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.Channel.ChannelDto;
import com.sprint.mission.discodeit.dto.User.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@AllArgsConstructor
public class ChannelMapper {

    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserMapper userMapper;

    public ChannelDto toDto(Channel channel) {
        List<UserDto> users = readStatusRepository.findAll().stream()
                .filter(readStatus -> readStatus.getChannel().getId().equals(channel.getId()))
                .map(ReadStatus::getUser)
                .map(userMapper::toDto)
                .toList();

        Message message = messageRepository.findByChannelIdOrderByCreatedAtDesc(channel.getId())
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
}
